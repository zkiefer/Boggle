
package boggle;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author zjk73
 * 
 * The board class creates a randomized board for the game boggle
 */
public class Board 
{
    //Variables
    Tile[][] board = new Tile[4][4];
    String[][] die = {{"R", "I", "F", "O", "B", "X"},
        {"I", "F", "E", "H", "E", "Y"},
        {"D", "E", "N", "O", "W", "S"},
        {"U", "T", "O", "K", "N", "D"},
        {"H", "M", "S", "R", "A", "O"},
        {"L", "U", "P", "E", "T", "S"},
        {"A", "C", "I", "T", "O", "A"},
        {"Y", "L", "G", "K", "U", "E"},
        {"Qu","B", "M", "J", "O", "A"},
        {"E", "H", "I", "S", "P", "N"},
        {"V", "E", "T", "I", "G", "D"},
        {"B", "A", "L", "I", "Y", "T"},
        {"E", "X", "A", "V", "N", "D"},
        {"R", "A", "L", "E", "S", "C"},
        {"U", "W", "I", "L", "R", "G"},
        {"P", "A", "C", "E", "M", "D"}};
    Random dice = new Random();
    Random side = new Random();
    
    //For recursive search
    ArrayList<Tile> word = new ArrayList();
    
    /**
     * Board constructor
     */
    public Board()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int e = 0; e < board[0].length; e++)
            {
                board[i][e] = new Tile(die[dice.nextInt(16)][side.nextInt(6)], i, e);
            }
        }
    }
    
    /**
     * Returns a tile at given space
     * 
     * @param row
     * @param col
     * @return Tile
     */
    public Tile getTile(int row, int col)
    {
        return board[row][col];
    }
    
    /**
     * Resets the flagged status of all tiles on the board
     */
    public void resetFlags()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int e = 0; e < board[0].length; e++)
            {
                board[i][e].setFlag(false);
            }
        }
    }
    
    @Override
    public String toString()
    {
        String s = "";
        for(Tile[] e : board)
        {
            for(Tile t : e)
                s += t.getValue() + " ";
            s += "\n";
        }
        
        return s;
    }
    
    /**
     * Takes in a key word and determines if word is on the board
     * 
     * @param key
     * @return result
     */
    public Word findWord(String key)
    {
        boolean ret = false;
        key = key.toUpperCase();
        //Creates array lists to store row and column data
        ArrayList rows = new ArrayList();
        ArrayList cols = new ArrayList();
        
        word.clear();
        
        for(int i = 0; i < board.length; i++)
        {
            for(int e = 0; e < board[0].length; e++)
            {
                if(sub(key).equalsIgnoreCase(board[i][e].getValue()))
                {
                    rows.add(i);
                    cols.add(e);
                }
            }
        }
        
        
        //Uses rows and columns provided to find if they can be the start of the key
        int i = 0;
        while(i < rows.size() && !ret)
        {
            if(findWord(key, (int)rows.get(i), (int)cols.get(i)))
                ret =  true;
            i++;
        }
        
        //Resets board and returns if word is on the board
        resetFlags();
        
        if(ret)
        {
            Word w = new Word(word);
            return w;
        }
        else
        {
            return new Word(word);
        }
    }
    
    /**
     * Recursive board traversal
     * 
     * @param key
     * @param row
     * @param col
     * @return done
     */
    private boolean findWord(String key, int row, int col)
    {
        boolean done = false;
        
        //Checks if key empty or if possible check spot is valid
        if(key.length() == 0 || valid(row, col, sub(key)))
        {
            //If key isn't empty string, sets flag as true
            if(key.length() > 0)
                board[row][col].setFlag(true);
            //Finished check
            if(key.length() == 0)
            {
                done = true;
            }
            //Calls next recursive check
            else
            {
                if(!done)
                {
                    //Calls next recursive
                    done = findWord(nextSub(key), row + 1, col); //down
                    //In rare chance the flag of this tile was changed to false, this resecures the flag until this is prooved an invalid path
                    board[row][col].setFlag(true);
                }
                    
                if(!done)
                {
                    done = findWord(nextSub(key), row + 1, col + 1); //down right
                    board[row][col].setFlag(true);
                }
                
                if(!done)
                {
                    done = findWord(nextSub(key), row, col + 1); //right
                    board[row][col].setFlag(true);
                }
                
                if(!done)
                {
                    done = findWord(nextSub(key), row - 1, col + 1); //up right
                    board[row][col].setFlag(true);
                }
                
                if(!done)
                {
                    done = findWord(nextSub(key), row - 1, col); // up
                    board[row][col].setFlag(true);
                }
                
                if(!done)
                {
                    done = findWord(nextSub(key), row - 1, col - 1); //up left
                    board[row][col].setFlag(true);
                }
                
                if(!done)
                {
                    done = findWord(nextSub(key), row, col - 1); //left
                    board[row][col].setFlag(true);
                }
                
                if(!done)
                {
                    done = findWord(nextSub(key), row + 1, col - 1); //down left
                    board[row][col].setFlag(true);
                }
                
            }
            //finished
            if(done)
            {
                if(key.length() >= 1)
                    word.add(0, new Tile(sub(key), row, col));
                return true;
            }
        }
        //resets the flagged value for possible retesting from another tile
        if((row < board.length && col < board[0].length) && (row >= 0 && col >= 0))
            board[row][col].setFlag(false);
        return false;
        
    }
    
    /**
     * returns the next tile value within the key word
     * 
     * @param key
     * @return tile value
     */
    private String nextSub(String key)
    {
        if(key.substring(0,1).equals("Q"))
            return key.substring(2);
        else
            return key.substring(1);
    }
    
    /**
     * returns the key word without the last possible tile value
     * 
     * @param key
     * @return key without first tile value
     */
    private String sub(String key)
    {
        if(key.substring(0,1).equals("Q"))
            return "Qu";
        else
            return key.substring(0,1);
    }
    
    /**
     * Determines if the next spot the recursive method tests is a valid choice
     * 
     * @param row
     * @param col
     * @param s
     * @return validity
     */
    private boolean valid(int row, int col, String s)
    {
        if(row < 0 || col < 0)
            return false;
        else if(row >= board.length || col >= board[0].length)
            return false;
        else if(board[row][col].getFlag())
            return false;
        else if(!board[row][col].getValue().equals(s))
            return false;
        else
            return true;
    }
}
