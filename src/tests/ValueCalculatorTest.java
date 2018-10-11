package tests;

import logic.ValueCalculator;
import model.Order;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueCalculatorTest {

    private static final Order o1 = new Order("1", 1L, "test1", 10, 3.0);
    private static final Order o2 = new Order("2", 2L, "test2", 3, 14.0);
    private static final Order o3 = new Order("3", 3L, "test3", 7, 4.0);
    private static final Order o4 = new Order("4", 4L, "test4", 9, 24.0);
    private static final Order o5 = new Order("5", 5L, "test5", 1, 7.0);
    private static final List<Order> orders = List.of(o1, o2,o3,o4,o5);
    private static final List<Order> orders2 = List.of();

    @Test
    void calculateQuantity() {
        int quant = ValueCalculator.calculateQuantity(orders);
        assertEquals(5, quant);
        int quant2 = ValueCalculator.calculateQuantity(orders2);
        assertEquals(0, quant2);
    }

    @Test
    void calculateOverallPrice() {
        double price = ValueCalculator.calculateOverallPrice(orders);
        assertEquals(52.0, price);
        double price2 = ValueCalculator.calculateOverallPrice(orders2);
        assertEquals(0, price2);
    }

    @Test
    void calculateMeanPrice() {
        double price = ValueCalculator.calculateMeanPrice(orders);
        assertEquals(10.4,price);
        double price2 = ValueCalculator.calculateMeanPrice(orders2);
        assertEquals(0,price2);
    }
}