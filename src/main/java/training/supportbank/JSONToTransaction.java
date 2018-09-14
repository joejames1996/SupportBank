package training.supportbank;

import com.google.gson.*;

import java.io.Reader;
import java.util.Date;

public class JSONToTransaction
{
    private static Gson gson;

    public static void loadTransactionsFromJson()
    {
        try
        {
            parseJson(JSONFileReader.readJson(Main.filePath));

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            Main.LOGGER.error(e.toString());
        }
    }

    public static void parseJson(Reader reader)
    {
        JsonParser jp = new JsonParser();
        JsonElement tree = jp.parse(reader);
        JsonArray jsonArray = tree.getAsJsonArray();

        buildTypeAdapter();
        buildFromJsonArray(jsonArray);
    }

    private static void buildTypeAdapter()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Transaction.class, (JsonDeserializer<Transaction>) (jsonElement, type, jsonDeserializationContext) ->
        {
            Transaction t = null;
            if(jsonElement.isJsonObject())
            {
                t = createTransactionFromJsonObject(jsonElement.getAsJsonObject());
            }
            return t;
        });
        gson = gsonBuilder.create();
    }

    private static void buildFromJsonArray(JsonArray jsonArray)
    {
        for(JsonElement jsonElement : jsonArray)
        {
            Transaction.addNewTransaction(gson.fromJson(jsonElement, Transaction.class));
        }
    }

    private static Transaction createTransactionFromJsonObject(JsonObject transactionObject)
    {
        Transaction transaction = null;
        try
        {
            Date date = Parser.parseRowDate(transactionObject.get("date").getAsString(), "yyyy-MM-dd");
            String fromAccount = transactionObject.get("fromAccount").getAsString();
            String toAccount = transactionObject.get("toAccount").getAsString();
            String narrative = transactionObject.get("narrative").getAsString();
            float amount = Parser.parseRowAmount(transactionObject.get("amount").getAsString());
            transaction = new Transaction(date, fromAccount, toAccount, narrative, amount);
        }
        catch(Exception e)
        {
            System.out.println("Could not parse JSON file: " + e.toString());
            System.out.println(transactionObject.toString());
            Main.LOGGER.error(e.toString());
        }
        return transaction;
    }
}
