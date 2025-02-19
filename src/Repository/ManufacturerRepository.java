package Repository;

import connect.SqliteConnection;
import model.Manufacturers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ManufacturerRepository {
    public List<Manufacturers> getAllManufacturers() {
        List<Manufacturers> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers";

        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Manufacturers manufacturer = new Manufacturers(
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
}
