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

    public static void loadTransactions()
    {
        try
        {
            List csvContent = CSVFileReader.readCsv(Main.filePath);
            String[] row = null;
            Date rowDate;
            String rowFromAccountName;
            String rowToAccountName;
            String rowNarrative;
            float rowAmount;
            //System.out.println("Date:\tFrom:\tTo:\tReason:\tAmount");
            for(Object o : csvContent)
            {
                row = (String[])o;
                try
                {
                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                    rowDate = form.parse(row[0]);
                    rowFromAccountName = row[1];
                    rowToAccountName = row[2];
                    rowNarrative = row[3];
                    rowAmount = Float.parseFloat(row[4]);
                    //System.out.println(rowDate + "\t" + rowFromAccountName + "\t" + rowToAccountName + "\t" + rowNarrative + "\t£" + rowAmount);
                    Account fromAccount = Account.changeAccountBalance(rowFromAccountName, (-rowAmount));
                    Account toAccount = Account.changeAccountBalance(rowToAccountName, rowAmount);
                    Transaction newTransaction = new Transaction(rowDate, fromAccount, toAccount, rowNarrative, rowAmount);
                    transactions.add(newTransaction);
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
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
}
