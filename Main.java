
/**
 * Main Class
 * Here is the Entire Simulator Environment going to start
 * @author (Abhinava Phukan, Natalie Jordan, Dev Shah, Alhassan)
 * @version (1.0)
 */
public class Main extends Converter
{
    public Main(){
        
    }

    public static void main(String args[]){
        CPU cpu = new CPU();
        Memory mem = new Memory();
        mem.Data[63]=20;
        char IR[] = {0,0,0,0,0,1,0,0,1,0,0,1,1,1,1,1};
        cpu.X2[10]=1;
        //cpu.DecimalToBinary(63, cpu.MAR, 12);
        cpu.setIR(IR);
        cpu.Execute(mem);
        cpu.getMAR();
        cpu.getMBR();
        System.out.println("Testing LDR OpCode");
        cpu.getR0();
        IR[4] = IR[5] = 1;
        cpu.setIR(IR);
        cpu.Execute(mem);
        cpu.getMAR();
        cpu.getMBR();
        System.out.println("Testing LDA OpCode");
        cpu.getR0();
        IR[5]=0;
        IR[7]=1;
        cpu.setIR(IR);
        short a=50;
        cpu.setR1(a);
        cpu.getR1();
        cpu.Execute(mem);
        cpu.getMAR();
        cpu.getMBR();
        System.out.println("STR Opcode Result Memory Location:" + mem.Data[63]);
    }
}
