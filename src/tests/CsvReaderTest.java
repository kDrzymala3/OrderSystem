package tests;

import logic.CsvReader;
import logic.TransferObject;
import logic.WrongFile;
import model.Order;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CsvReaderTest {

    private final String compareString = "[1, 1, Bulka, 2, 10.0, 1, 1, Chleb, 2, 15.0, 1, 2, Chleb, 5, 15.0]";
    private final List<String> fileName = Arrays.asList("src\\tests\\testdataCSV.csv");
    private final String wrongFileString = "[Filename: src\\tests\\testdataCSV.csv Lines/Element: [5]]";

    @Test
    public void readCsv() throws IOException {
        TransferObject tObject = CsvReader.readCsv(fileName);
        List<Order> orders = tObject.orders;
        ArrayList<WrongFile> wrongFiles= tObject.wrongRecords;
        assertEquals(compareString,orders.toString());
        assertEquals(wrongFileString,wrongFiles.toString());

    }
}