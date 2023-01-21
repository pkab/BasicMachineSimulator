
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
     * 
     */
    public short BinaryToDecimal(char Bin[],int length){
        short result=0;
        short base=1;
        for(int i=length - 1; i >=0 ; i--){
            if(Bin[i]==1)
                result += base;
            base *= 2;
        }
        return result;
    }
    public void DecimalToBinary(int dec, char[] Bin,int length){
        int c=0;
        while(dec>0){
            if((dec & 1) == 1)
                Bin[length - c -1] = 1;
            else
                Bin[length - c -1] = 0;
            dec >>=1;
            c++;
            if(c==length) break;
        }
    }
}
