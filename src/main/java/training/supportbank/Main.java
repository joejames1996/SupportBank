package training.supportbank;

public class Main
{
    public static String filePath = "C:\\Work\\Training\\SupportBank\\Transactions2014.csv";

    public static void main(String args[])
    {
        Transaction.loadTransactions();

        ConsoleInput.readInput();
    }
}
