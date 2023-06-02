import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompositionDAO extends DAO<Composition> {
    
    public CompositionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Composition> findAll() {
        ArrayList<Composition> compositions = new ArrayList<>();
        try {
            String query = "SELECT * FROM Composition";
            PreparedStatement statement = DBMSConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Composition composition = new Composition(
                    resultSet.getInt("RefComposition"),
                    resultSet.getInt("QuantiteComposition"),
                    resultSet.getInt("RefRecette"),
                    resultSet.getInt("RefIngredient")
                );
                compositions.add(composition);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compositions;
    }

    @Override
    public Composition find(int id) {
        Composition composition = null;
        try {
            String query = "SELECT * FROM Composition WHERE RefComposition=?";
            PreparedStatement statement =  DBMSConnection.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                composition = new Composition(
                    resultSet.getInt("RefComposition"),
                    resultSet.getInt("QuantiteComposition"),
                    resultSet.getInt("RefRecette"),
                    resultSet.getInt("RefIngredient")
                );
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return composition;
    }

    @Override
    public boolean create(Composition obj) {
        try {
            String query = "INSERT INTO Composition (RefComposition, QuantiteComposition, RefRecette, RefIngredient) VALUES (?, ?, ?, ?)";
            PreparedStatement statement =  DBMSConnection.getConnection().prepareStatement(query);
            statement.setInt(4, obj.getRefComposition());
            statement.setInt(2, obj.getQuantiteComposition());
            statement.setInt(3, obj.getRefRecette());
            statement.setInt(4, obj.getRefIngredient());
            int rowsInserted = statement.executeUpdate();
            statement.close();
            return (rowsInserted > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Composition obj) {
        try {
            String query = "UPDATE Composition SET QuantiteComposition=?, RefRecette=?, RefIngredient=? WHERE RefComposition=?";
            PreparedStatement statement =  DBMSConnection.getConnection().prepareStatement(query);
            statement.setInt(1, obj.getQuantiteComposition());
            statement.setInt(2, obj.getRefRecette());
            statement.setInt(3, obj.getRefIngredient());
            statement.setInt(4, obj.getRefComposition());
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            return (rowsUpdated > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Composition obj) {
        try {
            String query = "DELETE FROM Composition WHERE RefComposition=?";
            PreparedStatement statement =  DBMSConnection.getConnection().prepareStatement(query);
            statement.setInt(1, obj.getRefComposition());
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return (rowsDeleted > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
