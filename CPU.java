import java.io.IOException;

/**
 * CPU - Section for Simulation
 * Designing the CPU structure here
 * All registers and required variables are defined here.
 * @author Abhinava Phukan, Halawani AlHassan
 * @version 1.0
 */
public class CPU extends Converter
{
    /**
     * Define the Structure of the CPU
     */
    /** Program Counter **/
    public char PC[]; 
    /** Condition Code **/
    public char CC[]; 
    /** Instruction Register **/
    public char IR[]; 
    /** Memory Address Register **/
    public char MAR[]; 
    /** Memory Buffer Register **/
    public char MBR[]; 
    /** Memory Fault Register **/
    public char MFR[]; 
    /** General Purpose Register **/
    public char R0[],R1[],R2[],R3[]; 
    /** Index Registers **/
    public char X1[],X2[],X3[]; 
    /** Device Interface **/
    private Devices dev;
    /**
     * Cache Functionality
     */
    public Cache cache;
    /** ------------------- End of Structure Definition -------------------**/
    /**
     * Define OpCode Inst (Abhinava Phukan)
     */
    static final short HLT = 0x00; /** Stops the Machine **/
    /** Trap Code */
    static final short TRAP = 0x18;
    /** Load Register From Memory **/
    /** RX <- Value(Effective Address) **/
    static final short LDR = 0x01; 
    /** Store Register to Memory Location 
      * Value of Effective Address is assigned
      * as value stored in the register
      */
    static final short STR = 0x02; 
    /** Load Register with Address
      * Insert the Effective Address Value inside the Register RX.
      */
    static final short LDA = 0x03; 
    /** Load Index Register with Address
      * Insert the Effective Address Value inside the Index Register XI.
      */
    static final short LDX = 0x21;
    /** Store Index Register to Memory Address
      * Insert the Effective Address Value inside the Index Register XI.
      */
    static final short STX = 0x22;
    /*
     * OpCode Definition by Dev Shah
     */
    static final short AMR = 0x04; // Add Memory To Register
    static final short SMR = 0x05; // Subtract Memory From Register
    static final short AIR = 0x06; // Add  Immediate to Register
    static final short SIR = 0x07; // Subtract Immediate to Register
    /* End of OpCode Definition - Dev Shah */
    /*
     * OpCode Definition by Natalie Jordan
     */
    /**
     * Jump if zero
     */
    static final short JZ = 0x08;
    
    /**
     * Jump if not equal
     */
    static final short JNE = 0x09;
    
    /**
     * Jump if condition code
     */
    static final short JCC = 0x0A;
    
