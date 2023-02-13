/**
 * Converter Class - Numerical Conversions
 *
 * @author Abhinava Phukan
 * @version 1.0
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
     * Convert Decimal to a Binary Number in an Array
     */
    public void DecimalToBinary(short dec, char[] Bin,int length){
        int c=0;
        int d=dec;
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
    }
    public short[] HexToDecimal(String hex_loc,String hex_val){
        /** To be implemented by Dev **/
        
        int len = hex_loc.length();  //Length should be 4 for both loc & val
        int base = 1;
        int dec_loc = 0;
        int dec_val = 0;
        short[] instr = new short[2];
        for (int i = len - 1; i >= 0; i--) {
            if (hex_loc.charAt(i) >= '0' && hex_loc.charAt(i) <= '9') {
                dec_loc += (hex_loc.charAt(i) - 48) * base;
                base = base * 16;
            }
            else if (hex_loc.charAt(i) >= 'A' && hex_loc.charAt(i) <= 'F') {
                dec_loc += (hex_loc.charAt(i) - 55) * base;
                base = base * 16;
            }
        }
        for (int i = len - 1; i >= 0; i--) {
            if (hex_val.charAt(i) >= '0' && hex_val.charAt(i) <= '9') {
                dec_val += (hex_val.charAt(i) - 48) * base;
                base = base * 16;
            }
            else if (hex_val.charAt(i) >= 'A' && hex_val.charAt(i) <= 'F') {
                dec_val += (hex_val.charAt(i) - 55) * base;
                base = base * 16;
            }
        }
        instr[0] = dec_loc;
        instr[1] = dec_val;
        return instr;
    }
}
