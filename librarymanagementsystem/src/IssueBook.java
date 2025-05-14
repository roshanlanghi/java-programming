import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class IssueBook extends JFrame implements ActionListener {
    JTextField bookIdField, studentIdField;
    JButton issueButton, backButton;

    IssueBook() {
        setTitle("Issue Book");
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

        issueButton = new JButton("Issue Book");
        issueButton.setBounds(50, 180, 120, 30);
        issueButton.addActionListener(this);
        add(issueButton);

        backButton = new JButton("Back");
        backButton.setBounds(210, 180, 120, 30);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            String bookId = bookIdField.getText().trim();
            String studentId = studentIdField.getText().trim();

            if (bookId.isEmpty() || studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            try (Connection con = DatabaseConnection.getConnection()) {
                // Check if book is already issued
                PreparedStatement checkStmt = con.prepareStatement("SELECT is_issued FROM books WHERE book_id = ?");
                checkStmt.setString(1, bookId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    if (rs.getBoolean("is_issued")) {
                        JOptionPane.showMessageDialog(this, "Book is already issued.");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Book ID not found.");
                    return;
                }

                // Issue the book
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO issued_books(book_id, student_id, issue_date) VALUES (?, ?, CURDATE())"
                );
                pst.setString(1, bookId);
                pst.setString(2, studentId);
                pst.executeUpdate();

                // Update books table
                PreparedStatement updateStmt = con.prepareStatement(
                        "UPDATE books SET is_issued = true WHERE book_id = ?"
                );
                updateStmt.setString(1, bookId);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error issuing book.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}
