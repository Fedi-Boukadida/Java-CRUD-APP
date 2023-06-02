import java.sql.*;
import java.util.ArrayList;

public class RangementDAO extends DAO<Rangement> {
    public RangementDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Rangement> findAll() {
        ArrayList<Rangement> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Rangement");
            while (resultSet.next()) {
                int refRangement = resultSet.getInt("RefRangement");
                String nomRangement = resultSet.getString("NomRangement");
                result.add(new Rangement(refRangement, nomRangement));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Rangement find(int id) {
        Rangement result = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Rangement WHERE RefRangement = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nomRangement = resultSet.getString("NomRangement");
                result = new Rangement(id, nomRangement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean create(Rangement obj) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Rangement (RefRangement, NomRangement) VALUES (?, ?)");
            statement.setInt(1, obj.getRefRangement());
            statement.setString(2, obj.getNomRangement());
            int rowsInserted = statement.executeUpdate();
            result = rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Rangement obj) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Rangement SET NomRangement = ? WHERE RefRangement = ?");
            statement.setString(1, obj.getNomRangement());
            statement.setInt(2, obj.getRefRangement());
            int rowsUpdated = statement.executeUpdate();
            result = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Rangement obj) {
        boolean result = false;
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Rangement WHERE RefRangement = ?");
            statement.setInt(1, obj.getRefRangement());
            int rowsDeleted = statement.executeUpdate();
            result = rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}