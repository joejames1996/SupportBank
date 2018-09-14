package training.supportbank;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;

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

            Element root = document.getDocumentElement();

            NodeList nodeList = document.getElementsByTagName("SupportTransaction");

            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    String OLEDate = element.getAttribute("Date");
                    Date Date = DateManager.parseOLEDate(OLEDate);
                    String Narrative = element.getElementsByTagName("Description").item(0).getTextContent();
                    float Amount = Float.parseFloat(element.getElementsByTagName("Value").item(0).getTextContent());

                    String fromAccount = "";
                    String toAccount = "";

                    NodeList parties = document.getElementsByTagName("Parties");
                    Node partyNode = parties.item(0);
                    if(partyNode.getNodeType() == Node.ELEMENT_NODE);
                    {
                        Element partyElement = (Element)node;
                        fromAccount = partyElement.getElementsByTagName("From").item(0).getTextContent();
                        toAccount = partyElement.getElementsByTagName("To").item(0).getTextContent();
                    }
                    Transaction t = new Transaction(Date, fromAccount, toAccount, Narrative, Amount);
                    Transaction.addNewTransaction(t);
                }
            }

        }
        catch(Exception e)
        {
            System.out.print(e.toString());
            Main.LOGGER.error(e.toString());
        }
    }
}
