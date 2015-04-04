/* The world class, from gameworld
 * 
 *@author   Zack Raver
 *@date     12/22/14
 *
 * Has all the rules for the world
 */

package tetris;

import gameengine.GameWorld;
import gameengine.Sprite;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import static javafx.animation.Animation.Status.RUNNING;
import static javafx.animation.Animation.Status.STOPPED;

public class TetrisWorld extends GameWorld {
    
    /* TetrisWorld's fields:
     * @var: int gamespeed should be gamewide and public, so the various updates (specifically when we pop a row of deadblocks) can increase it
     * @var: double HEIGHT is the height of the game window, should be 20x the height of a pixel (set in Block)
     * @var: double WIDTH is the width of the game window, should be 10x the width of a pixel (set in Block)    */
    public static int gamespeed = 0;

    private static final double HEIGHT = 600;
    private static final double WIDTH = 300; 

    /* TetrisWorld's constructor:
     * @param: int fps, which sets the frame per second (how fast the frames repaint)
     * @param: String title, the window's title */
    public TetrisWorld(int fps, String title){
        super(fps,title);
    }

    /* TetrisWorld's initialize method:
     *      -initializes the game world by adding some sprite objects
     *      -sets the background of the window
     *      -starts a new block at the top
     *      -makes a label (for gamespeed aka level of difficulty)
     *      -makes a TODO:pause button (which is kinda broken actually)
     *      -sets the key handlers for the scene (which just calls methods defined in the sprites)
     *      -writes it all to the scene
     * @param: final Stage primaryStage */
    @Override
    public void initialize(final Stage primaryStage){
        //Set the window title
        primaryStage.setTitle(getTitle());

        //Create the scene
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT, Color.BLACK));
        primaryStage.setScene(getGameSurface());
        
        getGameSurface().getStylesheets().add(TetrisWorld.class.getResource("style/bg.css").toExternalForm());

        //Start a block
        generateBlock();
        
        //Making the label for gamespeed (aka game level)
        Label speed = new Label("Level: " + gamespeed);

        //making a button to do the pause
        Button pause = new Button("Pause");
        pause.setCancelButton(true);
        //LAMBDAS IN JAVA THO****************************************
        pause.setOnAction(e -> {
            switch (getGameLoop().getStatus()){
                case RUNNING:
                    getGameLoop().stop();
                    break;
                case STOPPED:
                    getGameLoop().play();
                    break;
            }
        });

        //making a menu to hold the label for speed and the pause button
        VBox menu = new VBox(5); 
        //menu.setPrefWidth(10); //dunno if this is necessary
        menu.getChildren().addAll(pause, speed);
        
        //Add the handler for the nodes
        //only does something is the game is "running"
        getSceneNodes().setOnKeyPressed(new EventHandler<KeyEvent>(){ //KeyPressed
            @Override
            public void handle(KeyEvent ke){
                if (getGameLoop().getStatus() == RUNNING){
                    for (Sprite spr : getSpriteManager().getAllSprites()){
                        spr.handleKeyPressed(ke.getCode());
                    }
                }
            }
        });
        getSceneNodes().setOnKeyReleased(new EventHandler<KeyEvent>(){ //KeyRelease
            @Override
            public void handle(KeyEvent ke){
                if (getGameLoop().getStatus() == RUNNING){ 
                    for (Sprite spr : getSpriteManager().getAllSprites()){
                        spr.handleKeyReleased(ke.getCode());
                    }
                }
            }
        });
        getSceneNodes().setOnKeyTyped(new EventHandler<KeyEvent>(){ //KeyTyped
            @Override
            public void handle(KeyEvent ke){
                if (getGameLoop().getStatus() == RUNNING){ 
                    for (Sprite spr : getSpriteManager().getAllSprites()){
                        spr.handleKeyTyped(ke.getCode());
                    }
                }
            }
        });

        //lay down the controls 
        getSceneNodes().getChildren().add(menu);
    }//initialize

    /* TetrisWorld's generateBlock method:
     *      responsible for making new live blocks
     * @return & @param: NONE   */
    protected void generateBlock(){
        Block block = new Block(this, BlockType.randomBlockType(), (double)WIDTH / 2, (double)0);
        getSpriteManager().addSpritesToBeAdded(block);
        for (Rectangle pixel : block.getPixels()){
            getSceneNodes().getChildren().add(pixel); 
        }
    }
    
    //TODO: might want to increment gamespeed elsewhere

    /* TetrisWorld's handleUpdate method:
     *      -in this case all "updates" ie moving and rotating etc are handled in the sprites eventHandlers
     *      -this is just gravity (scaled by gamespeed)
     *      -overrides GameWorld's handleUpdates (which also just passed the buck)
     * @param: Sprite spr   */
    @Override
    protected void handleUpdate(Sprite spr){
        if (spr instanceof Block){
            Block b = (Block)spr;
            b.update(getSpriteManager());
        }
    }
    
    /* TetrisWorld's handleCollision method:
     *      -Should only be colliding with the edge or a dead block..make another 4 sprites of dead blocks..that can't move (unless they pop a row)
     *      -overrides GameWorld's handleCollision method (which also just passed the buck)
     *      -removes the live block and adds a deadblock if block2block collision occurs
     * @param: Sprite spr1, the sprite to check against all others (should be a Block class)
     * @param: Sprite spr2, the sprite in question, should be dead
     * @return: boolean, if there is indeed a collision */
    @Override
    protected boolean handleCollision(Sprite spr1, Sprite spr2){
        if (spr1.collides(spr2)){
            return true;
        }
        return false;
    }

    /* TetrisWorld's cleanupSprites method:
     *      remove sprites from the scene and backend store
     * @return & @param: NONE      */
    @Override
    protected void cleanupSprites(){
        super.cleanupSprites();
        //TODO: maybe need to do more here
    }
    
    /* TetrisWorld's getWidth window width accessor method:
     * @return double width */
    public static double getWidth(){ return WIDTH; }
    
    /* TetrisWorld's getHeight window height accessor method:
     * @return double height */
    public static double getHeight(){ return HEIGHT; }

}//TetrisWorld
