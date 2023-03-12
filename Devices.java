import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Devices extends JFrame{
    private ArrayList<JPanel> panel;
    private JTextArea ConsoleOut,ConsoleIn;
    public Devices(){
        super("Console");
        this.setSize(640,480);
        panel = new ArrayList<JPanel>();
        panel.add(new JPanel());
        panel.add(new JPanel());
        ConsoleOut = new JTextArea("ConsoleOut", 60 , 30);
        ConsoleIn = new JTextArea("KeyBoard", 1, 30);
        panel.get(0).add(ConsoleOut);
        panel.get(1).add(ConsoleIn);
        panel.get(0).setBounds(0, 0, 320, 240);
        panel.get(1).setBounds(0, 400, 200, 50);
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