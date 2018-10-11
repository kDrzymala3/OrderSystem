package logic;

import model.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Export {
    public static void exportToCsv(File file, List<Order> orders, Integer quantity, Double price, Double meanPrice) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
        StringBuilder sb = new StringBuilder();
        sb.append("Quantity");
        sb.append(',');
        sb.append("Full price");
        sb.append(',');
        sb.append("Mean price");
        sb.append('\n');
        sb.append(quantity);
        sb.append(',');
        sb.append(price);
        sb.append(',');
        sb.append(meanPrice);
        sb.append('\n');
        sb.append("Client_ID");
        sb.append(',');
        sb.append("Request_ID");
        sb.append(',');
        sb.append("Name");
        sb.append(',');
        sb.append("Quantity");
        sb.append(',');
        sb.append("Price");
        sb.append('\n');

        for (Order order : orders) {
            sb.append(order.getClientId());
            sb.append(',');
            sb.append(order.getRequestId());
            sb.append(',');
            sb.append(order.getName());
            sb.append(',');
            sb.append(order.getQuantity());
            sb.append(',');
            sb.append(order.getPrice());
            sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}
