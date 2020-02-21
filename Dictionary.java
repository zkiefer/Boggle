
package boggle;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author zjk73
 */
public class Dictionary {

    //Variables
    ArrayList<String> dict = new ArrayList<>();
    
    /**
     * Constructor for Dictionary class
     * 
     * @param address 
     */
    public Dictionary(String address)
    {
        try
        {
            File file = new File(address);
            Scanner s = new Scanner(file);
            
            
            while(s.hasNext())
            {
                dict.add(s.nextLine());
            }
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File can not be found");
        }
        
    }
    
    /**
     * Takes an array list of tiles and checks to see if the tiles create a valid word
     * 
     * @param w Tile list
     * @return if Valid
     */
    public boolean isValidWord(ArrayList<Tile> w)
    {
        String s = "";
        for(Tile e : w)
        {
            s += e.getValue();
        }
        
        for(String key : dict)
        {
            if(s.equalsIgnoreCase(key))
                return true;
        }
        return false;
    }
}
