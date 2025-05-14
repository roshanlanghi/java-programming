import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentRecords extends JFrame {

    JTable table;
    DefaultTableModel model;

    StudentRecords() {
        setTitle("Student Records");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {"Student ID", "Name", "Course", "Book ID", "Book Name", "Issue Date", "Return Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        fetchStudentRecords();

        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }

    private void fetchStudentRecords() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = """
                SELECT s.student_id, s.name, s.course, b.book_id, b.book_name, i.issue_date, i.return_date
                FROM students s
                LEFT JOIN issued_books i ON s.student_id = i.student_id
                LEFT JOIN books b ON i.book_id = b.book_id
                ORDER BY s.student_id;
                """;

            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("student_id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getString("book_id"),
                        rs.getString("book_name"),
                        rs.getDate("issue_date"),
                        rs.getDate("return_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