    /**
     * Unconditional jump to address
     */
    static final short JMA = 0x0B;
    /* End of Opcode Definition by Natalie Jordan */
    // ---------------------------------------------
    /* OpCode Definition by Alhassan Halawani */
    /**
     * Jump and Save return address
     * 
     */
    static final short JSR = 0x0C;
    /**
     * Return From Subroutine w/ return code as Immed
     * portion (optional) stored in the instruction’s
     * address field.
     * R0 <- Immed; PC <- c(R3)
     * IX, I fields are ignored.
     */
    static final short RFS = 0x0D;
    /**
     * Subtract One and Branch Register
     */
    static final short SOB = 0x0E;
    /**
     * Jump Greater Than Equal
     */
    static final short JGE = 0x0F;
    /* End of OpCode Definition by Alhassan Halawani */
    /**
     * OpCode Definition by Abhinava Phukan
     */
    /**
     *  Multiplication Instruction 
     */
    static final short MLT = 0x10; 
    /**
     *  Division Instruction 
     */
    static final short DVD = 0x11; 
    /**
     *  Register Equality Instruction 
     */
    static final short TRR = 0x12; 
    /**
     *  AND operator Instruction 
     */
    static final short AND = 0x13; 
    /**
     *  OR Operator Instruction 
     */
    static final short ORR = 0x14; 
    /**
     *  Not Operator Instruction 
     */
    static final short NOT = 0x15; // BitWise NOT Operator
    /**
     * Shift Register by Count
     */
    static final short SRC = 0x19; // Shift Register by Count
    /**
     * Rotate Register by Count
     */
    static final short RRC = 0x1A; // Rotate Register by Count
    /**
     * Read from Device
     */
    static final short IN = 0x31;
    /**
     * Write to Device
     */
    static final short OUT = 0x32;
    /**
     * Check Device Status
     */
    static final short CHK = 0x33;
    // End Of OpCode Definition By Abhinava Phukan
    /** -------------------End of OpCode Definition --------------------**/
    /**
     * Constructor to Initialize the CPU
     */
    public CPU(Devices dev)
    {
        this.dev = dev;
        PC = new char[12];
        CC = new char[4];
        IR = new char[16];
        MAR = new char[12];
        MBR = new char[16];
        MFR = new char[4];
        X1 = new char[16];
        X2 = new char[16];
        X3 = new char[16];
        R0 = new char[16];
        R1 = new char[16];
        R2 = new char[16];
        R3 = new char[16];
        // Initializing the State of the Machine to 0
        for(int i=0;i<12;i++){
            PC[i] = MAR[i] = 0;
        }
        for(int i=0;i<4;i++){
            CC[i] = 0;
            MFR[i] = 0;
        }
        for(int i=0;i<16;i++){
            IR[i] = 0;
            MBR[i] = 0;
            X1[i] = X2[i] = X3[i] = 0;
            R0[i] = R1[i] = R2[i] = R3[i] = 0;
        }
        cache = new Cache();
    }
    public void CopyArr(char src[],char des[],int len){
        for(int i=0;i<len;i++)
            des[i] = src[i];
    }
    /**
     * Internal Function that fetches the Effective address effectively
     */
    private short FetchEA(char ix[], char addr[], Memory m,char I){
        byte IXVal = (byte)BinaryToDecimal(ix,2);
        short EA = BinaryToDecimal(addr,5);
        switch(IXVal){
            case 0:
                break;
            case 1:
                EA += BinaryToDecimal(X1,16);
                break;
            case 2:
                EA += BinaryToDecimal(X2,16);
                break;
            case 3:
                EA += BinaryToDecimal(X3,16);
                break;
            default:
                break;
        }
        if(I==1) {
            m.Data[6] = EA;
            return m.Data[EA];
        }
        return EA;
    }
    /** End Of FetchEA **/
    /**
     * Internal Function That Loads the value in to the specified register
     */
    private void StoreRegister(char rx[],short EA,Memory m){
        byte RVal= (byte)BinaryToDecimal(rx,2);
        DecimalToBinary(EA,MAR,12);
        DecimalToBinary(m.Data[EA],MBR,16);
        switch(RVal){
            case 0:
                CopyArr(MBR, R0, 16);
                break;
            case 1:
                CopyArr(MBR, R1, 16);
                break;
            case 2:
                CopyArr(MBR, R2, 16);
                break;
            case 3:
                CopyArr(MBR, R3, 16);
                break;
        }
    }
    /** End of StoreRegister **/
    /**
     * Internal Function That Loads the value from memory 
     * into the specified index register
     */
    private void StoreIndexRegister(char ix[],short EA,Memory m){
        byte RVal= (byte)BinaryToDecimal(ix,2);
        DecimalToBinary(EA,MAR,12);
        DecimalToBinary(m.Data[EA],MBR,16);
        switch(RVal){
            case 0:
                break;
            case 1:
                CopyArr(MBR, X1, 16);
                break;
            case 2:
                CopyArr(MBR, X2, 16);
                break;
            case 3:
                CopyArr(MBR, X3, 16);
                break;
            default: break;
        }
    }
    /** End of StoreIndexRegister **/
    /**
     * Store memory from register
     * Data[EA] = Value(RXVal)
     */
    private void MemStore(char rx[],short EA,Memory m){
        byte RVal = (byte)BinaryToDecimal(rx,2);
        char[] R_x = getRegister((short)RVal);
        DecimalToBinary(EA,MAR,12);
        CopyArr(R_x, MBR, 16);
        if(EA>=0 && EA<=9) { 
            MFR[3]=1;
            m.Data[4] = BinaryToDecimal(PC, 12);
            return;
        } 
        m.Data[EA] = BinaryToDecimal(MBR,16);
    }
    /** End of MemStore **/
    /**
     * Store memory from index register
     * Data[EA] = Value(IXVal)
     */
    private void MemStoreFromIndex(char ix[],short EA,Memory m){
        byte XVal = (byte)BinaryToDecimal(ix,2);
        DecimalToBinary(EA,MAR,12);
        switch(XVal){
            case 0:
                break;
            case 1:
                CopyArr(X1, MBR, 16);
                break;
            case 2:
                CopyArr(X2, MBR, 16);
                break;
            case 3:
                CopyArr(X3, MBR, 16);
                break;
        }
        if(EA>=0 && EA<=9) { 
            MFR[3]=1;
            m.Data[4] = BinaryToDecimal(PC, 12);
            return;
        } 
        m.Data[EA] = BinaryToDecimal(MBR,16);
    }
    /** End of MemStoreToIndex **/
    /**
     * Internal Function That Loads the Effective address value in to the specified register
     */
    private void StoreRegisterEA(char rx[],short EA){
        byte RVal= (byte)BinaryToDecimal(rx,2);
        char[] R_x = getRegister((short)RVal);
        DecimalToBinary(EA,MAR,12);
        ReverseCopyArr(MAR, R_x, 16, 12);
        // switch(RVal){
        //     case 0:
        //         ReverseCopyArr(MAR, R0, 16, 12);
        //         break;
        //     case 1:
        //         ReverseCopyArr(MAR, R1, 16, 12);
        //         break;
        //     case 2:
        //         ReverseCopyArr(MAR, R2, 16, 12);
        //         break;
        //     case 3:
        //         ReverseCopyArr(MAR, R3, 16, 12);
        //         break;
        // }
    }
    /** End of StoreRegisterEA **/
    public void ReverseCopyArr(char src[],char des[],int length,int srclen){
        for(int i=0;i<srclen;i++)
            des[length-i-1] = src[srclen-i-1];
    }
    /**
     * Reset The Machine state (Useful in Halting)
     */
    public void Reset(Memory m){
        for(int i=0;i<12;i++){
            PC[i] = MAR[i] = 0;
        }
        for(int i=0;i<4;i++){
            CC[i] = 0;
            MFR[i] = 0;
        }
        for(int i=0;i<16;i++){
            IR[i] = 0;
            MBR[i] = 0;
            X1[i] = X2[i] = X3[i] = 0;
            R0[i] = R1[i] = R2[i] = R3[i] = 0;
        }
        for(int i=0;i<m.Data.length;i++)
            m.Data[i]=0;
    }
    /**
     * Execute the Instructions According in the Memory
     */
    public void Execute(Memory m,Devices d){
        /**
         * Segregating the Data
         */
        char InstOp[] = new char[6];
        for(int i=0;i<6;i++) InstOp[i] = IR[i];
        char RX[] = new char[2];
        for(int i=6;i<8;i++) RX[i-6] = IR[i];
        char IX[] = new char[2];
        for(int i=8;i<10;i++) IX[i-8] = IR[i];
        char I = IR[10];
        char Address[] = new char[5];
        for(int i=11;i<16;i++) Address[i-11] = IR[i];
        char Count[] = new char[4];
        for(int i=12;i<16;i++) Count[i-12] = IR[i];
        short OpCode = BinaryToDecimal(InstOp,6); // Fetch OpCode Value
        System.out.printf("OpCode: 0x%-2x\n",OpCode);
        try {
            dev.printCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * CPU Decision making for executing opcode here
         */
        short EA=FetchEA(IX,Address,m,I);
        try{
            switch(OpCode){
                case HLT:
                    break;
                case LDR:
                    StoreRegister(RX,EA,m);
                    break;
                case STR:
                    MemStore(RX,EA,m);
                    break;
                case LDA:
                    StoreRegisterEA(RX,EA);
                    break;
                case LDX:
                    StoreIndexRegister(IX, EA, m);
                    break;
                case STX:
                    MemStoreFromIndex(IX, EA, m);
                    break;            
                case AMR:
                    fAMR(BinaryToDecimal(RX, 2), EA,m);
                    break;
                case SMR:
                    fSMR(BinaryToDecimal(RX, 2), EA,m);
                    break;
                case AIR:
                    fAIR(BinaryToDecimal(RX, 2), BinaryToDecimal(Address, 5));
                    break;
                case SIR:
                    fSIR(BinaryToDecimal(RX, 2), BinaryToDecimal(Address, 5));
                    break;
                case JZ:
                    JumpZero((BinaryToDecimal(RX, 2)),EA);
                    break;
                case JNE:
                    JumpIfNotEqual((BinaryToDecimal(RX, 2)),EA);
                    break;    
                case JCC:
                    JumpIfCond(BinaryToDecimal(RX, 2),EA);
                    break;
                case JMA:
                    UncondJump(EA);
                    break;
                case JSR: 
                    JumpSubRoutine(EA); 
                    break;
                case RFS: 
                    RFSImmed(Address); 
                    break;
                case SOB: 
                    SubandBranch(BinaryToDecimal(RX, 2), EA);
                    break;
                case JGE: 
                    JumpGE(BinaryToDecimal(RX, 2), EA); 
                    break;
                case MLT:
                    fMLT(BinaryToDecimal(RX, 2), BinaryToDecimal(IX, 2));
                    break;
                case DVD:
                    fDVD(BinaryToDecimal(RX, 2), BinaryToDecimal(IX, 2));
                    break;
                case TRR:
                    fTRR(BinaryToDecimal(RX, 2), BinaryToDecimal(IX, 2));
                    break;
                case AND:
                    fAND(BinaryToDecimal(RX, 2), BinaryToDecimal(IX, 2));
                    break;
                case ORR:
                    fORR(BinaryToDecimal(RX, 2), BinaryToDecimal(IX, 2)); 
                    break;
                case NOT:
                    fNOT(BinaryToDecimal(RX, 2));
                    break;
                case TRAP:
                    fTrap(BinaryToDecimal(Count,4),m);
                    break;
                case SRC:
                    fSRC(BinaryToDecimal(RX, 2),BinaryToDecimal(Count, 4),
                        (byte)IR[9],(byte)IR[8]);
                    break;
                case RRC:
                    fRRC(BinaryToDecimal(RX, 2),BinaryToDecimal(Count, 4),
                        (byte)IR[9],(byte)IR[8]);
                    break;
                case IN:
                    fIN(BinaryToDecimal(RX, 2), (byte)BinaryToDecimal(Address,5)
                                        , dev);
                    break;
                case OUT:
                //System.out.println((byte)BinaryToDecimal(Address, 5));
                    fOUT(BinaryToDecimal(RX, 2), (byte)BinaryToDecimal(Address,5)
                                        , dev);
                    break;
                case CHK:
                    fCHK(BinaryToDecimal(RX, 2), (byte)BinaryToDecimal(Address, 5)
                    , dev);
                    break;
                default: 
                    MFR[1]=1;
                    m.Data[4] = BinaryToDecimal(PC, 12);
                    break;
            }
        }catch(IndexOutOfBoundsException ioobe){
            MFR[0]=1;
            m.Data[4] = BinaryToDecimal(PC, 12);
        }
        m.Data[1] = BinaryToDecimal(MFR, 4);
    }
    /**
     * Handle Machine Fault
     * @param m Memory fault to handle.
     */
    public void MFHandle(Memory m){
        if(MFR[0]==1){
            DecimalToBinary((short)10, PC, 12);
        }
        MFR[0] = MFR[1] = MFR[2] = MFR[3] = 0;
        m.Data[4] = BinaryToDecimal(PC, 12);
    }
    /**
     * OPCode Implementation Natatlie Jordan
     */
    /**
     * Jump if zero
     * @param rx Register to check for JMP condition
     * @param EA Effective Address to PC
     */
    public void JumpZero(short rx,short EA){
        short val;char[] R_x=getRegister(rx);
        val = BinaryToDecimal(R_x, 16);
        if(val==0){
            DecimalToBinary(EA, PC, 12);
        }else {
            DecimalToBinary((short)
            (BinaryToDecimal(PC, 12)+1), PC, 12);
        }
        
    }
    /**
     * Jump if Not Equal to if True
     * @param rx Register to check for JMP condition
     * @param EA Effective Address to PC
     */
    public void JumpIfNotEqual(short rx,short EA){
        short val;
        char[] R_x = getRegister(rx);
        val=BinaryToDecimal(R_x, 16);
        if (val != 0){
            DecimalToBinary(EA, PC, 12);
        } else {
            DecimalToBinary((short)
            (BinaryToDecimal(PC, 12)+1), PC, 12);
        }
    }
    /**
     * Jump if condition code
     * @param cc Which control flag is triggered
     * @param EA effective Address
     */
    public void JumpIfCond(short cc,short EA){
        if ( CC[cc] == 1){
            DecimalToBinary(EA, PC, 12);
            CC[cc]=0;
        } else {
            DecimalToBinary((short)
            (BinaryToDecimal(PC, 12)+1), PC, 12);
        }
    }
    /**
     * Unconditional Jump to Address
     * @param EA
     */
    public void UncondJump(short EA){
        DecimalToBinary(EA, PC, 12);
    }
    /* End Implementation of OpCode Method - Natalie Jordan */
    /* Implementation of OpCode Method - AlHassan Halawani */
    public void JumpSubRoutine(short EA){
        DecimalToBinary(
            (short)(BinaryToDecimal(PC, 12)+1),
            R3, 16);
        DecimalToBinary(EA, PC, 12);
        DecimalToBinary((short)(EA+1), R0, 16);

    }
    public void RFSImmed(char Addr[]){
        ReverseCopyArr(Addr, R0, 16, 5);
        ReverseCopyArr(R3, PC, 12, 16);
    }
    public void SubandBranch(short rx,short EA){
        short val,flag=0;
        char[] R_x = getRegister(rx);
        val=BinaryToDecimal(R_x, 16);
        if(val>0)flag=1;
        if(flag==1) DecimalToBinary(EA, PC, 12);
        else DecimalToBinary((short)(BinaryToDecimal(PC, 12)+1),
         PC, 12);
    }
    /**
     * Jump Greater than equal to if True
     * @param rx Register to check for JMP condition
     * @param EA Effective Address to PC
     */
    public void JumpGE(short rx,short EA){
        char[] R_x = getRegister(rx);
        short val=BinaryToDecimal(R_x, 16);
        if(val>=0){
            DecimalToBinary(EA, PC, 12);
        }else
            DecimalToBinary((short)(BinaryToDecimal(PC, 12)+1),
                PC, 12);
    }
    /* End Implementation of OpCode Method - AlHassan Halawani */
    
    /* Implmentation of Methods For Other OpCode - Dev Shah */
    public void fAMR(short RVal,short EA, Memory m){
        char[] R_x = getRegister(RVal);
        short result = (short)(BinaryToDecimal(R_x, 16) + m.Data[EA]);
        DecimalToBinary(result, R_x, 16);
    }
    public void fSMR(short RVal,short EA, Memory m){
        char[] R_x = getRegister(RVal);
        short result = (short)(BinaryToDecimal(R_x, 16) - m.Data[EA]);
        DecimalToBinary(result, R_x, 16);
    }
    public void fAIR(short RVal,short Addr){
        char[] R_x = getRegister(RVal);
        short result = (short)(BinaryToDecimal(R_x, 16) + Addr);
        DecimalToBinary(result, R_x, 16);
    }
    public void fSIR(short RVal,short Addr){
        char[] R_x = getRegister(RVal);
        short result = (short)(BinaryToDecimal(R_x, 16) - Addr);
        DecimalToBinary(result, R_x, 16);
    }

    /* Implmentation of Methods For Other OpCode - Abhinava Phukan */
    /**
     * Method for the Multiplication OpCode
     * @param rx for Which register to use (R0/R2)
     * @param ry for Which register to use (R0/R2)
     */
    public void fMLT(short rx,short ry){
        if( rx%2==1 || ry%2==1) return ;
        if(rx==0){
            short result=BinaryToDecimal(R0, 16);
            if(ry==0) result *= result;
            else result *= BinaryToDecimal(R2, 16);
            DecimalToBinary(result, R1, 16);
        }else if(rx==2){
            short result = BinaryToDecimal(R2, 16);
            if(ry==0) result *= BinaryToDecimal(R0, 16);
            else result *= result;
            DecimalToBinary(result, R3, 16);
        }
    }
    /**
     * Method for the Division OpCode
     * @param rx for Which register to use (R0/R2)
     * @param ry for Which register to use (R0/R2)
     */
    public void fDVD(short rx,short ry){
        if( rx%2==1 || ry%2==1) return ;
        if(rx==0){
            short result=BinaryToDecimal(R0, 16);
            try{
                short rem=0;
                if(ry==0) {
                    rem+= result % BinaryToDecimal(R0,16);
                    result /= BinaryToDecimal(R0, 16);
                }
                else { 
                    rem+= result % BinaryToDecimal(R2,16);
                    result /= BinaryToDecimal(R2, 16); 
                }
                DecimalToBinary(rem, R1, 16);
                DecimalToBinary(result, R0, 16);
            }catch(ArithmeticException E){
                CC[2]=1;
            }
        }else if(rx==2){
            short result=BinaryToDecimal(R2, 16);
            try{
                short rem=0;
                if(ry==0) {
                    rem+= result % BinaryToDecimal(R0,16);
                    result /= BinaryToDecimal(R0, 16);
                }
                else { 
                    rem+= result % BinaryToDecimal(R2,16);
                    result /= BinaryToDecimal(R2, 16); 
                }
                DecimalToBinary(rem, R2, 16);
                DecimalToBinary(result, R3, 16);
            }catch(ArithmeticException E){
                CC[2]=1;
            }
        }
    }
    /**
     * Method for the Equality OpCode
     * @param rx for Which register to use (R0/R1/R2/R3)
     * @param ry for Which register to use (R0/R1/R2/R3)
     */
    public void fTRR(short rx,short ry){
        char[] R_x=getRegister(rx),
        R_y=getRegister(ry);
        short rxval = BinaryToDecimal(R_x,16);
        short ryval = BinaryToDecimal(R_y,16);
        if(rxval==ryval) CC[3]=1;
    }
    /**
     * Method for the AND Operator OpCode
     * @param rx for Which register to use (R0/R1/R2/R3)
     * @param ry for Which register to use (R0/R1/R2/R3)
     */
    public void fAND(short rx,short ry){
        char[] R_x=getRegister(rx),
        R_y=getRegister(ry);
        for(int i=0;i<16;i++){
            R_x[i] = (char)(R_x[i] & R_y[i]);
        }
    }
    /**
     * Method for the OR Operator OpCode
     * @param rx for Which register to use (R0/R1/R2/R3)
     * @param ry for Which register to use (R0/R1/R2/R3)
     */
    public void fORR(short rx,short ry){
        char[] R_x=getRegister(rx),
        R_y=getRegister(ry);
        for(int i=0;i<16;i++){
            R_x[i] = (char)(R_x[i] | R_y[i]);
        }
    }
    /**
     * Method for the NOT Operator OpCode
     * @param rx for Which register to use (R0/R1/R2/R3)
     */
     public void fNOT(short rx){
        char[] R_x=getRegister(rx);
        for(int i=0;i<16;i++){
            if(R_x[i] == 0) R_x[i] = 1;
            else if(R_x[i] == 1) R_x[i] = 0;
        }
    }
    /**
     * Trap Code Instruction
     * @param trapCode Input the Trap Code
     */
    public void fTrap(short trapCode,Memory m){
        m.Data[0]=trapCode;
        short Value = (short)(BinaryToDecimal(PC, 12)+1);
        m.Data[2] = Value;
        //DecimalToBinary(m.Data[2], PC, 12);
        switch(trapCode){
            // Handle Trap Code here
        }
    }
    /**
     * Shift Register By Count
     * @param rx
     * @param count
     * @param LR
     * @param AL
     */
    public void fSRC(short rx,short count,byte LR,byte AL){
        char[] R_x=getRegister(rx);
        short val = BinaryToDecimal(R_x, 16);
        if(AL==1){    
            switch(LR){
                case 0:
                    val = (short)(val>>count); // Right Shift
                    DecimalToBinary(val, R_x, 16);
                    break;
                case 1:
                    val = (short)(val<<count); // Left Shift
                    DecimalToBinary(val, R_x, 16);
                    break;
            }
        }else{
            // Arithmetic Shift
            switch(LR){
                case 0:
                    val = (short)(val>>count); // Right Shift
                    DecimalToBinary(val, R_x, 16);
                    R_x[0] = R_x[1];
                    break;
                case 1:
                    val = (short)(val<<count); // Left Shift
                    DecimalToBinary(val, R_x, 16);
                    break;
            }
        }
    }
    /**
     * Rotate Register by Count
     * @param rx
     * @param count
     * @param LR
     * @param AL
     */
    public void fRRC(short rx,short count,byte LR,byte AL){
        char[] R_x=getRegister(rx);
        //short val = BinaryToDecimal(R_x, 16);
        if(AL==1){
            switch(LR){
                case 0:
                    for(int i=0;i<16;i++){
                        char temp = R_x[(i+count)%16];
                        R_x[(i+count)%16] = R_x[i];
                        R_x[i] = temp;
                    }
                    break;
                case 1:
                    for(int i=15;i>=0;i++){
                        char temp = R_x[(i+count)%16];
                        R_x[(i+count)%16] = R_x[i];
                        R_x[i] = temp;
                    }
            }
        }
    }
    /**
     * Read from Device to Register
     * @param rx Register to write read data into.
     * @param devid Device id of the connected Device
     * @param dev Device interface to use for connection
     */
    public void fIN(short rx,byte devid,Devices dev){
        char Rx[] = getRegister(rx);
        switch(devid){
            case 0:
                dev.keyboard(Rx);
                break;
        }
    }
    /**
     * Write from Register to Device
     * @param rx Register where the write data is stored.
     * @param devid Device id of the connected Device
     * @param dev Device interface to use for connection
     */
    public void fOUT(short rx,byte devid,Devices dev){
        char Rx[] = getRegister(rx);
        switch(devid){
            case 1:
                dev.printer(Rx);
                break;
        }
    }
    public void fCHK(short rx,byte devid,Devices dev){
        char Rx[] = getRegister(rx);
        switch(devid){

        }
    }
    /* END of Implmentation of Methods For Other OpCode - Abhinava Phukan */
    /** Getter and Setter Functions for Debugging and future development only **/
    public char[] getIR() {
        return IR;
    }
    public char[] getMAR(){
        return MAR;
    }
    public void getR0(){
        for(int i=0;i<16;i++)
            System.out.printf("%d ",(int)R0[i]);
        System.out.println();
    }
    public char[] getR1() {
        return R1;
    }
    public char[] getR2(){
        return R2;
    }
    public char[] getR3() {
        return R3;
    }
    public void setIR(char IR[]){
        for(int i=0;i<16;i++)
            this.IR[i] = IR[i];
    }
    public void setR0(short value){
        DecimalToBinary(value, R0, 16);
    }
    public void setR1(char arr[],int len){
        CopyArr(arr,R1,16);
    }
    public void setR2(char arr[],int len){
        CopyArr(arr,R2,16);
    }
    public void setR3(char arr[],int len){
        CopyArr(arr,R3,16);
    }
    public void setX1(char arr[],int len){
        CopyArr(arr,X1,16);
    }
    public void setX2(char arr[],int len){
        CopyArr(arr,X2,16);
    }
    public void setX3(char arr[],int len){
        CopyArr(arr,X3,16);
    }
    public void setPC(short value){ 
        DecimalToBinary(value, PC, 12);
    }
    public void setMAR(short value){ 
        DecimalToBinary(value, MAR, 12);
    }
    public void setMBR(char arr[],int len){ 
        CopyArr(arr,MBR,16);
    }
    public char[] getMBR(){
        return MBR;
    }
    public char[] getRegister(short rx){
        switch(rx){
            case 0: return R0; 
            case 1: return R1; 
            case 2: return R2; 
            case 3: return R3; 
            default: return R0; 
        }
    }
}