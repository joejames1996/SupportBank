package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static String filePath = "";

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
