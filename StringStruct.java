
/**
 * String Structure - 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StringStruct
{
    // instance variables - replace the example below with your own
    private String loc,val;
    /**
     * Constructor for objects of class StringStruct
     */
    public StringStruct(String loc,String val)
    {
        this.loc = loc;
        this.val = val;
    }
    /**
     * Getter and Setter Functions
     */
    public void setLoc(String loc){ this.loc = loc; }
    public void setVal(String val){ this.val = val; }
    public String getLoc(){ return this.loc; }
    public String getVal(){ return this.val; }
}
