
/**
 * Memory Class - Defining the memory block here
 *
 * @author Abhinava Phukan
 * @version 0.1
 */
public class Memory extends Converter
{
    public short Data[];
    /**
     * 2KB Memory - 1024 * 2 (Memory of 2048 Words)
     * 4KB Memory - 1024 * 4 (Memory of 4096 Words)
    **/
    static int KB2 = 1024 * 2;
    static int KB4 = 1024 * 4;
    public Memory(){
        
        Data = new short[KB2]; 
        /** ^ - Java Does not support unsigned primitive type
         * so we are expanding with int to cover 
         * max unsigned value that can be stored in a largest 16bit number.
         * Value resets to 0
        **/
        for(int i=0;i<KB2;i++){
            Data[i] = 0;
        }
    }
    /**
     * Reset the Memory
     */
    public void Reset(){
        for(int i=0;i<KB2;i++){
            Data[i] = 0;
        }
    }
}
