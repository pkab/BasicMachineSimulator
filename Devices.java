import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.util.ArrayList;

public class Devices extends JFrame{
    private ArrayList<JPanel> panel;
    private JTextArea ConsoleOut,ConsoleIn,CacheOutput;
    public Devices(){
        super("Console");
        this.setSize(960,480);
        this.setLayout(null);
        panel = new ArrayList<JPanel>();
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        
        TitledBorder ConsoleOutBorder = new TitledBorder("Console Printer");
        ConsoleOutBorder.setTitleJustification(TitledBorder.CENTER);
        ConsoleOutBorder.setTitlePosition(TitledBorder.TOP);
        TitledBorder ConsoleInBorder = new TitledBorder("Console Keyboard");
        ConsoleInBorder.setTitleJustification(TitledBorder.CENTER);
        ConsoleInBorder.setTitlePosition(TitledBorder.TOP);
        TitledBorder CacheBorder = new TitledBorder("Cache Out");
        CacheBorder.setTitleJustification(TitledBorder.CENTER);
        CacheBorder.setTitlePosition(TitledBorder.TOP);

        ConsoleIn = new JTextArea("", 1, 20);
        ConsoleOut = new JTextArea(18,40);
        //ConsoleOut.setBounds(0,0,350,300);
        ConsoleOut.setEditable(false);
        ConsoleOut.setBackground(Color.BLACK);
        ConsoleOut.setForeground(Color.green);
        
        CacheOutput = new JTextArea(18,32);
        CacheOutput.setFont(new Font("Courrier New",Font.PLAIN,16));

        panel.get(0).setBorder(ConsoleOutBorder);       
        panel.get(0).add(ConsoleOut);

        panel.get(1).setBorder(ConsoleInBorder);
        panel.get(1).add(ConsoleIn);

        panel.get(2).setBorder(CacheBorder);
        panel.get(2).add(CacheOutput);
        
        panel.get(0).setBounds(10, 10, 480, 330);
        panel.get(1).setBounds((480-250)/2+10, 360, 250, 60);
        panel.get(2).setBounds(500,10,440,420);
        for(int i=0;i<3;i++)
            this.add(panel.get(i));
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
        //this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}