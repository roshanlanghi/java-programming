import javax.swing.*;

public class About extends JFrame {
    About() {
        setTitle("About Project");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea aboutArea = new JTextArea();
        aboutArea.setText("Library Management System\nVersion 1.0\nDeveloped by [Your Name]\nFor Academic Purposes");
        aboutArea.setEditable(false);
        add(aboutArea);

        setVisible(true);
    }
}
