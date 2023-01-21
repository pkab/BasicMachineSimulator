
/**
 * Memory Class - Defining the memory block here
 *
 * @author Abhinava Phukan
 * @version 0.1
 */
public class Memory
{
    public int Data[];
    /**
     * 4KB Memory - 1024 * 4 (Memory of 2048 Words)
     * 8KB Memory - 1024 * 8 (Memory of 4096 Words)
    **/
    static int KB4 = 1024 * 4;
    static int KB8 = 1024 * 8;
    public Memory(){
        
        Data = new int[KB4]; 
        /** ^ - Java Does not support unsigned primitive type
         * so we are expanding with int to cover 
         * max unsigned value that can be stored in a largest 16bit number.
         * Value resets to 0
        **/
        for(int i=0;i<KB4;i++){
            Data[i] = 0;
        }
    }
    /**
     * Reset the Memory
     */
    public void Reset(){
        for(int i=0;i<KB4;i++){
            Data[i] = 0;
        }
    }
}
