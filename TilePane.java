
package boggle;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

/**
 *
 * @author zjk73
 */
public class TilePane extends HBox{

    //Variables
    private Tile tile;
    private Text text;
    
    /**
     * Constructor for TilePane
     * 
     * @param t tile
     */
    public TilePane(Tile t)
    {
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-width: 5; -fx-border-color: black;");
        this.setPrefSize(100, 100);
        tile = t;
        text = new Text(tile.getValue());
        
        text.setFont(Font.font("Helvetica", 24));
        this.getChildren().add(text);
        
        if(tile.getFlag())
        {
            this.setStyle("-fx-background-color: lightblue;-fx-border-width: 5; -fx-border-color: black;");
        }
    }
    
    /**
     * Sets tile as Selected
     */
    public void setSelected()
    {
        tile.setFlag(true);
        this.setStyle("-fx-background-color: lightblue;-fx-border-width: 5; -fx-border-color: black;");
    }
    
    /**
     * Sets tile as unselected
     */
    public void setUnselected()
    {
        tile.setFlag(false);
        this.setStyle("-fx-background-color: white;-fx-border-width: 5; -fx-border-color: black;");
    }
    /**
     * Returns the row value
     * @return row
     */
    public int getTileRow()
    {
        return tile.getRow();
    }
    /**
     * Returns column value
     * @return column
     */
    public int getTileCol()
    {
        return tile.getCol();
    }
    
}
