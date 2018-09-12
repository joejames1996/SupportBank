package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static String filePath = "C:\\Work\\Training\\SupportBank\\Transactions2015.csv";

    public static void main(String args[])
    {
        Transaction.loadTransactions();

        ConsoleInput.readInput();
    }
}
