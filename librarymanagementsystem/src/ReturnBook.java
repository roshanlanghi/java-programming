import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ReturnBook extends JFrame implements ActionListener {
    JTextField bookIdField, studentIdField;
    JButton returnButton, backButton;

    ReturnBook() {
        setTitle("Return Book");
        setLayout(null);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(50, 50, 100, 30);
        add(bookIdLabel);

        bookIdField = new JTextField();
        bookIdField.setBounds(150, 50, 180, 30);
        add(bookIdField);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(50, 100, 100, 30);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(150, 100, 180, 30);
        add(studentIdField);

        returnButton = new JButton("Return Book");
        returnButton.setBounds(50, 180, 120, 30);
        returnButton.addActionListener(this);
        add(returnButton);

        backButton = new JButton("Back");
        backButton.setBounds(210, 180, 120, 30);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            String bookId = bookIdField.getText().trim();
            String studentId = studentIdField.getText().trim();

            if (bookId.isEmpty() || studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            try (Connection con = DatabaseConnection.getConnection()) {
                PreparedStatement pst = con.prepareStatement(
                        "UPDATE issued_books SET return_date = CURDATE() " +
                                "WHERE book_id = ? AND student_id = ? AND return_date IS NULL"
                );
                pst.setString(1, bookId);
                pst.setString(2, studentId);

                int updated = pst.executeUpdate();
                if (updated > 0) {
                    PreparedStatement updateBook = con.prepareStatement(
                            "UPDATE books SET is_issued = false WHERE book_id = ?"
                    );
                    updateBook.setString(1, bookId);
                    updateBook.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Book already returned or not found!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error returning book.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}
