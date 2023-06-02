import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Login_GUI extends JFrame {
    private JLabel titleLabel, usernameLabel, passwordLabel, statusLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public Login_GUI() {
        // Set the title and layout of the GUI
        super("Login Form");
        ImageIcon image=new ImageIcon("icons/login.png");
        setLayout(new GridBagLayout());
        setIconImage(image.getImage());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // add spacing between components
        
        // Initialize the labels and fields
        titleLabel = new JLabel("Database CRUD Application");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        titleLabel.setForeground( new Color(33, 150, 243)); // set light pink color
        
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Calibri", Font.PLAIN, 18));
        
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Calibri", Font.PLAIN, 18));

        
        // Initialize the login button and customize it with colors and an icon
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0x4CBB17)); 
        loginButton.setForeground(Color.white);
        loginButton.setFont(new Font("Calibri", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // add padding to the button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Perform login validation here
                if (username.equals("admin") && password.equals("admin")) {
                    // Login successful, close the login form and open the CRUD GUI
                    dispose();
                    FoodManagement foodManagement = new FoodManagement();
                } else {
                    // Login failed, show an error message
                    statusLabel.setText("Invalid username or password");
                    usernameField.setText("");
                    passwordField.setText("");
                    usernameField.requestFocus();

                }
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick(); // Trigger button click event on ENTER key press
                }
            }
        });
        
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick(); // Trigger button click event on ENTER key press
                }
            }
        });
        

        // Initialize the status label and add it to the GUI
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.red);
        
        
       // Add the components to the GUI using GridBagConstraints
       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.gridwidth = 2;
       gbc.anchor = GridBagConstraints.CENTER;
       add(titleLabel, gbc);
       
       gbc.gridy = 1;
       gbc.gridwidth = 1;
       gbc.anchor = GridBagConstraints.LINE_END;
       add(usernameLabel, gbc);
       
       gbc.gridx = 1;
       gbc.anchor = GridBagConstraints.LINE_START;
       add(usernameField, gbc);
       
       gbc.gridx = 0;
       gbc.gridy = 2;
       gbc.anchor = GridBagConstraints.LINE_END;
       add(passwordLabel, gbc);
       
       gbc.gridx = 1;
       gbc.anchor = GridBagConstraints.LINE_START;
       add(passwordField, gbc);
       
       gbc.gridx = 0;
       gbc.gridy = 3;
       gbc.gridwidth = 2;
       gbc.anchor = GridBagConstraints.CENTER;
       add(loginButton, gbc);
       
       gbc.gridx = 0;
       gbc.gridy = 4;
       gbc.gridwidth = 2;
       gbc.anchor = GridBagConstraints.CENTER;
       add(statusLabel, gbc);


        // Set the size and visibility of the GUI
        setSize(400, 300);
        //pack();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick(); // Trigger button click event on ENTER key press
                }
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Login_GUI gui = new Login_GUI();
    }
}
