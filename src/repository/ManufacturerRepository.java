package repository;

import util.SqliteConnection;
import model.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ManufacturerRepository {
    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers";

        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        rs.getInt("manufacturer_id"),
                        rs.getString("name")
                );
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public Manufacturer searchManufacturerById(int id) {
        String sql = "SELECT * FROM manufacturers WHERE manufacturer_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Manufacturer(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
