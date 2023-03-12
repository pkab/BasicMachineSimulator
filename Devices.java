import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Devices extends JFrame{
    private ArrayList<JPanel> panel;
    private JLabel L_ConsoleOut, L_ConsoleIn;
    private JTextArea ConsoleOut,ConsoleIn;
    public Devices(){
        super("Console");
        this.setSize(360,480);
        panel = new ArrayList<JPanel>();
        panel.add(new JPanel());
        panel.add(new JPanel());
        L_ConsoleIn = new JLabel("Keyboard");
        L_ConsoleIn.setFont(new Font("Arial", Font.BOLD, 16));
        L_ConsoleIn.setForeground(Color.BLUE);

        L_ConsoleOut = new JLabel("Printer");
        L_ConsoleOut.setFont(new Font("Arial", Font.BOLD, 16));
        L_ConsoleOut.setForeground(Color.BLUE);

        ConsoleIn = new JTextArea("KeyBoard", 1, 30);
        ConsoleOut = new JTextArea("ConsoleOut", 60 , 30);
        panel.get(1).add(L_ConsoleIn);
        panel.get(1).add(ConsoleIn);
        panel.get(0).add(L_ConsoleOut);        
        panel.get(0).add(ConsoleOut);
        panel.get(0).setBounds(0, 0, 320, 60);
        panel.get(1).setBounds(0, 61, 320, 300);
        this.add(panel.get(0));
        this.add(panel.get(1));
    }
    public void LoadDevices(short devid, String input){  
        switch (devid) {
            case 1:
                keyboard();
                break;
            case 2:
                printer(input);
                break;
        }
    
    }
    
    public void keyboard(){
    
    }
    
    public void printer(String input){
        
    }
    public void Run(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
    }
}