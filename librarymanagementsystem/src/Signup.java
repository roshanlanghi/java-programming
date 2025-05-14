import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton signupButton, backButton;

    Signup() {
        // Frame settings
        setTitle("Signup");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\LOQ\\OneDrive\\Desktop\\JAVACODESPRA\\librarymanagementsystem\\src\\login.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 500, 350);
        setContentPane(background);
        background.setLayout(null);

        // Transparent panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 50, 400, 230);
        panel.setOpaque(false);
        background.add(panel);

        // Username label & field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 30, 100, 30);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 180, 30);
        panel.add(usernameField);

        // Password label & field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 80, 100, 30);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 180, 30);
        panel.add(passwordField);

        // Signup button
        signupButton = new JButton("Signup");
        signupButton.setBounds(50, 140, 100, 30);
        signupButton.setBackground(new Color(0, 123, 255));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(this);
        panel.add(signupButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(230, 140, 100, 30);
        backButton.setBackground(new Color(0, 123, 255));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(this);
        panel.add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pst = con.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)");
                pst.setString(1, usernameField.getText());
                pst.setString(2, String.valueOf(passwordField.getPassword()));
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Signup Successful!");

                new Login(); // Make sure Login class exists
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Signup Failed!");
            }
        } else if (e.getSource() == backButton) {
            new Login(); // Make sure Login class exists
            dispose();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
