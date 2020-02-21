
package boggle;
import java.util.ArrayList;
import java.io.*;
/**
 *
 * @author zjk73
 * 
 * The Game class is the guts of the boggle game. It houses the methods that let the game operate
 */
public class Game
{
    //Variables
    private int score;
    private Board board;
    private ArrayList<Word> words;
    private ArrayList<Tile> tiles;
    private Dictionary dic;
    
    /**
     * Constructor for game class
     */
    public Game()
    {
        dic = new Dictionary("dic.txt");
        tiles = new ArrayList<>();
        words = new ArrayList<>();
        board = new Board();
        score = 0;
    }
    
    /**
     * Method determines if the given tile at row col is avaliable to be selected in the game
     * @param row
     * @param col
     * @return valid
     */
    public boolean isValidSelection(int row, int col)
    {
        if(tiles.isEmpty())
        {
            return(row < 4 && row >= 0)&&(col < 4 && col >= 0);
        }
        else
        {
            if((row < 4 && row >= 0)&&(col < 4 && col >= 0))
            {
                Tile last = tiles.get(tiles.size()-1);
                
                if(last.getCol() == col || last.getCol() == col + 1 || last.getCol() == col - 1)
                    if(last.getRow() == row || last.getRow() == row + 1 || last.getRow() == row - 1)
                        return true;
                
                return false;
                
            }
            
            return false;
            
        }
    }
    /**
     * Determines if the given key word is on the board
     * @param key 
     */
    public void wordCheck(String key)
    {
        Word w = board.findWord(key);
        
        if(dic.isValidWord(w.getList()))
            if(!isIn(w))
                words.add(w);
    }
    /**
     * Returns array list of the tiles that are selected
     * @return Selected Tiles
     */
    public ArrayList<Tile> getSelectedTiles()
    {
        return tiles;
    }
    /**
     * Returns tile at row, col from games board
     * @param row
     * @param col
     * @return Tile
     */
    public Tile getTile(int row, int col)
    {
        return board.getTile(row, col);
    }
    
    /**
     * Returns the valid words guessed
     * @return words
     */
    public ArrayList<Word> getWords()
    {
        return words;
    }
    /**
     * Adds a tile at row, col to the selected tiles
     * @param row
     * @param col 
     */
    public void addToSelected(int row, int col)
    {
        tiles.add(board.getTile(row, col));
    }
    /**
     * Removes tile at row, col from the selected tiles
     * @param row
     * @param col 
     */
    public void removeFromSelected(int row, int col)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).equals(board.getTile(row, col)))
                tiles.remove(i);
        }
    }
    /**
     * Clears all selected tiles
     */
    public void clearSelected()
    {
        tiles.clear();
    }
    /**
     * Tests if the selected tiles form a valid word. If the word is valid, it is added to the list of words
     */
    public void testSelected()
    {
        if(dic.isValidWord(tiles))
            if(!isIn(new Word(tiles)))
                words.add(new Word(tiles));
        
        tiles.clear();
    }
    
    @Override
    public String toString()
    {
        String s = "";
        
        s += board.toString();
        
        s += "\n";
        
        s += "Selected: ";
        
        for(Tile t : tiles)
        {
            s += t.getValue() + " ";
        }
        
        s += "\n\n";
        
        s += "Words: ";
        
        for(Word w : words)
        {
            s += w.toString() + " ";
        }
        
        s += "\n";
        
        return s;
    }
    /**
     * Private method that checks if a word is already in the word list
     * @param w
     * @return 
     */
    private boolean isIn(Word w)
    {
        for(Word check : words)
        {
            if(check.equals(w))
            {
                System.out.println(check + " " + w);
                System.out.println("Equals");
                return true;
            }
        }
        return false;
    }
}
