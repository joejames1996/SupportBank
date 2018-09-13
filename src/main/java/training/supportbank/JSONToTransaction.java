package training.supportbank;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.util.Date;

public class JSONToTransaction
{
    public static void parseJson(Reader reader)
    {
        JsonParser jp = new JsonParser();
        JsonElement tree = jp.parse(reader);
        JsonArray jsonArray = tree.getAsJsonArray();

        for(JsonElement jsonElement : jsonArray)
        {
            if(jsonElement.isJsonObject())
            {
                JsonObject transactionObject = jsonElement.getAsJsonObject();
                createTransactionFromJsonObject(transactionObject);
            }
        }
    }

    private static void createTransactionFromJsonObject(JsonObject transactionObject)
    {
        try
        {
            Date date = Transaction.parseRowDate(transactionObject.get("date").getAsString(), "yyyy-MM-dd");
            String fromAccount = transactionObject.get("fromAccount").getAsString();
            String toAccount = transactionObject.get("toAccount").getAsString();
            String narrative = transactionObject.get("narrative").getAsString();
            float amount = Transaction.parseRowAmount(transactionObject.get("amount").getAsString());
            Transaction transaction = new Transaction(date, fromAccount, toAccount, narrative, amount);
            Transaction.addNewTransaction(transaction);
        }
        catch(Exception e)
        {
            System.out.println("Could not parse JSON file: " + e.toString());
            System.out.println(transactionObject.toString());
            Main.LOGGER.error(e.toString());
        }
    }
}
