package logic;

import model.Order;

import java.util.ArrayList;
import java.util.List;

public class TransferObject {
    public static List<Order> orders;
    public static ArrayList<WrongFile> wrongRecords;

    public TransferObject(List<Order> orders, ArrayList<WrongFile> wrongRecords) {
        this.orders = orders;
        this.wrongRecords = wrongRecords;
    }

}
