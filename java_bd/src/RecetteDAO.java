import java.sql.*;
import java.util.ArrayList;

public class RecetteDAO extends DAO<Recette> {

    public RecetteDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Recette> findAll() {
        ArrayList<Recette> recettes = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            String query = "SELECT * FROM Recette";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("RefRecette");
                String nom = resultSet.getString("NomRecette");
                String descriptif = resultSet.getString("DescriptifRecette");
                int calories = resultSet.getInt("CaloriesRecette");
                String difficulte = resultSet.getString("Difficulte");
                int tempsPreparation = resultSet.getInt("TempsPreparation");
                int tempsCuisson = resultSet.getInt("TempsCuisson");
                int nbPersonnes = resultSet.getInt("NbPersonnes");
                Recette recette = new Recette(id, nom, descriptif, calories, difficulte, tempsPreparation, tempsCuisson,
                        nbPersonnes);
                recettes.add(recette);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recettes;
    }

    @Override
    public Recette find(int id) {
        Recette recette = null;
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Recette WHERE RefRecette=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nom = resultSet.getString("NomRecette");
                String descriptif = resultSet.getString("DescriptifRecette");
                int calories = resultSet.getInt("CaloriesRecette");
                String difficulte = resultSet.getString("Difficulte");
                int tempsPreparation = resultSet.getInt("TempsPreparation");
                int tempsCuisson = resultSet.getInt("TempsCuisson");
                int nbPersonnes = resultSet.getInt("NbPersonnes");
                recette = new Recette(id, nom, descriptif, calories, difficulte, tempsPreparation, tempsCuisson,
                        nbPersonnes);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recette;
    }

    @Override
    public boolean create(Recette obj) {
        boolean success = false;
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO Recette (NomRecette, DescriptifRecette, CaloriesRecette, Difficulte, TempsPreparation, TempsCuisson, NbPersonnes) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, obj.getNomRecette());
            statement.setString(2, obj.getDescriptifRecette());
            statement.setInt(3, obj.getCaloriesRecette());
            statement.setString(4, obj.getDifficulte());
            statement.setInt(5, obj.getTempsPreparation());
            statement.setInt(6, obj.getTempsCuisson());
            statement.setInt(7, obj.getNbPersonnes());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean update(Recette obj) {
        try {
            // Create a PreparedStatement to update the Recette table
            String query = "UPDATE Recette SET NomRecette=?, DescriptifRecette=?, CaloriesRecette=?, Difficulte=?, TempsPreparation=?, TempsCuisson=?, NbPersonnes=? WHERE RefRecette=?";
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters of the PreparedStatement with the values of the Recette
            // object
            stmt.setString(1, obj.getNomRecette());
            stmt.setString(2, obj.getDescriptifRecette());
            stmt.setDouble(3, obj.getCaloriesRecette());
            stmt.setString(4, obj.getDifficulte());
            stmt.setInt(5, obj.getTempsPreparation());
            stmt.setInt(6, obj.getTempsCuisson());
            stmt.setInt(7, obj.getNbPersonnes());
            stmt.setInt(8, obj.getRefRecette());

            // Execute the update statement and return true if successful
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Recette obj) {
        try {
            // Prepare the delete query
            String query = "DELETE FROM Recette WHERE RefRecette = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            // Set the parameters
            preparedStatement.setInt(1, obj.getRefRecette());

            // Execute the query
            int deletedRows = preparedStatement.executeUpdate();

            // Check if any row was deleted
            return deletedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}