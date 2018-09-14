package training.supportbank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileImporter
{
    private static String fileRegex = "[^\0-\37\\s*<>?|]+\\.(csv|json|xml)";

    public static void importFile(String fileName)
    {
        Pattern pattern = Pattern.compile(fileRegex);
        Matcher matcher = pattern.matcher(fileName);
        if(matcher.find())
        {
            Main.filePath = matcher.group();
            Main.onLoad();
            String ext = matcher.group(1);
            switch(ext)
            {
                case "csv":
                    CSVToTransaction.loadTransactionsFromCsv();
                    break;
                case "json":
                    JSONToTransaction.loadTransactionsFromJson();
                    break;
                case "xml":
                    XMLToTransaction.loadTransactionsFromXml();
                    break;
            }
        }
    }
}
