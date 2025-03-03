package repository;

import model.Manufacturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.SqliteConnection;

import static util.SqliteConnection.getConnection;

public class ManufacturerRepository {

    public List<Manufacturer> getAllManufacturers() {

        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        rs.getInt("manufacturer_id"),
                        rs.getString("name")
                );
                manufacturers.add(manufacturer);
            }
        }catch (SQLException e){
                e.printStackTrace();
        }
             return manufacturers;
        }

    public Manufacturer getManufacturerById(int id) throws SQLException {
        String sql = "SELECT * FROM manufacturers WHERE manufacturer_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Manufacturer(
                            rs.getInt("manufacturer_id"),
                            rs.getString("name")
                    );
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Explicit "not found" indicator
    }
}