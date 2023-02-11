import javax.swing.*;
import java.awt.*;
import java.util.*;
public class GUI extends JFrame
{
    private CPU cpu;
    private Memory mem;
    private JMenuBar menuBar;
    private JMenu fileMenu,editMenu,aboutMenu;
    private JLabel GPR[],X[],PC,MAR,MBR,IR,MFR,Priv;
    private Label gpr0_arr[],gpr1_arr[],gpr2_arr[],gpr3_arr[]; // Important Ones that will be Kept Modifying
    private Label XLabel[][],pclab[],marlab[],mbrlab[],mfrlab[], // Important Ones
    irlab[],privlab,hlt,Run;
    private JButton LDarr[],store,st_plus,load,init,ss,run; // Load Button Array and other Imp Buttons
    private ArrayList<JButton> switches;
    private JPanel Pan[];
    char swarr[]; // Array for the switches pressed
    public GUI()throws NullPointerException{
        super();
        cpu = new CPU();
        mem = new Memory();
        //this = new JFrame("Machine Simulator");
        hlt = new Label();
        hlt.setBounds(1150,460,20,20);
        hlt.setBackground(Color.BLACK);
        this.add(hlt);
        Run = new Label();
        Run.setBounds(1150,490,20,20);
        Run.setBackground(Color.BLACK);
        this.add(Run);
        swarr = new char[16];
        for(int i=0;i<16;i++) swarr[i] = 0;
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
        this.add(PC);
        this.add(MAR);
        this.add(MBR);
        this.add(IR);
        this.add(MFR);
        this.add(Priv);
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
        this.add(privlab);
        for(int i=0;i<3;i++) XLabel[i] = new Label[16];
        /* Setting The Layout for Different Panels */
        for(int i=0;i<5;i++){
            Pan[i] = new JPanel();
            Pan[i].setLayout(new FlowLayout(FlowLayout.CENTER,1,5));
            //new BoxLayout(Pan[i],BoxLayout.X_AXIS)
            Pan[i].setBackground(Color.CYAN);
            this.add(Pan[i]);
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
        Pan[4].setBounds(610,465,270,70);
        JLabel addLabel = new JLabel("Address");
        addLabel.setBounds(695,535,100,20);
        this.add(OpCode);
        this.add(gprLabel);
        this.add(ixrLabel);
        this.add(indLabel);
        this.add(addLabel);
        //this.add(Pan[0]);
        
        for(int i=0;i<10;i++){
            LDarr[i] = new JButton("LD");
            if(i>=0 && i<4)
                LDarr[i].setBounds(480, 80+(i*30), 50, 20);
            else if(i>=4 && i <7)
                LDarr[i].setBounds(480, 220+((i-4)*30), 50, 20);
            else
                LDarr[i].setBounds(1080,80+((i-7)*30), 50, 20);
            LDarr[i].addActionListener(e -> LoadButtonAction(e));
            this.add(LDarr[i]);
        }
        for(int i=0;i<4;i++){
            GPR[i]= new JLabel("R"+i);
            GPR[i].setBounds(50, 80+(i*30), 20, 20);
            mfrlab[i]=new Label();
            mfrlab[i].setBounds(980 + (i*25), 200, 20, 20);
            mfrlab[i].setBackground(Color.black);
            this.add(GPR[i]);
            this.add(mfrlab[i]);
        }
        for(int i=1;i<4;i++){
            X[i-1]=new JLabel("X"+i);
            X[i-1].setBounds(50, 220+((i-1)*30), 20, 20);
            this.add(X[i-1]);
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
            switches.get(i).addActionListener(e -> switchAction(e));
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
            this.add(gpr0_arr[i]);
            this.add(gpr1_arr[i]);
            this.add(gpr2_arr[i]);
            this.add(gpr3_arr[i]);
            this.add(XLabel[0][i]);
            this.add(XLabel[1][i]);
            this.add(XLabel[2][i]);
            this.add(mbrlab[i]);
            this.add(irlab[i]);
        }
        for(int i=0;i<12;i++){
            pclab[i]=new Label();
            pclab[i].setBounds(780 +(i*25),80,20,20);
            pclab[i].setBackground(Color.black);
            marlab[i]=new Label();
            marlab[i].setBounds(780+(i*25),110,20,20);
            marlab[i].setBackground(Color.black);
            this.add(pclab[i]);
            this.add(marlab[i]);
        }
        // Halt and Run Indicator
        JLabel lhlt =new JLabel("Halt");
        JLabel lrun =new JLabel("Run");
        lhlt.setBounds(1110,460,40,20);
        lrun.setBounds(1110,490,40,20);
        this.add(lhlt);
        this.add(lrun);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        aboutMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);
        this.setJMenuBar(menuBar);
    }
    private void RefreshLeds(int buttonpress){
        if( buttonpress != 7 && buttonpress != 8)
            for(int i=0;i<16;i++){
                switch(buttonpress){
                    case 0:
                        if(cpu.R0[i] == 1)
                            gpr0_arr[i].setBackground(Color.green);
                        else gpr0_arr[i].setBackground(Color.black);
                    break;
                    case 1:
                        if(cpu.R1[i] == 1)
                            gpr1_arr[i].setBackground(Color.green);
                        else gpr1_arr[i].setBackground(Color.black);
                    break;
                    case 2:
                        if(cpu.R2[i] == 1)
                            gpr2_arr[i].setBackground(Color.green);
                        else gpr2_arr[i].setBackground(Color.black);
                    break;
                    case 3:
                        if(cpu.R3[i] == 1)
                            gpr3_arr[i].setBackground(Color.green);
                        else gpr3_arr[i].setBackground(Color.black);
                    break;
                    case 4:
                        if(cpu.X1[i] == 1)
                            XLabel[0][i].setBackground(Color.green);
                        else XLabel[0][i].setBackground(Color.black);
                    break;
                    case 5:
                        if(cpu.X2[i] == 1)
                            XLabel[1][i].setBackground(Color.green);
                        else XLabel[1][i].setBackground(Color.black);
                    break;
                    case 6:
                        if(cpu.X3[i] == 1)
                            XLabel[2][i].setBackground(Color.green);
                        else XLabel[2][i].setBackground(Color.black);
                    break;
                    case 9:
                        if(cpu.MBR[i] == 1)
                            mbrlab[i].setBackground(Color.blue);
                        else mbrlab[i].setBackground(Color.black);
                    break;
                    default: break;
                }
            }
        else
            for(int i=0;i<12;i++){
                if(buttonpress == 7){
                    if(cpu.PC[i]==1) pclab[i].setBackground(Color.yellow);
                    else pclab[i].setBackground(Color.black);
                }
                else{ if(cpu.MAR[i]==1) marlab[i].setBackground(Color.orange);
                    else marlab[i].setBackground(Color.black);
                }
            }
    }
    private void LoadButtonAction(ActionEvent e){
        JButton j = (JButton)e.getSource();
        int buttonpress = 0;
        for(int i=0;i<10;i++)
            if(j==LDarr[i]) buttonpress = i;
        switch(buttonpress){
            case 0:
                cpu.setR0(cpu.BinaryToDecimal(swarr,16));
                break;
            case 1:
                cpu.setR1(swarr,16);
                break;
            case 2:
                cpu.setR2(swarr,16);
                break;
            case 3:
                cpu.setR3(swarr,16);
                break;
            case 4:
                cpu.setX1(swarr,16);
                break;
            case 5:
                cpu.setX2(swarr,16);
                break;
            case 6:
                cpu.setX3(swarr,16);
                break;
            case 7:
                cpu.setPC(cpu.BinaryToDecimal(swarr, 16));
                break;
            case 8:
                cpu.setMAR(cpu.BinaryToDecimal(swarr, 16));
                break;
            case 9:
                cpu.setMBR(swarr,16);
                break;
            default:
                break;
        }
        RefreshLeds(buttonpress);
    }
    private void switchAction(ActionEvent e){
        JButton j = (JButton)e.getSource();
        int click = 15-Integer.parseInt(j.getLabel());
        //System.out.println(click);
        if(swarr[click]==0){
            swarr[click] = 1;
            j.setBackground(Color.getHSBColor((float)0.0, (float)1.0, (float)1.0));
            j.setForeground(Color.getHSBColor((float)0.5,(float)0.5,(float)0.8));
        }
        else{
            swarr[click] = 0;
            j.setBackground(new JButton().getBackground());
            j.setForeground(Color.black);
        }
        //System.out.println((int)swarr[click]);
    }
    private void Store(ActionEvent e){
        System.out.println("Store Invoked");
        short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
        short value = cpu.BinaryToDecimal(cpu.MBR,16);
        mem.Data[EA] = value;
    }
    private void StorePlus(ActionEvent e){
        System.out.println("Store+ Invoked");
        short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
        short value = cpu.BinaryToDecimal(cpu.MBR,16);
        mem.Data[EA] = value;
        EA++;
        cpu.DecimalToBinary(EA, cpu.MAR, 12);
        RefreshLeds(8);
    }
    private void LoadValue(ActionEvent e){
        System.out.println("Load Invoked");
        try{
            short EA = cpu.BinaryToDecimal(cpu.MAR, 12);
            cpu.DecimalToBinary(mem.Data[EA], cpu.MBR, 16);
            RefreshLeds(9);
        }catch(IndexOutOfBoundsException i){
            JOptionPane.showMessageDialog(this, "Illegal Operation with memory Access","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void runMainLoop(){

        this.setSize(1200, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.getHSBColor((float)0.533, (float)0.8, (float)0.75));
        this.setLayout(null);
        this.setVisible(true);
    }
    
    public void LoadGui(){
        store = new JButton("Store");
        store.addActionListener(e -> Store(e));
        store.setBounds(875,430,70,25);
        this.add(store);
        
        st_plus = new JButton("St+");
        st_plus.addActionListener(e -> StorePlus(e));
        st_plus.setBounds(950,430,70,25);
        this.add(st_plus);
        
        load = new JButton("Load");
        load.addActionListener(e -> LoadValue(e));
        load.setBounds(1025,430,70,25);
        this.add(load);
        
        init = new JButton("Init");
        init.setBounds(1100,430,70,25);
        init.setBackground(Color.RED);
        init.setForeground(Color.white);
        this.add(init);
        
        ss = new JButton("SS");
        ss.setBounds(940,460,55,80);
        run = new JButton("Run");
        run.setBounds(1027,460,65,80);
        run.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        this.add(ss);
        this.add(run);
        
        runMainLoop();
    }
}
