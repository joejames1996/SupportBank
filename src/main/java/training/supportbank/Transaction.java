package training.supportbank;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Transaction
{
    Date date;
    Account fromAccount;
    Account toAccount;
    String narrative;
    float amount;

    static List<Transaction> transactions = new LinkedList<>();

    public Transaction(Date date, Account fromAccount, Account toAccount, String narrative, float amount)
    {
        this.date = date;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.narrative = narrative;
        this.amount = amount;
    }

    public Transaction(Date date, String fromAccount, String toAccount, String narrative, float amount)
    {
        this.date = date;
        this.fromAccount = Account.findOrCreateAccountFromName(fromAccount);
        this.toAccount = Account.findOrCreateAccountFromName(toAccount);
        this.narrative = narrative;
        this.amount = amount;
    }

    public static void loadTransactionsFromCsv()
    {
        try
        {
            List csvContent = CSVFileReader.readCsv(Main.filePath);
            int line = 2;
            String[] row = null;
            Date rowDate;
            String rowFromAccountName;
            String rowToAccountName;
            String rowNarrative;
            float rowAmount;
            for(Object o : csvContent)
            {
                row = (String[])o;
                try
                {
                    rowDate = parseRowDate(row[0], "dd/MM/yyyy");
                    rowFromAccountName = row[1];
                    rowToAccountName = row[2];
                    rowNarrative = row[3];
                    rowAmount = parseRowAmount(row[4]);
                    Account fromAccount = Account.findOrCreateAccountFromName(rowFromAccountName);
                    Account toAccount = Account.findOrCreateAccountFromName(rowToAccountName);
                    Transaction newTransaction = new Transaction(rowDate, fromAccount, toAccount, rowNarrative, rowAmount);
                    addNewTransaction(newTransaction);
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage() + " on line " + line + " of " + Main.filePath);
                    Main.LOGGER.error(e);
                }

                line++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            Main.LOGGER.error(e);
        }
    }

    public static void loadTransactionsFromJson()
    {
        try
        {
            JSONToTransaction.parseJson(JSONFileReader.readJson(Main.filePath));

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            Main.LOGGER.error(e.toString());
        }
    }

    public static Date parseRowDate(String s, String pattern) throws Exception
    {
        Date d = null;
        try
        {
            SimpleDateFormat form = new SimpleDateFormat(pattern);
            d = form.parse(s);
        }
        catch(Exception e)
        {
            throw new Exception("Could not parse date: " + s);
        }
        return d;
    }

    public static float parseRowAmount(String s) throws Exception
    {
        float a = 0.0f;
        try
        {
            a = Float.parseFloat(s);
        }
        catch(NumberFormatException e)
        {
            throw new Exception("Could not parse amount: " + s);
        }
        return a;
    }

    public static void printAllTransactions(Account account)
    {
        for(Transaction t : transactions)
        {
            if(t.fromAccount == account)
            {
                System.out.println(t.fromAccount.name + " owes " + t.formatTransactionAmount() + " to " + t.toAccount.name + " for " + t.narrative);
            }
            else if(t.toAccount == account)
            {
                System.out.println(t.toAccount.name + " is owed " + t.formatTransactionAmount() + " from " + t.fromAccount.name + " for " + t.narrative);
            }
        }
    }

    String formatTransactionAmount()
    {
        return String.format("£%.2f", this.amount);
    }

    public static void addNewTransaction(Transaction transaction)
    {
        transactions.add(transaction);
        Account.changeAccountBalance(transaction.fromAccount.name, -transaction.amount);
        Account.changeAccountBalance(transaction.toAccount.name, transaction.amount);
    }
}
