/* General game world for use in a general game engine
 * Most credit goes to javacodegeeks tutorial on javafx
 */

package gameengine;

 import javafx.animation.Timeline;
 import javafx.animation.KeyFrame;
 import javafx.animation.Animation;
 import javafx.event.EventHandler;
 import javafx.event.ActionEvent;
 import javafx.event.Event;
 import javafx.scene.input.InputEvent;
 import javafx.scene.input.KeyEvent;
 import javafx.scene.Group;
 import javafx.scene.Scene;
 import javafx.stage.Stage;
 import javafx.util.Duration;


@SuppressWarnings("unchecked") 
public abstract class GameWorld {
    
    //javafx Scene is the games surface
    private Scene gameSurface;
    //all the nodes to be displayed are in the Group
    private Group sceneNodes;
    //the gameloop is javafx Timeline
    private static Timeline gameLoop;
    //number of frames per second
    private final int FPS;
    //title in the application window
    private final String TITLE;
    
    //Here might also go initialization of the managers
    SpriteManager sm = new SpriteManager();
    
    //Constructin' to be called by the derived class (ie super(..))
    public GameWorld(final int fps, final String title){
        this.FPS = fps;
        this.TITLE = title;
        //create and set timeline for game loop
        buildAndSetGameLoop();
    }

    //Build and set the game loop to be started
    protected final void buildAndSetGameLoop(){
        final Duration oneFrameAmt = Duration.millis(1000/getFPS());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
            new EventHandler(){

                @Override
                public void handle(Event e){
                    //update blocks
                    updateSprites();
                    //check for collision(s)
                    checkCollisions();
                    //clean it up
                    cleanupSprites();
                }//handle

            });//oneFrame

        //Set the game loop(timeline)
        setGameLoop(new Timeline(oneFrame));

    }//buildAndSetGameLoop

    //initialize the world 
    public abstract void initialize(final Stage primaryStage);

    //plays the game loop 
    public void beginGameLoop(){
        getGameLoop().play();
    }

    //Updates each sprite using handleUpdate(sprite) 
    //The derived class should override handleUpdate
    protected void updateSprites(){
        for (Sprite spr : sm.getAllSprites()){
            handleUpdate(spr);
        }
    }

    //Updates the sprites information to position on the game surface
    protected void handleUpdate(Sprite spr){}

    //checks each game sprite to determine if a collision occurred
    //the derived class should override handleCollision()
    protected void checkCollisions(){
        //check other sprites collisions
        sm.resetCollisionsToCheck();
        //check each sprite against each other sprite
        for (Sprite spr1 : sm.getCollisionsToCheck()){
            for (Sprite spr2 : sm.getAllSprites()){
                if (!(spr2 == spr1) && (handleCollision(spr1, spr2))){
                    //the following break statement helps optimize collisions
                    //it means only one sprite can collide with one other
                    //comment the break for more accurate collisions
                    break;
                }
            }
        }
    }

    //true if collides, false otherwise
    protected boolean handleCollision(Sprite spr1, Sprite spr2){
        return false;
    }

    //Sprites to be cleaned up
    protected void cleanupSprites(){
        sm.cleanupSprites();
    }

    //Returns fps
    protected int getFPS(){ return this.FPS; }
    
    //Return the windows title
    protected String getTitle(){ return this.TITLE; }

    //Return the game loop (or timeline)
    protected static Timeline getGameLoop(){ return gameLoop; }

    //Set the gameloop
    protected static void setGameLoop(Timeline gl){ 
        gameLoop = gl; 
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    //return the sprite manager
    public SpriteManager getSpriteManager(){ return sm; }

    //return the Scene or game surface
    public Scene getGameSurface(){ return this.gameSurface; }

    //sets the scene 
    protected void setGameSurface(Scene gs){ this.gameSurface = gs; }

    //returns scene nodes which are rendered onto the game surface 
    public Group getSceneNodes(){ return this.sceneNodes; }

    //sets the group to hold the scene nodes 
    protected void setSceneNodes(Group sn){ this.sceneNodes = sn;}

}

