import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

 /*
  * @author: Boukadida Fedi
  */

public class FoodManagement extends JFrame {

    private static final long serialVersionUID = 1L;

    private Connection conn;
    private TypeIngredientDAO typeIngredientDAO;
    private IngredientDAO ingredientDAO;
    private RecetteDAO recetteDAO;
    private RangementDAO rangementDAO;
    private ProduitDAO produitDAO;
    private CompositionDAO compositionDAO;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> tableComboBox;
    private JButton searchButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JLabel titleLabel;

    public FoodManagement() {
        // Initialize database connection
        conn = DBMSConnection.getConnection();
        typeIngredientDAO = new TypeIngredientDAO(conn);
        ingredientDAO = new IngredientDAO(conn);
        recetteDAO = new RecetteDAO(conn);
        rangementDAO = new RangementDAO(conn);
        produitDAO = new ProduitDAO(conn);
        compositionDAO = new CompositionDAO(conn);

        // Initialize frame
        setTitle("Food Management");
        ImageIcon image=new ImageIcon("icons/download.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        //

        // Create panel for title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel("CRUD Operations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(36, 47, 65));
        titlePanel.add(titleLabel);

        // Create panel for search functionality
        JPanel searchPanel = new JPanel(new GridBagLayout());
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchLabel.setForeground(new Color(36, 47, 65));
        searchField = new JTextField(5);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton = new JButton("Search");
        searchButton.setFocusable(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(36, 47, 65));
        // searchButton.addActionListener(e -> searchButtonClicked());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        searchPanel.add(searchLabel, gbc);
        gbc.gridx = 1;
        searchPanel.add(searchField, gbc);
        gbc.gridx = 2;
        searchPanel.add(searchButton, gbc);

        // Create panel for table selection and buttons
        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tableComboBox = new JComboBox<>(
                new String[] { "TypeIngredient", "Ingredient", "Recette", "Rangement", "Produit", "Composition" });
        tableComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        tableComboBox.setPreferredSize(new Dimension(150, 30));
        insertButton = new JButton("Insert");
        insertButton.setFocusable(false);
        insertButton.setFont(new Font("Arial", Font.BOLD, 14));
        insertButton.setForeground(Color.WHITE);
        insertButton.setBackground(new Color(36, 47, 65));
        // insertButton.addActionListener(e -> insertButtonClicked());
        deleteButton = new JButton("Delete");
        deleteButton.setFocusable(false);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(36, 47, 65));
        
        modifyButton = new JButton("Update");
        modifyButton.setFocusable(false);
        modifyButton.setFont(new Font("Arial", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(new Color(36, 47, 65));
        modifyButton.addActionListener(e -> modifyButtonClicked());
   

        tablePanel.add(tableComboBox);
        tablePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        tablePanel.add(insertButton);
        tablePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        tablePanel.add(modifyButton);
        tablePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        tablePanel.add(deleteButton);

        // Create panel for table
        JPanel tableContainer = new JPanel(new BorderLayout());
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(36, 47, 65));
        table.getTableHeader().setBackground(new Color(189, 195, 199));
        table.setRowHeight(25);
        table.setDefaultEditor(Object.class, null);
        table.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    deleteButton.doClick();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(508, 400));
        tableContainer.add(scrollpane, BorderLayout.CENTER);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonText = ((JButton) e.getSource()).getText();
                String tableName = (String) tableComboBox.getSelectedItem();
                switch (buttonText) {
                    case "Search":
                        // method for search button
                        String searchValue = searchField.getText();
                        if (searchValue.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please enter a value to search for", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        searchValue = searchValue.trim();
                        search(searchValue);
                        break;
                    case "Insert":
                        // method for insert button
                        String t = (String) tableComboBox.getSelectedItem();
                        
                        try {
                            createInsertForm(t);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "Delete":
                        // method for delete button
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(null, "Please select a row to delete", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int confirm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this record?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            BigDecimal id = BigDecimal.valueOf(Double.parseDouble(model.getValueAt(selectedRow, 0).toString()));
                            String pk=(String)model.getColumnName(0);
                            try {
                                String query = "DELETE FROM " + tableName + " WHERE "+pk+"=?";
                                PreparedStatement statement = conn.prepareStatement(query);
                                statement.setBigDecimal(1, id);
                                int rowsDeleted = statement.executeUpdate();
                                if (rowsDeleted > 0) {
                                    JOptionPane.showMessageDialog(null, "Record deleted successfully");
                                    model.removeRow(selectedRow);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to delete record", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, "Failed to delete record :\n"+e1, "Error",
                            JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        searchButton.addActionListener(buttonListener);
        insertButton.addActionListener(buttonListener);
        deleteButton.addActionListener(buttonListener);
        tableComboBox.addActionListener(e -> refreshTable());
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });

        // Add panels to frame
        add(titlePanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
        add(tableContainer, BorderLayout.EAST);
        refreshTable();
        setVisible(true);
    }

    private void refreshTable() {
        String selectedTable = (String) tableComboBox.getSelectedItem();
        switch (selectedTable) {
            case "TypeIngredient":
                try {

                    String query = "SELECT * FROM TypeIngredient";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Ingredient":
                try {

                    String query = "SELECT * FROM Ingredient";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Recette":
                try {

                    String query = "SELECT * FROM Recette";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Rangement":
                try {

                    String query = "SELECT * FROM Rangement";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Produit":
                try {

                    String query = "SELECT * FROM Produit";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Composition":
                try {

                    String query = "SELECT * FROM Composition";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            default:
                break;
        }
    }
    void search( String value) {
        String selectedTable = (String) tableComboBox.getSelectedItem();
        switch (selectedTable) {
            case "TypeIngredient":
                try {

                    String query = "SELECT * FROM TypeIngredient WHERE RefType LIKE '%"+value+"%' OR NomType LIKE '%"+value+"%'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    searchField.setText("");
                    refreshTable();}
                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Ingredient":
                try {

                    String query = "SELECT * FROM Ingredient WHERE RefIngredient LIKE '%"+value+"%' OR NomIngredient LIKE '%"+value+"%' OR RefType LIKE '%"+value+"%'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    searchField.setText("");
                    refreshTable();}
                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Recette":
                try {

                    String query = "SELECT * FROM Recette WHERE RefRecette LIKE '%"+value+"%' OR NomRecette LIKE '%"+value+"%'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    searchField.setText("");
                    refreshTable();}

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Rangement":
                try {

                    String query = "SELECT * FROM Rangement WHERE RefRangement LIKE '%"+value+"%' OR NomRangement LIKE '%"+value+"%'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    searchField.setText("");
                    refreshTable();}

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Produit":
                try {

                    String query = "SELECT * FROM Produit WHERE RefProduit LIKE '%"+value+"%' OR DescriptifProduit LIKE '%"+value+"%' OR RefRangement LIKE '%"+value+"%'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    searchField.setText("");
                    refreshTable();}

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            case "Composition":
                try {

                    String query = "SELECT * FROM Composition WHERE RefComposition LIKE '%"+value+"%'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet rs = statement.executeQuery(query);
                    table.setModel(buildTableModel(rs));
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No results found", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    searchField.setText("");
                    refreshTable();}

                } catch (SQLException e) {

                    e.printStackTrace();

                }
                break;
            default:
                break;
        }
    }
    private void createInsertForm(String tableName) throws SQLException {
        // Create new JFrame with dark blue background color
        JFrame insertFrame = new JFrame("Insert into " + tableName);
        JButton submitButton= new JButton("Submit");
        insertFrame.getContentPane().setBackground(new Color(24, 42, 60));
        insertFrame.setSize(500, 350);
        insertFrame.setLayout(new BorderLayout());
        ImageIcon img = new ImageIcon("icons/row.png");
        insertFrame.setIconImage(img.getImage());
        
        // Get column names for the selected table
        String[] columnNames = getColumnNames(tableName);
        
        // Create JPanel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(24, 42, 60));
        inputPanel.setLayout(new GridLayout(columnNames.length, 2, 10, 10));
        
        // Create input fields for each column
        JTextField[] inputFields = new JTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            JLabel label = new JLabel(columnNames[i]);
            label.setForeground(Color.WHITE);
            inputFields[i] = new JTextField();
            inputFields[i].setCaretColor(Color.WHITE);
            inputFields[i].setBackground(new Color(44, 62, 80));    
            inputFields[i].setForeground(Color.WHITE);
            inputFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                        submitButton.doClick();
                    }
                }
            });
            inputPanel.add(label);
            inputPanel.add(inputFields[i]);
        }
        
        // Create JPanel for submit button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(24, 42, 60));
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            // Build SQL query with input values
            String query = "INSERT INTO " + tableName + " (";
            for (int i = 0; i < columnNames.length; i++) {
                query += columnNames[i];
                if (i < columnNames.length - 1) {
                    query += ", ";
                }
            }
            query += ") VALUES (";
            //if (tableName.equals("Produit"))
            for (int i = 0; i < inputFields.length; i++) {
                if (tableName.equals("Produit")&&i==2) {
                    query += "TO_DATE('" + inputFields[i].getText() + "', 'YYYY-MM-DD')";
                } else {
                    query += "'" + inputFields[i].getText() + "'";
                }
                if (i < inputFields.length - 1) {
                    query += ", ";
                }
            }
            query += ")";
            
            // Execute SQL query
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Record inserted successfully");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to insert record", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Failed to insert record :\n"+e1, "Error",
                            JOptionPane.ERROR_MESSAGE);
                            try {
                                createInsertForm(tableName);
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                e1.printStackTrace();
            }
            
            // Close insert frame
            insertFrame.dispose();
        });
        buttonPanel.add(submitButton);
        
        // Add input and button panels to frame
        insertFrame.add(inputPanel, BorderLayout.CENTER);
        insertFrame.add(buttonPanel, BorderLayout.SOUTH);
        
        // Show insert frame
        insertFrame.setVisible(true);
    }
    private void modifyButtonClicked() {
        // Get selected table and row
        String tableName = (String) tableComboBox.getSelectedItem();
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to modify");
            return;
        }
        
        try {
    
            // Create new EditForm to modify row
            createUpdateForm(tableName, selectedRow);
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to get column values:\n" + e, "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void createUpdateForm(String tableName, int rowId) throws SQLException {
 
        JFrame updateFrame = new JFrame("Update " + tableName + " record");
        JButton submitButton = new JButton("Submit");
        updateFrame.getContentPane().setBackground(new Color(24, 42, 60));
        updateFrame.setSize(500, 350);
        updateFrame.setLayout(new BorderLayout());
        ImageIcon img = new ImageIcon("icons/icons8-update-64.png");
        updateFrame.setIconImage(img.getImage());
    
        // Get column names and values for the selected row
        String[] columnNames = getColumnNames(tableName);
        String[] columnValues = getColumnValues(tableName, rowId);
    
        // Create JPanel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(24, 42, 60));
        inputPanel.setLayout(new GridLayout(columnNames.length, 2, 10, 10));
    
        // Create input fields for each column
        JTextField[] inputFields = new JTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            JLabel label = new JLabel(columnNames[i]);
            label.setForeground(Color.WHITE);
            inputFields[i] = new JTextField(columnValues[i]);
            inputFields[i].setCaretColor(Color.WHITE);
            inputFields[i].setBackground(new Color(44, 62, 80));
            inputFields[i].setForeground(Color.WHITE);
            if (i==0) {
                inputFields[i].setEditable(false);
            }
            inputFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                        submitButton.doClick();
                    }
                }
            });
            inputPanel.add(label);
            inputPanel.add(inputFields[i]);
        }
    
        // Create JPanel for submit button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(24, 42, 60));
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            // Build SQL query with updated values
            String query = "UPDATE " + tableName + " SET ";
            for (int i = 1; i < columnNames.length; i++) {
                query += columnNames[i]+"= ";
                if (tableName.equals("Produit")&&i==2) {
                    query += "TO_DATE('" + inputFields[i].getText() + "', 'YYYY-MM-DD')";
                } else {
                    query += "'" + inputFields[i].getText() + "'";
                }
                if (i < inputFields.length - 1) {
                    query += ", ";
                }
            }
            query += " WHERE "+columnNames[0]+"=" + (inputFields[0].getText());
    
            // Execute SQL query
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Record updated successfully");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update record no rows had been affected", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Failed to update record", "Error",
                        JOptionPane.ERROR_MESSAGE);
                        try {
                            createUpdateForm(tableName, rowId);
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                e1.printStackTrace();
            }
    
            // Close update frame
            updateFrame.dispose();
        });
    
        // Add components to update frame
        updateFrame.add(inputPanel, BorderLayout.CENTER);
        updateFrame.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(submitButton);
    
        // Display update frame
        updateFrame.setVisible(true);
    }
    
    
    private String[] getColumnValues(String tableName, int selectedRow) throws SQLException {
        // Get column names and number of columns for the selected table
        String[] columnNames = getColumnNames(tableName);
        int numColumns = columnNames.length;
    
        // Build SQL query to select the row at the specified index
        String query = "SELECT * FROM (SELECT ROWNUM rnum, t.* FROM " + tableName + " t) WHERE rnum = " + (selectedRow+1);

    
        // Execute SQL query and get result set
        ResultSet rs = conn.createStatement().executeQuery(query);
    
        // Extract column values from result set
        String[] values = new String[numColumns];
        if (rs.next()) {
            for (int i = 0; i < numColumns; i++) {
                String columnName = columnNames[i];
                String value = rs.getObject(columnName).toString();
                if(tableName.equals("Produit")&&i==2) {
                    value = value.substring(0, 10);
                }
                values[i] = value;
            }
        }
    
        return values;
    }
    
    public String[] getColumnNames(String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE 1=0";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i-1] = metadata.getColumnName(i);
        }
        return columnNames;
    }
    
        
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        // Get metadata of the ResultSet
        ResultSetMetaData metaData = rs.getMetaData();

        // Get number of columns in the ResultSet
        int columnCount = metaData.getColumnCount();
        // System.out.println(columnCount);

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

        new FoodManagement();
    }

}
