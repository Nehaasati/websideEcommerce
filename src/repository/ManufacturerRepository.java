package repository;

import model.Manufacturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.SqliteConnection;

public class ManufacturerRepository {

    public List<Manufacturer> getAllManufacturers() throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers";

        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Validate connection
            if (conn == null) {
                throw new SQLException("Failed to obtain database connection");
            }

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        rs.getInt("manufacturer_id"),
                        rs.getString("name")
                );
                manufacturers.add(manufacturer);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching manufacturers: " + e.getMessage());
            throw e;
        }

        return manufacturers;
    }


}