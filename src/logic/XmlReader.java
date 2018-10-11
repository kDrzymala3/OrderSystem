package logic;

import model.Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {
    private static final String[] tags = {"clientId", "requestId", "name", "quantity", "price"};

    public static TransferObject readXML(List<String> fileNames) {

        List<Order> orders = new ArrayList<>();
        ArrayList<WrongFile> wrongFiles=new ArrayList<>();
        for (String fileName : fileNames) {
            ArrayList<Integer> wrongRecords = new ArrayList<>();
            File file = new File(fileName);
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();
                NodeList nodeList = document.getElementsByTagName("request");
                String[] values = new String[tags.length];
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    try {
                        for (int j = 0; j < tags.length; j++) {
                            values[j] = element.getElementsByTagName(tags[j]).item(0).getTextContent();
                        }
                        Order order = new Order(values[0], Long.valueOf(values[1]), values[2], Integer.valueOf(values[3]), Double.valueOf(values[4]));
                        orders.add(order);
                    } catch (Exception e) {
                        wrongRecords.add(++i);
                        i--;
                    }
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (!wrongRecords.isEmpty()){
                WrongFile wrongFile = new WrongFile(fileName,wrongRecords);
                wrongFiles.add(wrongFile);
            }
        }

        TransferObject transferObject = new TransferObject(orders, wrongFiles);
        return transferObject;
    }
}
