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
    public char PC[]; /** Program Counter **/
    public char CC[]; /** Condition Code**/
    public char IR[]; /** Instruction Register **/
    public char MAR[]; /** Memory Address Register **/
    public char MBR[]; /** Memory Buffer Register **/
    public char MFR[]; /** Machine Fault Register **/
    public char R0[],R1[],R2[],R3[]; /** General Purpose Register **/
    public char X[][];
    public char X1[],X2[],X3[]; /** Index Registers **/
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
     * Store memory from register
     * Data[EA] = Value(RXVal)
     */
    private void MemStore(char rx[],short EA,Memory m){
        byte RVal = (byte)BinaryToDecimal(rx,2);
        DecimalToBinary(EA,MAR,12);
        switch(RVal){
            case 0:
                CopyArr(R0, MBR, 16);
                break;
            case 1:
                CopyArr(R1, MBR, 16);
                break;
            case 2:
                CopyArr(R2, MBR, 16);
                break;
            case 3:
                CopyArr(R3, MBR, 16);
                break;
        }
        m.Data[EA] = BinaryToDecimal(MBR,16);
    }
    /** End of MemStore **/
    public void ReverseCopyArr(char src[],char des[],int length,int srclen){
        for(int i=0;i<srclen;i++)
            des[length-i-1] = src[srclen-i-1];
    }
    /**
     * Internal Function That Loads the Effective address value in to the specified register
     */
    private void StoreRegisterEA(char rx[],short EA){
        byte RVal= (byte)BinaryToDecimal(rx,2);
        DecimalToBinary(EA,MAR,12);
        switch(RVal){
            case 0:
                ReverseCopyArr(MAR, R0, 16, 12);
                break;
            case 1:
                ReverseCopyArr(MAR, R1, 16, 12);
                break;
            case 2:
                ReverseCopyArr(MAR, R2, 16, 12);
                break;
            case 3:
                ReverseCopyArr(MAR, R3, 16, 12);
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
                //Reset(m);
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
    
}