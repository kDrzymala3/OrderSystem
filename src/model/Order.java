package model;

public class Order {
    private String clientId;
    private Long requestId;
    private String name;
    private Integer quantity;
    private Double price;

    public Order(String clientId, Long requestId, String name, Integer quantity, Double price) {
        this.clientId = clientId;
        this.requestId = requestId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return clientId+", "+requestId+", "+name+", "+quantity+", "+price;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
