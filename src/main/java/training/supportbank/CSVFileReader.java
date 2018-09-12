package training.supportbank;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;

public class CSVFileReader
{
    public static List readCsv(String filePath) throws Exception
    {
        String[] row = null;
        CSVReader csvReader = new CSVReader(new FileReader(filePath));
        csvReader.skip(1);
        List content = csvReader.readAll();
        csvReader.close();
        return content;
    }
}
