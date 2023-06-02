import java.sql.*;
import java.util.ArrayList;

public class TypeIngredientDAO extends DAO<TypeIngredient> {
    public TypeIngredientDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<TypeIngredient> findAll() {
        ArrayList<TypeIngredient> types = new ArrayList<>();
        try {
            String query = "SELECT * FROM TypeIngredient";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int refType = resultSet.getInt("RefType");
                String nomType = resultSet.getString("NomType");

                TypeIngredient type = new TypeIngredient(refType, nomType);
                types.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    @Override
    public TypeIngredient find(int id) {
        TypeIngredient type = null;
        try {
            String query = "SELECT * FROM TypeIngredient WHERE RefType=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nomType = resultSet.getString("NomType");

                type = new TypeIngredient(id, nomType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public boolean create(TypeIngredient obj) {
        boolean success = false;
        try {
            String query = "INSERT INTO TypeIngredient (RefType, NomType) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, obj.getRefType());
            statement.setString(2, obj.getNomType());
            int rowsInserted = statement.executeUpdate();
            success = rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean update(TypeIngredient obj) {
        boolean success = false;
        try {
            String query = "UPDATE TypeIngredient SET NomType=? WHERE RefType=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, obj.getNomType());
            statement.setInt(2, obj.getRefType());
            int rowsUpdated = statement.executeUpdate();
            success = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean delete(TypeIngredient obj) {
        boolean success = false;
        try {
            String query = "DELETE FROM TypeIngredient WHERE RefType=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, obj.getRefType());
            int rowsDeleted = statement.executeUpdate();
            success = rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
