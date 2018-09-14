package training.supportbank;

import java.util.Date;
import java.util.List;

public class CSVToTransaction
{
    public static void loadTransactionsFromCsv()
    {
        try
        {
            List csvContent = CSVFileReader.readCsv(Main.filePath);
            int line = 2;
            String[] row;
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
                    rowDate = Parser.parseRowDate(row[0], "dd/MM/yyyy");
                    rowFromAccountName = row[1];
                    rowToAccountName = row[2];
                    rowNarrative = row[3];
                    rowAmount = Parser.parseRowAmount(row[4]);
                    Account fromAccount = Account.findOrCreateAccountFromName(rowFromAccountName);
                    Account toAccount = Account.findOrCreateAccountFromName(rowToAccountName);
                    Transaction newTransaction = new Transaction(rowDate, fromAccount, toAccount, rowNarrative, rowAmount);
                    Transaction.addNewTransaction(newTransaction);
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
}
