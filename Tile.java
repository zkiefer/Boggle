
package boggle;

import java.util.Objects;

/**
 *
 * @author zjk73
 */
public class Tile 
{
    //Variables
    private String value;
    private int row;
    private int col;
    private boolean flag;
    
    /**
     * Constructor for Tile, v = value, r = row, c = column
     * 
     * @param v
     * @param r
     * @param c 
     */
    public Tile(String v, int r, int c)
    {
        value = v;
        row = r;
        col = c;
        flag = false;
    }
    
    /**
     * Secondary Constructor for if user inputs a char for value
     * 
     * @param ch
     * @param r
     * @param c 
     */
    public Tile(char ch, int r, int c)
    {
        value = ch + "";
        row = r;
        col = c;
        flag = false;
    }
    
    /**
     * Get's value
     * 
     * @return value
     */
    public String getValue()
    {
        return value;
    }
    
    /**
     * Get's row
     * 
     * @return row
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Get's column
     * 
     * @return col
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Sets if tile has been used or not
     * 
     * @param f 
     */
    public void setFlag(boolean f)
    {
        flag = f;
    }
    
    /**
     * Returns if tile was used
     * 
     * @return flag
     */
    public boolean getFlag()
    {
        return flag;
    }
    
    @Override
    public String toString()
    {
        return value;
    }
    
    @Override
    public boolean equals(Object o)
    {
        Tile other = (Tile)o;
        if(!other.getValue().equals(value))
            return false;
        if(!(other.getRow() == row))
            return false;
        if(!(other.getCol() == col))
            return false;
        return true;
    }

}
