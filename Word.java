
package boggle;
import java.util.ArrayList;
/**
 *
 * @author zjk73
 * 
 * The word Class takes the Array list of tiles and 
 * converts it into the boggle word. It has a point 
 * value along with the tiles and String itself
 */
public class Word 
{
    //Variables
    ArrayList<Tile> tiles;
    String word = "";
    int points;
    
    /**
     * Constructor for word class
     * 
     * @param w 
     */
    public Word(ArrayList<Tile> w)
    {
        for(Tile t : w)
            word += t.getValue();
        points = assignPoints();
        tiles = w;
    }
    
    
    
    /**
     * Private method to determine a words point value
     * 
     * @return point
     */
    private int assignPoints()
    {
        if(word.length() <= 4)
            return 1;
        else if(word.length() <= 5)
            return 2;
        else if(word.length() <= 6)
            return 3;
        else if(word.length() <= 7)
            return 5;
        else
            return 11;
    }
    
    /**
     * Returns the length of the word\
     * 
     * @return length
     */
    public int size()
    {
        return word.length();
    }
    
    /**
     * Returns the list of tiles used to create the word
     * 
     * @return 
     */
    public ArrayList<Tile> getList()
    {
        return tiles;
    }
    
    /**
     * Point getter
     * 
     * @return points 
     */
    public int getPoints()
    {
        return points;
    }
    
    @Override
    public String toString()
    {
        return word;
    }
    
    @Override
    public boolean equals(Object o)
    {
        
        Word other = (Word)o;
        
        return word.equals(o.toString());
        
    }
    
}
