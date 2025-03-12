package group.pkg3;

public class Item {
    private int id;
    private String name;
    private int quantity;
    private String status; // Status instead of price

    public Item(int id, String name, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Quantity=" + quantity +
                ", Status='" + status + '\'' +
                '}';
    }
}
