
/**
 * Converter Class - Numerical Conversions
 *
 * @author (Abhinava Phukan)
 * @version (0.1)
 */
public class Converter
{
    public Converter(){
    }

    /**
     * Convert Binary to a decimal number and returns it
     */
    public short BinaryToDecimal(char Bin[],int length){
        short result=0;
        short base=1;
        for(int i=0; i <length ; i++){
            if(Bin[length-1-i]==1)
                result += base;
            base *= 2;
        }
        return result;
    }
    /**
     * Convert Decimal to a Binary Number
     */
    public void DecimalToBinary(short dec, char[] Bin,int length){
        int c=0;
        int d=dec;
        System.out.println(d);
        if(d<0){
            Bin[0]=1;
            d+=Short.MAX_VALUE+1;
        }
        while(d>=0){
            if((d & 1) == 1)
                Bin[length -1 -c] = 1;
            else
                Bin[length -1 -c] = 0;
            d >>=1;
            c++;
            if(dec <0) Bin[0]=1;
            if(c==length) break;
        }
        
        
        // Handling the Sign Bit Condition
        
    }
}
