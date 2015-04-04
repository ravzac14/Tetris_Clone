/* This is a class for blocks that have hit the bottom..
 * they need to dissappear when a whole row of them have combined
 *
 * @author  Zack Raver
 * @date    12/24/14
 * 
 * Need to override update() to just check for a whole row of deads and kill them
 * I think...
 */

package tetris;

import gameengine.Sprite;
import gameengine.SpriteManager;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class DeadBlock extends Sprite{

    /* DeadBlock's fields:
     * @var: Rectangle pixel (because deadblocks can only be ONE pixel so they can disappear)   */
    public Rectangle pixel;

    /* DeadBlock's constructor:
     *      just takes one of the liveBlocks 4 pixels and translates it into a deadblock
     * @param: Rectangle r  */
    public DeadBlock(double W, Color color, double xPos, double yPos) {
       pixel = new Rectangle(W,W);
       pixel.setX(xPos);
       pixel.setY(yPos);
       pixel.setFill(color);
    }

    /* DeadBlock's update method:
     *      Needs to grab the list of other deadblocks from the SM (which is just GAME_ACTORS minus the one live blocks) 
     *      and check if there are 10 in a horizontal row
     * @return & @param: NONE      */
    @Override
    public void update(SpriteManager sm){
    }

    /* DeadBlock's handleKeyPressed, handleKeyReleased, and handleKeyTyped methods:
     * These shouldnt do anything   */
    public void handleKeyPressed(KeyCode c){
    }
    
    public void handleKeyReleased(KeyCode c){
    }

    public void handleKeyTyped(KeyCode c){
    }

    /* DeadBlock's getPixel method:
     *      whenever you gotta grab the node outta the sprite
     * @return: Rectangle   */
    public Rectangle getPixel(){ return pixel; }
}
