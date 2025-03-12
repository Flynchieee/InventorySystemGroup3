package group.pkg3;

import java.sql.*;
import java.util.ArrayList;

public class InventorySystem {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
    }

    public static boolean login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false;
            } else {
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                insertStmt.setString(1, username);
                insertStmt.setString(2, password); // (TODO: Hash password before storing)
                insertStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addItem(Item item) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT id, quantity FROM items WHERE name = ? AND status = ?");
            checkStmt.setString(1, item.getName());
            checkStmt.setString(2, item.getStatus()); // Now checking by name AND status
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Item with same name & status exists → update quantity
                int existingId = rs.getInt("id");
                int existingQuantity = rs.getInt("quantity");
                int newQuantity = existingQuantity + item.getQuantity();

                if (newQuantity < -808) {
                    // If quantity falls below -808, remove the item
                    PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM items WHERE id = ?");
                    deleteStmt.setInt(1, existingId);
                    deleteStmt.executeUpdate();
                    System.out.println("Item removed due to low stock!");
                } else {
                    // Otherwise, update quantity
                    PreparedStatement updateStmt = conn.prepareStatement("UPDATE items SET quantity = ? WHERE id = ?");
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setInt(2, existingId);
                    updateStmt.executeUpdate();
                }
            } else {
                // If no existing item with same name & status, insert a new one
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO items (name, quantity, status) VALUES (?, ?, ?)");
                insertStmt.setString(1, item.getName());
                insertStmt.setInt(2, item.getQuantity());
                insertStmt.setString(3, item.getStatus());
                insertStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static ArrayList<Item> viewInventory() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM items")) {
            ArrayList<Item> items = new ArrayList<Item>();
            int place = 0;
            while (rs.next()) {
                Item item = new Item(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getString("status"));
                items.add(item);
                place++;
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
