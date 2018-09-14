package training.supportbank;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLFileReader
{
    public static void readXml()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(Main.filePath));

            document.getDocumentElement().normalize();

            XMLToTransaction.getTransactions(document);
        }
        catch(Exception e)
        {
            System.out.print(e.toString());
            Main.LOGGER.error(e.toString());
        }
    }
}
