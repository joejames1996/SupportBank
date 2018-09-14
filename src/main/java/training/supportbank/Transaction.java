package training.supportbank;

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

    public static void printAllTransactions(Account account)
    {
        for(Transaction t : transactions)
        {
            if(t.fromAccount == account)
            {
                System.out.println("(" + t.date + ") " + t.fromAccount.name + " owes " + t.formatTransactionAmount() + " to " + t.toAccount.name + " for " + t.narrative);
            }
            else if(t.toAccount == account)
            {
                System.out.println("(" + t.date + ") " + t.toAccount.name + " is owed " + t.formatTransactionAmount() + " from " + t.fromAccount.name + " for " + t.narrative);
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
