import java.sql.*;
import java.util.ArrayList;

public abstract class DAO<T> {
    protected Connection connection;

    // Constructeur de la classe DAO
    public DAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode findAll() qui retourne une liste d'objets de type T
    public abstract ArrayList<T> findAll() throws SQLException;

    // Méthode find() qui retourne un objet de type T en fonction de son ID
    public abstract T find(int id) throws SQLException;

    // Méthode create() pour insérer un nouvel objet de type T dans la base de données
    public abstract boolean create(T obj) throws SQLException;

    // Méthode update() pour mettre à jour un objet de type T dans la base de données
    public abstract boolean update(T obj) throws SQLException;

    // Méthode delete() pour supprimer un objet de type T de la base de données
    public abstract boolean delete(T obj) throws SQLException;
}
