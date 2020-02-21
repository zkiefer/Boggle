
package boggle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


/**
 * Zachary Kiefer CS 110
 * @author zjk73
 * 
 * Main GUI class for boggle
 */
public class Boggle extends Application
{
    
    // create game
    private Game g = new Game();
    private BorderPane pane;
    private GridPane grid;
    private Scene scene;
    private VBox title;
    private Button checkWord;
    private VBox right;
    private Button submit;
    private TextField textfield;
    private VBox left;
    private Button newGame;
    private Button exit;
    private Button undo;
    private VBox bottom;
    private Text wlist;
    private Text points;
    private Text timer;
    private int time;
    private final int MAX_TIME = 91;
    private boolean gameover;
    private KeyFrame frame;
    private Timeline timeline;
    private Text status;
    
    
    @Override
    public void start(Stage primaryStage)
    {
        //Sets up variables
        title = new VBox();
        right = new VBox();
        left = new VBox();
        bottom = new VBox();
        newGame = new Button("New Game");
        submit = new Button("Submit word");
        textfield = new TextField();
        grid = new GridPane();
        pane = new BorderPane();
        checkWord = new Button("Check Word");
        exit = new Button("Exit");
        undo = new Button("Undo");
        wlist = new Text("");
        points = new Text("");
        timer = new Text("");
        time = MAX_TIME;
        gameover = false;
        status = new Text("");
        
        
        //Title/Top bar
        Text t = new Text("BOGGLE");
        t.setStyle("-fx-font-family: Helvetica;"
                + "-fx-font-size: 48;"
                + "-fx-font-weight: bold;");
        status.setStyle("-fx-font-family: Helvetica;"
                + "-fx-font-size: 20;"
                + "-fx-fill: red;");
        title.setAlignment(Pos.CENTER);
        title.getChildren().add(t);
        title.getChildren().add(status);
        title.setStyle("-fx-background-color: lightblue;");
        pane.setTop(title);
        
        
        //Sets center grid
        setGrid();
        
        pane.setCenter(grid);
        
        //Setting Right box
        checkWord.setOnAction(this::handleWord);
        right.getChildren().add(checkWord);
        undo.setOnAction(this::handleUndo);
        right.getChildren().add(undo);
        right.setAlignment(Pos.BOTTOM_CENTER);
        
        right.getChildren().add(textfield);
        submit.setOnAction(this::handleSubmit);
        right.getChildren().add(submit);
        right.setPrefWidth(175);
        right.setSpacing(5);
        
        right.setStyle("-fx-background-color: lightblue;");
        pane.setRight(right);
        
        //Setting left box
        class Clock implements EventHandler<ActionEvent>
        {
            @Override
            public void handle(ActionEvent e)
            {
                time -= 1;
                if(time == 0)
                {
                    gameover = true;
                    status.setText("Times up!");
                }
                
                timer.setText(time + "");
                
                if(time >= MAX_TIME/3)
                {
                    timer.setStyle("-fx-font-family: Helvetica;"
                            + "-fx-font-size: 24;"
                            + "-fx-fill: green;");
                }
                else if(time >= MAX_TIME/9)
                {
                    timer.setStyle("-fx-font-family: Helvetica;"
                            + "-fx-font-size: 24;"
                            + "-fx-fill: yellow;");
                }
                else
                {
                    timer.setStyle("-fx-font-family: Helvetica;"
                            + "-fx-font-size: 24;"
                            + "-fx-fill: red;");
                }
            }
        }
        
        frame = new KeyFrame(Duration.seconds(1), new Clock());
        timeline = new Timeline(frame);
        timeline.setCycleCount(MAX_TIME);
        timeline.play();
        
        left.getChildren().add(timer);
        newGame.setOnAction(this::handleNewGame);
        left.getChildren().add(newGame);
        
        exit.setOnAction(this::handleExit);
        left.getChildren().add(exit);
        left.setAlignment(Pos.CENTER);
        left.setPrefWidth(175);
        left.setSpacing(5);
        
        left.setStyle("-fx-background-color: lightblue;");
        pane.setLeft(left);
        
        
        //Setting bottom box
        setWords();
        wlist.setStyle("-fx-font-family: Helvetica;"
                + "-fx-font-size: 18;");
        bottom.getChildren().add(wlist);
        setPoints();
        points.setStyle("-fx-font-family: Helvetica;"
                + "-fx-font-size: 18;");
        bottom.getChildren().add(points);
        bottom.setStyle("-fx-background-color: lightblue;");
        pane.setBottom(bottom);
        
        scene = new Scene(pane, 650, 450);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Sets up the grid of tiles
     */
    private void setGrid()
    {
        grid.getChildren().clear();
        for(int i = 0; i < 4; i++)
            for(int e = 0; e < 4; e++)
            {
                TilePane t = new TilePane(g.getTile(i, e));
                grid.add(t, e, i);
                t.setOnMouseClicked(this::handleClick);
            }
        grid.setPrefSize(100, 100);
    }
    
    
    /**
     * Sets up the words from the game class
     */
    public void setWords()
    {
        String s = "Words:";
        for(Word w : g.getWords())
        {
            s += " " + w;
        }
        
        wlist.setText(s);
    }
    
    /**
     * Finds the point value of the words in game
     */
    public void setPoints()
    {
        String s = "Score: ";
        int n = 0;
        for(Word w : g.getWords())
        {
            n += w.getPoints();
        }
        
        s += n;
        
        points.setText(s);
    }
    
    
    /**
     * Handles when a tile is clicked
     * @param me Mouse Event
     */
    public void handleClick(MouseEvent me)
    {
        TilePane tp = (TilePane)(me.getSource());
        if(!gameover)
        {
            if(g.isValidSelection(tp.getTileRow(), tp.getTileCol()) && !g.getTile(tp.getTileRow(), tp.getTileCol()).getFlag())
            {
                tp.setSelected();
                g.addToSelected(tp.getTileRow(), tp.getTileCol());
            }
        
        
            setGrid();
        }
        
    }
    
    /**
     * Handles when a player wants the tiles to be checked
     * @param e Action Event
     */
    public void handleWord(ActionEvent e)
    {
        g.testSelected();
        g.clearSelected();
        setWords();
        setPoints();
        clearFlags();
    }
    
    /**
     * Handles when a player wants to undo the last selected tile
     * @param e Action Event
     */
    public void handleUndo(ActionEvent e)
    {
        if(!g.getSelectedTiles().isEmpty())
        {
            Tile t = (g.getSelectedTiles().get(g.getSelectedTiles().size() - 1));
            g.removeFromSelected(t.getRow(), t.getCol());
            t.setFlag(false);
        }
        setGrid();
    }
    
    /**
     * Makes sure all the tile flags are set to false
     */
    public void clearFlags()
    {
        for(int i = 0; i < 4; i++)
            for(int e = 0; e < 4; e++)
                g.getTile(i, e).setFlag(false);
        setGrid();
    }
    
    /**
     * Handles when the player wants to exit the game
     * @param e ActionEvent 
     */
    public void handleExit(ActionEvent e)
    {
        Platform.exit();
    }
    
    /**
     * Handles when the player wants to play a new game
     * @param e Action Event
     */
    public void handleNewGame(ActionEvent e)
    {
        g = new Game();
        gameover = false;
        status.setText("");
        time = MAX_TIME;
        timeline.playFromStart();
        setGrid();
    }
    
    /**
     * Handles when a player types in a word and wants to check if it is valid and on the board
     * @param e Action Event
     */
    public void handleSubmit(ActionEvent e)
    {
        
        String s = textfield.getCharacters().toString();
        
        textfield.clear();
        if(!gameover)
        {
            if(s.length() != 0)
                g.wordCheck(s);
            setWords();
            setPoints();
        }
    }
    
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    
    
    /*
    public static void main(String[] args)
    {
        // create game
        Game g = new Game();
        
        
        // create scanner for user input
        Scanner keyboard = new Scanner(System.in);

        // variables for user input
        int row, col;
        String input;

        // create flag to end game
        boolean stop = false;
        
         // play game while time has not exceeded 10 minutes and the user hasn't stopped the game
        while (!stop) {
            // print board
            System.out.println(g);

            // prompt user for choices
            System.out.print("(s)elect, (d)eselect, (l)ist selected, (c)lear selected, (t)est selected, (e)nd: ");

            // get choice
            input = keyboard.nextLine();

            // select
            if (input.equalsIgnoreCase("s")) {
                // prompt for row & column
                System.out.print("row / column [r c]: ");

                // get row, col from input
                row = keyboard.nextInt();
                col = keyboard.nextInt();
                input = keyboard.nextLine(); // clear new line left in buffer

                // test if the r/c combination is a valid move
                if (g.isValidSelection(row, col)){
                    // add tile to selected tiles
                    if (g.getSelectedTiles().contains(g.getTile(row,col)))
                        System.out.println("Tile is already selected");
                    else
                     g.addToSelected(row, col);
                }
                else {
                    System.out.println("Invalid selection! Please select a letter adjacent to the previously selected letter.");
                }
            }

            // deselect
            else if (input.equalsIgnoreCase("d")) {
                // prompt for row & column
                System.out.print("row / column [r c]: ");

                // get row, col from input
                row = keyboard.nextInt();
                col = keyboard.nextInt();
                input = keyboard.nextLine(); // clear new line left in buffer

                // remove tile from selected tiles
                g.removeFromSelected(row,col);
            }

            // list currently selected tiles
            else if (input.equalsIgnoreCase("l")) {
                ArrayList<Tile> selected = g.getSelectedTiles();
                System.out.println(selected);
            }

            // clear currently selected tiles
            else if (input.equalsIgnoreCase("c")) {
                g.clearSelected();
            }

            else if (input.equalsIgnoreCase("t")) {
                g.testSelected();
            }

            // end game
           else if (input.equalsIgnoreCase("e")) {
                stop = true;
           } 
           
            
         }
        
        
        
    }
    
    */
}
