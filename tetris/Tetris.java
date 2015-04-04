/* Tetris main driving class
 *  - Has stuff
 *
 * @author  Zack Raver
 * @date    12/22/14
 * 
 * This is my first crack at a game..sorry about the mess (im assuming)
 * The main driver of the game
 */
 
package tetris;

import gameengine.GameWorld;
import javafx.application.Application;
import javafx.stage.Stage;
 
 public class Tetris extends Application {
    
    /* Tetris' fields:
     * @var: GameWorld gw is the framework from the game engine with specifics for tetris   
     *      ie, FPS and the window title    */
    GameWorld gw = new TetrisWorld (60, "My-Tetris");

    /* Tetris' main:
     * This is just formal i think  */
    public static void main(String[] args){
        launch(args);
    }

    /* Tetris' start method:
     *      Overrides the GameWorld's start method, which sets the stage and kicks of the game loop
     * @param: Stage primaryStage   */
    @Override
    public void start(Stage primaryStage){
        //setup title, scene, stats, controls, and actors
        gw.initialize(primaryStage);

        //kick off the game loop
        gw.beginGameLoop();

        //display window
        primaryStage.show();
    }
}

