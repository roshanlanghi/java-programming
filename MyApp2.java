import javax.swing.*;

public class MyApp2{
    public static void main(String[] args) 
    {
        JFrame frame=new JFrame("my frirst swing window");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //create panal
        JPanel panal=new JPanel();
        //create labael
        JLabel label=new JLabel("welcome to the swing");
        //create button
        JButton button=new JButton("click me");
        button.addActionListener(e->label.setText("you clicked the button"));
        JCheckBox checkBox=new JCheckBox("open");
        //JColorChooser color=new JColorChooser("black");
        panal.add(label);
        panal.add(button);
        panal.add(checkBox);
        //panal.add(color);
        frame.add(panal);
        frame.setVisible(true);
    }

    
}
