package control;

import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private Connection connection;

    public OrderDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
        String username = "root";
        String pass = "pass123";
        this.connection = DriverManager.getConnection(url, username, pass);
        PreparedStatement preparedStatement = this.connection.prepareStatement("DROP DATABASE IF EXISTS orders");
        preparedStatement.execute();
        preparedStatement = this.connection.prepareStatement("CREATE DATABASE orders;");
        preparedStatement.execute();
        preparedStatement = this.connection.prepareStatement("USE orders;");
        preparedStatement.execute();
        preparedStatement = this.connection.prepareStatement("CREATE TABLE orders ("
                + "clientID VARCHAR(6), "
                + "requestID INTEGER, "
                + "name VARCHAR(255), "
                + "quantity INTEGER, "
                + "price REAL) ENGINE=MEMORY;");
        preparedStatement.execute();
    }

    public String save(List<Order> orders) {
        String SQL = "insert into orders(clientID,requestID,name,quantity,price) values (?,?,?,?,?)";
        for (Order order : orders) {
            try {
                PreparedStatement prepStmt = connection.prepareStatement(SQL);
                prepStmt.setString(1, order.getClientId());
                prepStmt.setLong(2, order.getRequestId());
                prepStmt.setString(3, order.getName());
                prepStmt.setInt(4, order.getQuantity());
                prepStmt.setDouble(5, order.getPrice());
                prepStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return "Records saved succesfully";
    }

    public List<Order> read() {
        Statement statement;
        List<Order> orders = new ArrayList<>();
        try {
            statement = this.connection.createStatement();
            String query = "SELECT * FROM orders";
            orders = getResultsToList(statement.executeQuery(query));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return orders;
    }

    public List<Order> readById(String clientId) {
        Statement statement;
        List<Order> orders = new ArrayList<>();
        try {
            statement = this.connection.createStatement();
            String query = "SELECT * FROM orders WHERE clientID=" + clientId;
            orders = getResultsToList(statement.executeQuery(query));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return orders;
    }

    public List<Order> getResultsToList(ResultSet resultSet) {
        List<Order> orders = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order(resultSet.getString(1), resultSet.getLong(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getDouble(5));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
