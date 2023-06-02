import java.sql.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
       // Create a CompositionDAO object using the connection
        Connection conn = DBMSConnection.getConnection();
        CompositionDAO compositionDAO = new CompositionDAO(conn);

        // Test the findAll() method
        /*ArrayList<Composition> compositions = compositionDAO.findAll();
        System.out.println("findAll(): " + compositions);

        for (Composition x : compositions) {
            System.out.println(x);
        }*/

        // Test the find() method
        Composition composition = compositionDAO.find(1);
        System.out.println("find(1): " + composition);

        // Test the create() method
        /*Composition newComposition = new Composition(5, 300, 1, 1);
        if (compositionDAO.create(newComposition)) {
            System.out.println("New composition added to the database: " + newComposition);
        } else {
            System.err.println("Error adding new composition to the database");
        }*/

        // Test the update() method
        Composition compositionToUpdate = compositionDAO.find(3);
        compositionToUpdate.setQuantiteComposition(500);
        if (compositionDAO.update(compositionToUpdate)) {
            System.out.println("Composition updated in the database: " + compositionToUpdate);
        } else {
            System.err.println("Error updating composition in the database");
        }

        // Test the delete() method
        /*Composition compositionToDelete = compositionDAO.find(4);
        if (compositionDAO.delete(compositionToDelete)) {
            System.out.println("Composition deleted from the database: " + compositionToDelete);
        } else {
            System.err.println("Error deleting composition from the database");
        }*/

        // Close the database connection
        try {
            conn.close();
            System.out.println("Database connection closed");
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }
}
