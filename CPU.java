/**
 * CPU - Section for Simulation
 * Designing the CPU structure here
 * All registers and required variables are defined here.
 * @author (Abhinava Phukan)
 * @version (0.1)
 */
public class CPU
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
    char X1[],X2[],X3[]; /** Index Registers **/
    /** ------------------- End of Structure Definition -------------------**/
    /**
     * Define OpCode Inst
     */
    static short HLT = 0x00; /** Stops the Machine **/
    static short LDR = 0x01; 
    /** Load Register From Memory **/
    /** RX <- Value(Effective Address) **/
    static short STR = 0x02; 
    /** Store Register to Memory Location 
      * Value of Effective Address is assigned
      * as value stored in the register
      */
    static short LDA = 0x03; 
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
        
    }
}
