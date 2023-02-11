import javax.swing.*;
import java.awt.*;
import java.util.*;
public class GUI
{
    private CPU cpu;
    private Memory mem;
    private JFrame MainWindow;
    private JLabel GPR[],X[],PC,MAR,MBR,IR,MFR,Priv;
    private Label gpr0_arr[],gpr1_arr[],gpr2_arr[],gpr3_arr[]; // Important Ones that will be Kept Modifying
    private Label XLabel[][],pclab[],marlab[],mbrlab[],mfrlab[], // Important Ones
    irlab[],privlab,hlt,Run;
    private JButton LDarr[],store,st_plus,load,init,ss,run; // Load Button Array and other Imp Buttons
    private ArrayList<JButton> switches;
    private JPanel Pan[];
    public GUI()throws NullPointerException{
        MainWindow = new JFrame("Machine Simulator");
        hlt = new Label();
        hlt.setBounds(1150,460,20,20);
        hlt.setBackground(Color.BLACK);
        MainWindow.add(hlt);
        Run = new Label();
        Run.setBounds(1150,490,20,20);
        Run.setBackground(Color.BLACK);
        MainWindow.add(Run);
        Pan = new JPanel[5];
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
        switches = new ArrayList<JButton>();
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
        privlab.setBackground(Color.black);
        MainWindow.add(privlab);
        for(int i=0;i<3;i++){
            XLabel[i] = new Label[16];
        }
        /* Setting The Layout for Different Panels */
        for(int i=0;i<5;i++){
            Pan[i] = new JPanel();
            Pan[i].setLayout(new FlowLayout(FlowLayout.CENTER,1,5));
            //new BoxLayout(Pan[i],BoxLayout.X_AXIS)
            Pan[i].setBackground(Color.CYAN);
            MainWindow.add(Pan[i]);
        }
        Pan[0].setBounds(10,465,310,70);
        JLabel OpCode = new JLabel("OpCode");
        OpCode.setBounds(145,535,100,20);
        Pan[1].setBounds(330,465,100,70);
        JLabel gprLabel = new JLabel("GPR");
        gprLabel.setBounds(370,535,100,20);
        Pan[2].setBounds(440,465,100,70);
        JLabel ixrLabel = new JLabel("IXR");
        ixrLabel.setBounds(480,535,50,20);
        Pan[3].setBounds(550,465,50,70);
        JLabel indLabel = new JLabel("I");
        indLabel.setBounds(575,535,40,20);
        Pan[4].setBounds(610,465,220,70);
        JLabel addLabel = new JLabel("Address");
        addLabel.setBounds(695,535,100,20);
        MainWindow.add(OpCode);
        MainWindow.add(gprLabel);
        MainWindow.add(ixrLabel);
        MainWindow.add(indLabel);
        MainWindow.add(addLabel);
        //MainWindow.add(Pan[0]);
        
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
            switches.add(new JButton());
            switches.get(i).setText(""+(15-i));
            switches.get(i).setPreferredSize(new Dimension(48,60));
            switches.get(i).setFont(new Font("Arial",Font.PLAIN,11));
            if(i<6){
                Pan[0].add(switches.get(i));
            }
            else if(i>=6 && i<8){
                Pan[1].add(switches.get(i));
            }
            else if(i>=8 && i<10) {
                Pan[2].add(switches.get(i));
            }
            else if(i==10) {
                Pan[3].add(switches.get(i));
            }
            else if(i>=11 && i<16) {
                Pan[4].add(switches.get(i));
            }
            //switches.get(i).setBounds(30+(i*50),485,46,55);
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
        JLabel lhlt =new JLabel("Halt");
        JLabel lrun =new JLabel("Run");
        lhlt.setBounds(1110,460,40,20);
        lrun.setBounds(1110,490,40,20);
        MainWindow.add(lhlt);
        MainWindow.add(lrun);
    }
<<<<<<< HEAD
    private void runMainLoop(){
=======
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
>>>>>>> 3da789c10e0dc91ee9d2f3734a75823a1279a68c
        MainWindow.setSize(1200, 600);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.getContentPane().setBackground(Color.getHSBColor((float)0.533, (float)0.8, (float)0.75));
        MainWindow.setLayout(null);
        MainWindow.setVisible(true);
    }
    public void LoadGui(){
        store = new JButton("Store");
        store.setBounds(875,430,70,25);
        MainWindow.add(store);
        
        st_plus = new JButton("St+");
        st_plus.setBounds(950,430,70,25);
        MainWindow.add(st_plus);
        
        load = new JButton("Load");
        load.setBounds(1025,430,70,25);
        MainWindow.add(load);
        
        init = new JButton("Init");
        init.setBounds(1100,430,70,25);
        init.setBackground(Color.RED);
        init.setForeground(Color.white);
        MainWindow.add(init);
        
        ss = new JButton("SS");
        ss.setBounds(940,460,55,80);
        run = new JButton("Run");
        run.setBounds(1027,460,65,80);
        run.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        MainWindow.add(ss);
        MainWindow.add(run);
        
        runMainLoop();
    }
}
