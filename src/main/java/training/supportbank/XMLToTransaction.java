package training.supportbank;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;

public class XMLToTransaction
{
    public static void loadTransactionsFromXml()
    {
        XMLFileReader.readXml();
    }

    public static void getTransactions(Document document) throws Exception
    {
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
}
