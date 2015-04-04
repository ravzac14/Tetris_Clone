/* This is the basic framework of a sprite object
 * to be used in the game engine
 *
 * @author  Zack Raver
 * @date    12/21/14
 * 
 * These are supposed to be displayed
 */

package gameengine;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.input.*;

public abstract class Sprite {

    //Animations for the node
    public List animations = new ArrayList<>();

    //Current display node
    public Node node;

    //velocity vector x direction
    public double vX = 0;

    //velocity vector y direction
    public double vY = 0;

    //is dead?
    public boolean isDead = false;

    //updates the sprite objects velocity or animations
    public abstract void update(SpriteManager sm);

    //did this sprite collide with another sprite
    public boolean collides(Sprite other){ return false; }

    public Node getNode(){ return node; }

    public abstract void handleKeyPressed(KeyCode c);

    public abstract void handleKeyReleased(KeyCode c);
    
    public abstract void handleKeyTyped(KeyCode c);


}
