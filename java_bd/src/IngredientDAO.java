import java.sql.*;
import java.util.ArrayList;

public class IngredientDAO extends DAO<Ingredient> {
    public IngredientDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Ingredient> findAll() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            String query = "SELECT * FROM Ingredient";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("RefIngredient");
                String name = resultSet.getString("NomIngredient");
                int typeId = resultSet.getInt("RefType");
                Ingredient ingredient = new Ingredient(id, name, typeId);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    @Override
    public Ingredient find(int id) {
        Ingredient ingredient = null;
        try {
            String query = "SELECT * FROM Ingredient WHERE RefIngredient = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("NomIngredient");
                int typeId = resultSet.getInt("RefType");
                ingredient = new Ingredient(id, name, typeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public boolean create(Ingredient obj) {
        try {
            String query = "INSERT INTO Ingredient (NomIngredient, RefType) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, obj.getNomIngredient());
            preparedStatement.setInt(2, obj.getRefType());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Ingredient obj) {
        try {
            String query = "UPDATE Ingredient SET NomIngredient = ?, RefType = ? WHERE RefIngredient = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, obj.getNomIngredient());
            preparedStatement.setInt(2, obj.getRefType());
            preparedStatement.setInt(3, obj.getRefIngredient());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Ingredient obj) {
        try {
            String query = "DELETE FROM Ingredient WHERE RefIngredient = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, obj.getRefIngredient());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

    