import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BookRecords extends JFrame {
    JTable table;
    DefaultTableModel model;
    private Connection conn;
    private Statement stmt;

    public BookRecords() {
        setTitle("Book Records");
        setSize(800, 400); // Match the dashboard dimensions
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Book ID", "Book Name", "Author", "Publisher", "Is Issued"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadBookData();  // Call the method to load data into the table

        setVisible(true);
    }

    // Make sure connection is open before executing the query
    private void loadBookData() {
        try {
            // Open the connection if it's closed
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb?useSSL=false&serverTimezone=UTC", "root", "Roshan@1234");
                System.out.println("Database connected successfully"); // Debug message
            }

            // Create statement if it's closed
            if (stmt == null || stmt.isClosed()) {
                stmt = conn.createStatement();
            }

            String query = "SELECT book_id, book_name, author, publisher, is_issued FROM books";
            ResultSet rs = stmt.executeQuery(query);

            // Check if the result set contains any data
            boolean dataFound = false;
            while (rs.next()) {
                String bookId = rs.getString("book_id");
                String bookName = rs.getString("book_name");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                boolean isIssued = rs.getBoolean("is_issued");

                // Print the data to the console (debugging)
                System.out.println("Book ID: " + bookId + ", Book Name: " + bookName);

                model.addRow(new Object[]{bookId, bookName, author, publisher, isIssued});
                dataFound = true;  // Data found, set flag to true
            }

            // If no data found, show a message
            if (!dataFound) {
                JOptionPane.showMessageDialog(this, "No book records found.");
            }

            // Close resultSet after use
            rs.close();

            model.fireTableDataChanged(); // Refresh the table view

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading book records: " + e.getMessage());
        }
    }

    // Close resources properly
    public void closeConnection() {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BookRecords();
    }
}
