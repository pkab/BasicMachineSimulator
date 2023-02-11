import javax.swing.*;
import java.awt.*;

public class GUI
{
    private JFrame MainWindow;
    private JLabel GPR[],X[],PC,MAR,MBR,IR,MFR,Priv;
    private Label gpr0_arr[],gpr1_arr[],gpr2_arr[],gpr3_arr[];
    private Label XLabel[][],pclab[],marlab[],mbrlab[],mfrlab[],
    irlab[],privlab;
    private JButton LDarr[];
    public GUI()throws NullPointerException{
        MainWindow = new JFrame("Machine Simulator");
        GPR = new JLabel[4];
        X = new JLabel[3];
        PC = new JLabel("PC");
        PC.setBounds(730, 80, 40, 20);
        MAR = new JLabel("MAR");
        MAR.setBounds(730, 110, 40, 20);
        MBR = new JLabel("MBR");
        MBR.setBounds(630, 140, 40, 20);
        IR = new JLabel("IR");
        IR.setBounds(630, 170, 40, 20);
        MFR = new JLabel("MFR");
        MFR.setBounds(930, 200, 40, 20);
        Priv = new JLabel("Privilege");
        Priv.setBounds(980, 230, 60, 20);
        MainWindow.add(PC);
        MainWindow.add(MAR);
        MainWindow.add(MBR);
        MainWindow.add(IR);
        MainWindow.add(MFR);
        MainWindow.add(Priv);
        LDarr = new JButton[10];
        gpr0_arr = new Label[16];
        gpr1_arr = new Label[16];
        gpr2_arr = new Label[16];
        gpr3_arr = new Label[16];
        mbrlab = new Label[16];
        irlab = new Label[16];
        XLabel = new Label[3][];
        pclab = new Label[12];
        marlab = new Label[12];
        mfrlab = new Label[4];
        privlab = new Label();
        privlab.setBounds(1055, 230, 20, 20);
        
        MainWindow.add(privlab);
        for(int i=0;i<3;i++){
            XLabel[i] = new Label[16];
        }
        privlab.setBackground(Color.black);
        for(int i=0;i<10;i++){
            LDarr[i] = new JButton("LD");
            if(i>=0 && i<4)
                LDarr[i].setBounds(480, 80+(i*30), 50, 20);
            else if(i>=4 && i <7)
                LDarr[i].setBounds(480, 220+((i-4)*30), 50, 20);
            else
                LDarr[i].setBounds(1080,80+((i-7)*30), 50, 20);
            MainWindow.add(LDarr[i]);
        }
        for(int i=0;i<4;i++){
            GPR[i]= new JLabel("R"+i);
            GPR[i].setBounds(50, 80+(i*30), 20, 20);
            mfrlab[i]=new Label();
            mfrlab[i].setBounds(980 + (i*25), 200, 20, 20);
            mfrlab[i].setBackground(Color.black);
            MainWindow.add(GPR[i]);
            MainWindow.add(mfrlab[i]);
        }
        for(int i=1;i<4;i++){
            X[i-1]=new JLabel("X"+i);
            X[i-1].setBounds(50, 220+((i-1)*30), 20, 20);
            MainWindow.add(X[i-1]);
        }
        for(int i=0;i<16;i++){
            gpr0_arr[i] = new Label();
            gpr0_arr[i].setBounds(80+(i*25),80,20,20);
            gpr0_arr[i].setBackground(Color.black);
            gpr1_arr[i] = new Label();
            gpr1_arr[i].setBounds(80+(i*25),110,20,20);
            gpr1_arr[i].setBackground(Color.black);
            gpr2_arr[i] = new Label();
            gpr2_arr[i].setBounds(80+(i*25),140,20,20);
            gpr2_arr[i].setBackground(Color.black);
            gpr3_arr[i] = new Label();
            gpr3_arr[i].setBounds(80+(i*25),170,20,20);
            gpr3_arr[i].setBackground(Color.black);
            XLabel[0][i] = new Label();
            XLabel[0][i].setBounds(80+(i*25),220,20,20);
            XLabel[0][i].setBackground(Color.black);
            XLabel[1][i] = new Label();
            XLabel[1][i].setBounds(80+(i*25),250,20,20);
            XLabel[1][i].setBackground(Color.black);
            XLabel[2][i] = new Label();
            XLabel[2][i].setBounds(80+(i*25),280,20,20);
            XLabel[2][i].setBackground(Color.black);
            mbrlab[i]=new Label();
            mbrlab[i].setBounds(680+(i*25),140,20,20);
            mbrlab[i].setBackground(Color.black);
            irlab[i]=new Label();
            irlab[i].setBounds(680+(i*25),170,20,20);
            irlab[i].setBackground(Color.black);
            MainWindow.add(gpr0_arr[i]);
            MainWindow.add(gpr1_arr[i]);
            MainWindow.add(gpr2_arr[i]);
            MainWindow.add(gpr3_arr[i]);
            MainWindow.add(XLabel[0][i]);
            MainWindow.add(XLabel[1][i]);
            MainWindow.add(XLabel[2][i]);
            MainWindow.add(mbrlab[i]);
            MainWindow.add(irlab[i]);
        }
        for(int i=0;i<12;i++){
            pclab[i]=new Label();
            pclab[i].setBounds(780 +(i*25),80,20,20);
            pclab[i].setBackground(Color.black);
            marlab[i]=new Label();
            marlab[i].setBounds(780+(i*25),110,20,20);
            marlab[i].setBackground(Color.black);
            MainWindow.add(pclab[i]);
            MainWindow.add(marlab[i]);
        }
    }
    public void switches(){
        JButton fft = new JButton("15");
        fft.setBounds(350,450,80,25);
        MainWindow.add(fft);
    
    }
    public void LoadGui(){
        JButton fft = new JButton("15");
        fft.setBounds(30,485,48,60);
        MainWindow.add(fft);
        
        JButton frt = new JButton("14");
        frt.setBounds(80,485,48,60);
        MainWindow.add(frt);
        
        JButton trt = new JButton("13");
        trt.setBounds(130,485,48,60);
        MainWindow.add(trt);
        
        JButton twv = new JButton("12");
        twv.setBounds(180,485,48,60);
        MainWindow.add(twv);
        
        JButton elv = new JButton("11");
        elv.setBounds(230,485,48,60);
        MainWindow.add(elv);
        
        JButton ten = new JButton("10");
        ten.setBounds(280,485,48,60);
        MainWindow.add(ten);
        
        JButton nine = new JButton("9");
        nine.setBounds(340,485,48,60);
        MainWindow.add(nine);
        
        JButton egt = new JButton("8");
        egt.setBounds(390,485,48,60);
        MainWindow.add(egt);
        
        JButton sev = new JButton("7");
        sev.setBounds(450,485,48,60);
        MainWindow.add(sev);
        
        JButton six = new JButton("6");
        six.setBounds(500,485,48,60);
        MainWindow.add(six);
        
        JButton five = new JButton("5");
        five.setBounds(560,485,48,60);
        MainWindow.add(five);
        
        JButton four = new JButton("4");
        four.setBounds(620,485,48,60);
        MainWindow.add(four);
        
        JButton thr = new JButton("3");
        thr.setBounds(670,485,48,60);
        MainWindow.add(thr);
        
        JButton two = new JButton("2");
        two.setBounds(720,485,48,60);
        MainWindow.add(two);
        
        JButton one = new JButton("1");
        one.setBounds(770,485,48,60);
        MainWindow.add(one);
        
        JButton zero = new JButton("0");
        zero.setBounds(820,485,48,60);
        MainWindow.add(zero);
        
        JButton store = new JButton("Store");
        store.setBounds(750,450,80,25);
        MainWindow.add(store);
        
        JButton st_plus = new JButton("St+");
        st_plus.setBounds(850,450,80,25);
        MainWindow.add(st_plus);
        
        JButton load = new JButton("Load");
        load.setBounds(950,450,80,25);
        MainWindow.add(load);
        
        JButton init = new JButton("Init");
        init.setBounds(1050,450,80,25);
        init.setBackground(Color.RED);
        MainWindow.add(init);
        
        //a.add(p);
        MainWindow.setSize(1200, 600);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.getContentPane().setBackground(Color.getHSBColor((float)0.533, (float)0.8, (float)0.75));
        MainWindow.setLayout(null);
        MainWindow.setVisible(true);
    }
    public static void main(String args[]) 
    {
        GUI g = new GUI();
        g.LoadGui();
        
    }
}
