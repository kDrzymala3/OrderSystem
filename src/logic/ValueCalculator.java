package logic;

import model.Order;

import java.util.List;

public class ValueCalculator {
    public static Integer calculateQuantity(List<Order> orders) {
        Integer quantity = orders.size();
        return quantity;
    }

    public static Double calculateOverallPrice(List<Order> orders) {
        Double overallPrice = 0.0;
        for (Order order : orders) {
            overallPrice += order.getPrice();
        }
        return overallPrice;
    }

    public static Double calculateMeanPrice(List<Order> orders) {
        Double meanPrice;
        Integer quantity = calculateQuantity(orders);
        Double overallPrice = calculateOverallPrice(orders);
        if (quantity==0&&overallPrice==0){
            meanPrice = 0.0;
        }else {
            meanPrice = overallPrice / quantity;
        }

        return meanPrice;
    }
}
