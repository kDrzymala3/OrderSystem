package tests;

import logic.TransferObject;
import logic.WrongFile;
import logic.XmlReader;
import model.Order;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class XmlReaderTest {

    private final String compareString = "[1, 1, Bulka, 1, 10.0, 1, 2, Chleb, 2, 15.0, 1, 2, Chleb, 5, 15.0]";
    private final List<String> fileName = Arrays.asList("src\\tests\\testdataXML.xml");
    private final String wrongFileString= "[Filename: src\\tests\\testdataXML.xml Lines/Element: [4]]";


    @Test
    public void readXML() {
        TransferObject tObject = XmlReader.readXML(fileName);
        ArrayList<WrongFile> wrongFiles = tObject.wrongRecords;
        List<Order> orders = tObject.orders;
        assertEquals("Should be tha same", compareString, orders.toString());
        assertEquals("Should be the same",wrongFileString,wrongFiles.toString());
    }
}