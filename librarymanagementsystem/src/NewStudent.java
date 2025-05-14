import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.sql.*;

public class NewStudent extends JFrame implements ActionListener {
    JTextField studentIdField, nameField, courseField;
    JButton addButton, backButton;

    NewStudent() {
        setTitle("Add New Student");
        setLayout(null);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(50, 50, 100, 30);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(150, 50, 180, 30);
        add(studentIdField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 90, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 90, 180, 30);
        add(nameField);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(50, 130, 100, 30);
        add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(150, 130, 180, 30);
        add(courseField);

        addButton = new JButton("Add Student");
        addButton.setBounds(50, 190, 120, 30);
        addButton.addActionListener(this);
        add(addButton);

        backButton = new JButton("Back");
        backButton.setBounds(210, 190, 120, 30);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pst = con.prepareStatement("INSERT INTO students(student_id, name, course) VALUES (?, ?, ?)");
                pst.setString(1, studentIdField.getText());
                pst.setString(2, nameField.getText());
                pst.setString(3, courseField.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student Added Successfully!");
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}
