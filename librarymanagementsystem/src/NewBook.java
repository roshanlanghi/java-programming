import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class NewBook extends JFrame implements ActionListener {
    JTextField bookIdField, nameField, authorField, publisherField;
    JButton addButton, backButton;
    Image backgroundImage;

    public NewBook() {
        setTitle("Add New Book");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the same background image as Dashboard
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\LOQ\\OneDrive\\Desktop\\JAVACODESPRA\\librarymanagementsystem\\src\\librarybg.jpeg");
        backgroundImage = bgIcon.getImage();

        // Background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("Add New Book", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 0, 0, 120));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(heading, gbc);

        // Form Labels and Fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        addField(backgroundPanel, gbc, 1, "Book ID:", bookIdField = new JTextField(20));
        addField(backgroundPanel, gbc, 2, "Book Name:", nameField = new JTextField(20));
        addField(backgroundPanel, gbc, 3, "Author:", authorField = new JTextField(20));
        addField(backgroundPanel, gbc, 4, "Publisher:", publisherField = new JTextField(20));

        // Buttons
        gbc.gridy = 5;
        gbc.gridx = 0;
        addButton = createButton("Add Book");
        backgroundPanel.add(addButton, gbc);

        gbc.gridx = 1;
        backButton = createButton("Back");
        backgroundPanel.add(backButton, gbc);

        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        field.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(new Color(38, 117, 202));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO books(book_id, book_name, author, publisher) VALUES (?, ?, ?, ?)"
                );
                pst.setString(1, bookIdField.getText());
                pst.setString(2, nameField.getText());
                pst.setString(3, authorField.getText());
                pst.setString(4, publisherField.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
                dispose();
                new Dashboard();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding book.");
            }
        } else if (e.getSource() == backButton) {
            new Dashboard();  // Go back to dashboard
            dispose();        // Close this frame
        }
    }
}
