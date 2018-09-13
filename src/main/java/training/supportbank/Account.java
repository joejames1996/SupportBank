package training.supportbank;

import java.util.LinkedList;
import java.util.List;

public class Account
{
    String name;
    float currentMoney;

    static List<Account> accounts = new LinkedList<>();

    public Account(String name, float currentMoney)
    {
        this.name = name;
        this.currentMoney = currentMoney;
    }

    public static Account findAccountFromName(String name)
    {
        Account foundAccount = null;
        for(Account a : accounts)
        {
            if(a.name.equalsIgnoreCase(name))
            {
                foundAccount = a;
                break;
            }
        }
        return foundAccount;
    }

    public static Account findOrCreateAccountFromName(String name)
    {
        Account foundAccount = findAccountFromName(name);
        if(foundAccount == null)
        {
            foundAccount = createAccount(name);
        }
        return foundAccount;
    }

    public static Account changeAccountBalance(String name, float amount)
    {
        Account foundAccount = findAccountFromName(name);
        if(foundAccount != null)
        {
            accounts.remove(foundAccount);
            foundAccount.currentMoney += amount;
        }
        else
        {
            foundAccount = new Account(name, amount);
        }
        accounts.add(foundAccount);
        return foundAccount;
    }

    public static void printAllAccounts()
    {
        for(Account a : accounts)
        {
            System.out.println(a.name + " has a balance of " + a.formatCurrentMoney());
        }
    }

    public static Account createAccount(String name)
    {
        Account newAccount = new Account(name, 0.0f);
        accounts.add(newAccount);
        return newAccount;
    }

    public float getCurrentMoney()
    {
        return this.currentMoney;
    }

    public String formatCurrentMoney()
    {
        return String.format("£%.2f", getCurrentMoney());
    }
}
