/**
 * CPU - Section for Simulation
 * Designing the CPU structure here
 * All registers and required variables are defined here.
 * @author (Abhinava Phukan)
 * @version (0.1)
 */
public class CPU extends Converter
{
    /**
     * Define the Structure of the CPU
     */
    char PC[]; /** Program Counter **/
    char CC[]; /** Condition Code**/
    char IR[]; /** Instruction Register **/
    char MAR[]; /** Memory Address Register **/
    char MBR[]; /** Memory Buffer Register **/
    char MFR[]; /** Machine Fault Register **/
    char R0[],R1[],R2[],R3[]; /** General Purpose Register **/
    char X[][];
    char X1[],X2[],X3[]; /** Index Registers **/
    /** ------------------- End of Structure Definition -------------------**/
    /**
     * Define OpCode Inst
     */
    static final short HLT = 0x00; /** Stops the Machine **/
    static final short LDR = 0x01; 
    /** Load Register From Memory **/
    /** RX <- Value(Effective Address) **/
    static final short STR = 0x02; 
    /** Store Register to Memory Location 
      * Value of Effective Address is assigned
      * as value stored in the register
      */
    static final short LDA = 0x03; 
    /** Load Register with Address
      * Insert the Effective Address Value inside the Register RX.
      */
    /** -------------------End of OpCode Definition --------------------**/
    /**
     * Constructor to Initialize the CPU
     */
    public CPU()
    {
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
                EA += BinaryToDecimal(X1,16);
                break;
            case 3:
                EA += BinaryToDecimal(X1,16);
                break;
            default:
                break;
        }
        return EA;
    }
    /** End Of FetchEA **/
    /**
     * Internal Function That Loads the value in to the specified register
     */
    private void StoreRegister(char rx[],short EA,Memory m){
        byte RVal= (byte)BinaryToDecimal(rx,2);
        switch(RVal){
            case 0:
                DecimalToBinary(m.Data[EA],R0,16);
                break;
            case 1:
                DecimalToBinary(m.Data[EA],R1,16);
                break;
            case 2:
                DecimalToBinary(m.Data[EA],R2,16);
                break;
            case 3:
                DecimalToBinary(m.Data[EA],R3,16);
                break;
        }
    }
    /** End of StoreRegister **/
    /**
     * Store memory from register
     * Data[EA] = Value(RXVal)
     */
    private void MemStore(char rx[],short EA,Memory m){
        byte RVal = (byte)BinaryToDecimal(rx,2);
        switch(RVal){
            case 0:
                m.Data[EA] = BinaryToDecimal(R0,16);
                break;
            case 1:
                m.Data[EA] = BinaryToDecimal(R1,16);
                break;
            case 2:
                m.Data[EA] = BinaryToDecimal(R2,16);
                break;
            case 3:
                m.Data[EA] = BinaryToDecimal(R3,16);
                break;
        }
    }
    /** End of MemStore **/
    /**
     * Internal Function That Loads the Effective address value in to the specified register
     */
    private void StoreRegisterEA(char rx[],short EA){
        byte RVal= (byte)BinaryToDecimal(rx,2);
        switch(RVal){
            case 0:
                DecimalToBinary(EA,R0,16);
                break;
            case 1:
                DecimalToBinary(EA,R1,16);
                break;
            case 2:
                DecimalToBinary(EA,R2,16);
                break;
            case 3:
                DecimalToBinary(EA,R3,16);
                break;
        }
    }
    /** End of StoreRegisterEA **/
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
    }
    /**
     * Execute the Instructions According in the Memory
     */
    public void Execute(Memory m){
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
        short OpCode = BinaryToDecimal(InstOp,6); // Fetch OpCode Value
        
        /**
         * CPU Decision making for executing opcode here
         */
        short EA=FetchEA(IX,Address,m,I);
        switch(OpCode){
            case HLT:
                Reset(m);
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
        }
    }
    /** Getter and Setter Functions for Debugging and future development only **/
    public void setIR(char IR[]){
        for(int i=0;i<16;i++)
            this.IR[i] = IR[i];
    }
    public void setR1(short value){
        DecimalToBinary((int)value,R1,16);
    }
    public void getR0(){
        System.out.printf("R0: ");
        for(int i=0;i<16;i++)
            System.out.printf("%d ",(int)this.R0[i]);
        System.out.println();
    }
    public void getR1(){
        System.out.printf("R1: ");
        for(int i=0;i<16;i++)
            System.out.printf("%d ",(int)this.R1[i]);
        System.out.println();
    }
}
