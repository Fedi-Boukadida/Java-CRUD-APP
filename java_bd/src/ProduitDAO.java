import java.sql.*;
import java.util.ArrayList;

public class ProduitDAO extends DAO<Produit> {

    // Constructeur de la classe ProduitDAO
    public ProduitDAO(Connection connection) {
        super(connection);
    }

    // Méthode findAll() pour récupérer tous les produits
    @Override
    public ArrayList<Produit> findAll() throws SQLException {
        ArrayList<Produit> produits = new ArrayList<Produit>();

        // Préparation de la requête SQL
        String sql = "SELECT * FROM Produit";

        // Exécution de la requête
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            // Parcours des résultats de la requête
            while (result.next()) {
                Produit produit = new Produit(result.getInt("RefProduit"), result.getString("DescriptionProduit"),
                        result.getDate("DatePeremption"), result.getInt("QuantiteProduit"), result.getFloat("PrixProduit"),
                        result.getInt("RefRangement"), result.getInt("RefIngredient"));
                produits.add(produit);
            }
        }

        return produits;
    }

    // Méthode find() pour récupérer un produit en fonction de son ID
    @Override
    public Produit find(int id) throws SQLException {
        Produit produit = null;

        // Préparation de la requête SQL
        String sql = "SELECT * FROM Produit WHERE RefProduit = ?";

        // Exécution de la requête
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            // Si un résultat est trouvé, création de l'objet Produit correspondant
            if (result.next()) {
                produit = new Produit(result.getInt("RefProduit"), result.getString("DescriptionProduit"),
                        result.getDate("DatePeremption"), result.getInt("QuantiteProduit"), result.getFloat("PrixProduit"),
                        result.getInt("RefRangement"), result.getInt("RefIngredient"));
            }
        }

        return produit;
    }

    // Méthode create() pour insérer un nouveau produit dans la base de données
    @Override
    public boolean create(Produit produit) throws SQLException {
        // Préparation de la requête SQL
        String sql = "INSERT INTO Produit (RefProduit, DescriptionProduit, DatePeremption, QuantiteProduit, PrixProduit, RefRangement, RefIngredient) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Exécution de la requête
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, produit.getRefProduit());
            statement.setString(2, produit.getDescriptifProduit());
            statement.setDate(3, produit.getDatePeremption());
            statement.setInt(4, produit.getQuantiteProduit());
            statement.setFloat(5, produit.getPrixProduit());
            statement.setInt(6, produit.getRefRangement());
            statement.setInt(7, produit.getRefIngredient());

            statement.executeUpdate();
        }

        return true;
    }

    public boolean update(Produit produit) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE Produit SET descriptifProduit=?, datePeremption=?, quantiteProduit=?, prixProduit=?, refRangement=?, refIngredient=? WHERE refProduit=?");
    
        statement.setString(1, produit.getDescriptifProduit());
        statement.setDate(2, produit.getDatePeremption());
        statement.setInt(3, produit.getQuantiteProduit());
        statement.setFloat(4, produit.getPrixProduit());
        statement.setInt(5, produit.getRefRangement());
        statement.setInt(6, produit.getRefIngredient());
        statement.setInt(7, produit.getRefProduit());
    
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    }
    
    public boolean delete(Produit produit) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Produit WHERE refProduit=?");
    
        statement.setInt(1, produit.getRefProduit());
    
        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted > 0;
    }
}