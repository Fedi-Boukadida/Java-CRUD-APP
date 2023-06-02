import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Requete {

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        // Get metadata of the ResultSet
        ResultSetMetaData metaData = rs.getMetaData();
    
        // Get number of columns in the ResultSet
        int columnCount = metaData.getColumnCount();
        //System.out.println(columnCount);
    
        // Create a Vector to hold column names
        Vector<String> columnNames = new Vector<String>();
    
        // Get column names and add them to the vector
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
    
        // Create a Vector to hold rows of data
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    
        // Loop through each row of the ResultSet
        while (rs.next()) {
    
            // Create a Vector to hold the row data
            Vector<Object> row = new Vector<Object>();
    
            // Add the data for each column to the row Vector
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
    
            // Add the row Vector to the data Vector
            data.add(row);
        }
    
        // Create a DefaultTableModel using the column names and data
        return new DefaultTableModel(data, columnNames);
    }
    
    public static void main(String[] args) {
        Connection conn = DBMSConnection.getConnection();
        
        try {
            
            String query = "SELECT NomRecette FROM Recette WHERE CaloriesRecette > 300";
            PreparedStatement statement= conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
            conn.close();
        } catch (SQLException e) {
            
            e.printStackTrace();
        
        }
        
        
        
    }
}
