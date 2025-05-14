import javax.swing.*;

public class MyApp{

    public MyApp() {
        JFrame jfrm = new JFrame("A Simple Java App");
        jfrm.setSize(300, 150);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jlab = new JLabel("This is my application", SwingConstants.CENTER);
        jfrm.add(jlab);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
     
        // Create the "File" menu
        JMenu fileMenu = new JMenu("File");
        // Add items to the File menu
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");
    

        // Add action to Exit
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(exitItem);
   

        // Create the "Help" menu
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");

        // Add action to About
        aboutItem.addActionListener(e ->
            JOptionPane.showMessageDialog(jfrm, "MyApp v1.0\nCreated by Roshan")
        );

        helpMenu.add(aboutItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Set the menu bar on the frame
        jfrm.setJMenuBar(menuBar);

        jfrm.setVisible(true);



        // Create radio buttons
JRadioButton rb1 = new JRadioButton("Option 1");
JRadioButton rb2 = new JRadioButton("Option 2");

// Group the radio buttons
ButtonGroup group = new ButtonGroup();
group.add(rb1);
group.add(rb2);

// Create a panel to hold the radio buttons
JPanel radioPanel = new JPanel();
radioPanel.add(rb1);
radioPanel.add(rb2);

// Add the panel to the frame (below the label)
jfrm.add(radioPanel, java.awt.BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyApp());
    }
}
