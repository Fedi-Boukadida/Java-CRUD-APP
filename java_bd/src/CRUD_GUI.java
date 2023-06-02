import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CRUD_GUI extends JFrame {
    private JButton rechercherButton, insererButton, modifierButton, supprimerButton;
    
    public CRUD_GUI() {
        // Set the title and layout of the GUI
        super("Database CRUD ");
        Image icon = new ImageIcon("icons/user.png").getImage();
        setLayout(new GridLayout(4, 1, 10, 10)); // use GridLayout with spacing between components
        setIconImage(icon);
        // Initialize the four buttons and add them to the GUI
        rechercherButton = new JButton("Rechercher");
        insererButton = new JButton("Insérer");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
        
        // Customize the buttons with colors and icons
        rechercherButton.setBackground(Color.decode("#E26D5A")); // use a consistent color scheme
        rechercherButton.setForeground(Color.white);
        rechercherButton.setIcon(new ImageIcon("icons/search.png")); // use icons that are relevant to the purpose of each button
        rechercherButton.setFocusable(false);
        insererButton.setBackground(Color.decode("#EFA8B8"));
        insererButton.setForeground(Color.white);
        insererButton.setIcon(new ImageIcon("icons/plus.png"));
        insererButton.setFocusable(false);
        modifierButton.setBackground(Color.decode("#BBB09B"));
        modifierButton.setForeground(Color.white);
        modifierButton.setIcon(new ImageIcon("icons/tool.png"));
        modifierButton.setFocusable(false);
        supprimerButton.setBackground(Color.decode("#7CA1FF"));
        supprimerButton.setForeground(Color.white);
        supprimerButton.setIcon(new ImageIcon("icons/trash-2.png"));
        supprimerButton.setFocusable(false);
        
        // Add the buttons to the GUI
        add(rechercherButton);
        add(insererButton);
        add(modifierButton);
        add(supprimerButton);
        
        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code to handle "Rechercher" button click event
            }
        });
        
        insererButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code to handle "Insérer" button click event
            }
        });
        
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code to handle "Supprimer" button click event
            }
        });
        

        // Set the size and visibility of the GUI
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        CRUD_GUI gui = new CRUD_GUI();
    }
}
