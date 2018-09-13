package training.supportbank;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONFileReader
{
    public static Reader readJson(String filePath) throws Exception
    {
        Path path = Paths.get(filePath);
        return Files.newBufferedReader(path);
    }
}
