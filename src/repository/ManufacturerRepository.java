package repository;

import util.SqliteConnectionManger;
import model.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import model.Manufacturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.SqliteConnectionManger;

import static util.SqliteConnectionManger.getConnection;

public class ManufacturerRepository {

    // Get all manufacturers with SQLException handled internally
    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers";

        try (Connection conn = SqliteConnectionManger.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                manufacturers.add(new Manufacturer(
                        rs.getInt("manufacturer_id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving manufacturers: " + e.getMessage());
            e.printStackTrace();
        }
        return manufacturers;
    }

    // Get manufacturer by ID with SQLException handled internally
    public Manufacturer getManufacturerById(int id) throws SQLException {
        String sql = "SELECT * FROM manufacturers WHERE manufacturer_id = ?";

        try(Connection conn = SqliteConnectionManger.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Manufacturer(
                            rs.getInt("manufacturer_id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving by Id: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Explicit "not found" indicator
    }
}