package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static String filePath = "C:\\Work\\Training\\SupportBank\\Transactions2013.json";

    public static void main(String args[])
    {
        ConsoleInput.readInput();
        onLoad();
    }

    public static void onLoad()
    {
        Transaction.transactions.clear();
        Account.accounts.clear();
    }
}
