package updated.inventory.system;



public class Item {
    private int id;
    private String name;
    private int quantity;
    private String status; // Status instead of price
    private String category;

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
    
    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
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
                ", Category='" + category + '\'' +                
                ", Status='" + status + '\'' +
                '}';
    }
}
