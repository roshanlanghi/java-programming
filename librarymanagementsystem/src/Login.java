import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;

    Login() {
        setTitle("Library Login");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load and scale the background image to fit the frame
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\LOQ\\OneDrive\\Desktop\\JAVACODESPRA\\librarymanagementsystem\\src\\login.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 500, 350);
        setContentPane(background);
        background.setLayout(null);

        // Transparent panel to hold components and center them
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 50, 400, 230);
        panel.setOpaque(false);
        background.add(panel);

        JLabel title = new JLabel("Library Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(110, 10, 200, 30);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 60, 100, 25);
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 60, 200, 25);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 100, 100, 25);
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 100, 200, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(80, 150, 100, 30);
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(200, 150, 100, 30);
        signupButton.setBackground(new Color(0, 123, 255));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(this);
        panel.add(signupButton);

        setResizable(false);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            // Check for empty fields
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password");
                return; // Stop here if input is invalid
            }

            try {
                Connection con = DatabaseConnection.getConnection();
                String query = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    setVisible(false);
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error");
            }
        } else if (ae.getSource() == signupButton) {
            dispose();
            new Signup();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
