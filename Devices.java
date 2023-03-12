import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.util.ArrayList;

public class Devices extends JFrame{
    private ArrayList<JPanel> panel;
    private JTextArea ConsoleOut,ConsoleIn,CacheOutput;
    char cacheaddr[],cacheval[];
    public Devices(){
        super("Console");
        this.setSize(960,480);
        this.setLayout(null);

        cacheaddr=new char[16];
        cacheval=new char[16];
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
        ConsoleOut = new JTextArea(16,38);
        ConsoleOut.setFont(new Font("Courrier New",Font.BOLD,14));
        //ConsoleOut.setBounds(0,0,350,300);
        ConsoleOut.setEditable(false);
        ConsoleOut.setBackground(Color.BLACK);
        ConsoleOut.setForeground(Color.green);
        
        CacheOutput = new JTextArea(18,32);
        CacheOutput.setFont(new Font("Courrier New",Font.PLAIN,16));
        CacheOutput.setEditable(false);
        JScrollPane scroll = new JScrollPane(CacheOutput,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
        panel.get(0).setBorder(ConsoleOutBorder);       
        panel.get(0).add(ConsoleOut);

        panel.get(1).setBorder(ConsoleInBorder);
        panel.get(1).add(ConsoleIn);

        panel.get(2).setBorder(CacheBorder);
        panel.get(2).add(CacheOutput);
        
        panel.get(0).setBounds(10, 10, 480, 340);
        panel.get(1).setBounds((480-250)/2+10, 360, 250, 60);
        panel.get(2).setBounds(500,10,440,420);
        for(int i=0;i<3;i++)
            this.add(panel.get(i));
    }
    public void LoadDevices(short devid, String input){  
        switch (devid) {
            case 1:
                //keyboard();
                break;
            case 2:
                //printer(input);
                break;
        }
    
    }
    
    public void keyboard(char []Reg){
        Converter conv = new Converter();
        try{
            short c = (short)ConsoleIn.getText().charAt(0);
            conv.DecimalToBinary(c, Reg, 16);
        }catch(Exception e){
            conv.DecimalToBinary((short)0, Reg, 16);
        }
    }
    
    public void printer(char[] Reg){
        Converter conv = new Converter();
        Character dec = (char)conv.BinaryToDecimal(Reg, 16);
        StringBuilder build = new StringBuilder();
        build.append(dec);
        String s = new String(build.toString());
        try{
            ConsoleOut.append(s);
        }catch(Exception e){
            return;
        }
    }
    public void printCache(Cache cache){
        for(int i=cache.rear-1;i>=cache.front;i--){
            //System.out.println(cache.lines[i].key + " "+cache.lines[i].val);
            cache.DecimalToBinary(cache.lines[i].key, cacheaddr, 16);
            cache.DecimalToBinary(cache.lines[i].val, cacheval, 16);
            String key = new String(cacheaddr);
            String val = new String(cacheval);
            ArrayList<Character> keyarr = new ArrayList<Character>();
            ArrayList<Character> valarr = new ArrayList<Character>();
            for(int k=0;k<16;k++){
                if(key.charAt(k)==(char)0) keyarr.add('0');
                else keyarr.add('1');
                if(val.charAt(k)==(char)0) valarr.add('0');
                else valarr.add('1');
                
            }
            StringBuilder b = new StringBuilder();
            StringBuilder c = new StringBuilder();
            for(Character ch: keyarr) b.append(ch);
            for(Character ch: valarr) c.append(ch);
            CacheOutput.append(b.toString() + " " + c.toString()+"\n");
        }
        CacheOutput.append("\n");
    }
    public void Run(){
        //this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}