/* The basic sprites of tetris, can have any configuration of 4 squares, dictated by the BlockType enum (also sets their color)
 * 
 * @author  Zack Raver
 * @date    12/23/14
 */

package tetris;

import gameengine.GameWorld;
import gameengine.SpriteManager;
import gameengine.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;

//TODO: fix window collision, broken by rotations etc. in blockType mostly
//TODO: fix block collision

public class Block extends Sprite {
   
    /*  @var double W is the height and width of each "pixel" 
     *            should be 1/10 of the stage width and 1/20 of the stage height TODO maybe should add the max height of a block?
     *  @var ArrayList<Rectangle> PIXELS is the array of Rectangle nodes (called pixels here) that can be iterated through do move etc 
     *  @var BlockType W is used for other sprites to get the blocktype things     
     *  @var TetrisWorld localWorld is the world that this block lives in
     *  @var boolean dead is global so that the block doesnt keep updating 
     *  @var int position, depends on the blocktype but can only be a few*/
    private final double W = 30;   
    private final ArrayList<Rectangle> PIXELS = new ArrayList<Rectangle>(4);           
    private final BlockType bt;
    private final TetrisWorld localWorld;
    private boolean isDead = false;
    private int position = 0;

    /* Constructs a new block (composition of 4 shapes, so most of this constructs the shapes)
     * @param BlockType bt, a member of the enum BlockType, to dictate the shape of the block
     * @param double xPos and yPos should always be at the top ~50% of the stages X value */
    public Block(TetrisWorld tw, BlockType bt, double xPos, double yPos){                                       
        this.localWorld = tw;
        this.bt = bt;

        for (int j = 0; j < 4; j++){
            Rectangle r = new Rectangle(W,W);
            PIXELS.add(r);         
        }

        switch (bt){ 
            case I: for (int i = 1; i <= PIXELS.size(); i++){
                        PIXELS.get(i-1).setX(xPos + (i * W));
                        PIXELS.get(i-1).setY(yPos);
                    }
                    break;
            
            case J: PIXELS.get(0).setX(xPos); PIXELS.get(0).setY(yPos);                 //position x and y for pixel[0]
                    PIXELS.get(1).setX(xPos); PIXELS.get(1).setY(yPos + W);            //pixel[1]
                    PIXELS.get(2).setX(xPos + W); PIXELS.get(2).setY(yPos + W);         //pixel[2]
                    PIXELS.get(3).setX(xPos + (W*2)); PIXELS.get(3).setY(yPos + W);     //pixel[3]
                    break;

            case L: PIXELS.get(0).setX(xPos + (W*2)); PIXELS.get(0).setY(yPos);         //position x and y for pixel[0]
                    PIXELS.get(1).setX(xPos); PIXELS.get(1).setY(yPos + W);             //pixel[1]
                    PIXELS.get(2).setX(xPos + W); PIXELS.get(2).setY(yPos + W);         //pixel[2]
                    PIXELS.get(3).setX(xPos + (W*2)); PIXELS.get(3).setY(yPos + W);     //pixel[3]
                    break;

            case O: PIXELS.get(0).setX(xPos); PIXELS.get(0).setY(yPos);                 //position x and y for pixel[0]
                    PIXELS.get(1).setX(xPos + W); PIXELS.get(1).setY(yPos);             //pixel[1]
                    PIXELS.get(2).setX(xPos); PIXELS.get(2).setY(yPos + W);             //pixel[2]
                    PIXELS.get(3).setX(xPos + W); PIXELS.get(3).setY(yPos + W);         //pixel[3]
                    break;

            case S: PIXELS.get(0).setX(xPos + W); PIXELS.get(0).setY(yPos);             //position x and y for pixel[0]
                    PIXELS.get(1).setX(xPos + (W*2)); PIXELS.get(1).setY(yPos);         //pixel[1]
                    PIXELS.get(2).setX(xPos); PIXELS.get(2).setY(yPos + W);             //pixel[2]
                    PIXELS.get(3).setX(xPos + W); PIXELS.get(3).setY(yPos + W);         //pixel[3]
                    break;

            case T: PIXELS.get(0).setX(xPos + W); PIXELS.get(0).setY(yPos);             //position x and y for pixel[0]
                    PIXELS.get(1).setX(xPos); PIXELS.get(1).setY(yPos + W);             //pixel[1]
                    PIXELS.get(2).setX(xPos + W); PIXELS.get(2).setY(yPos + W);         //pixel[2]
                    PIXELS.get(3).setX(xPos + (W*2)); PIXELS.get(3).setY(yPos + W);     //pixel[3]
                    break;

            case Z: PIXELS.get(0).setX(xPos); PIXELS.get(0).setY(yPos);                 //position x and y for pixel[0]
                    PIXELS.get(1).setX(xPos + W); PIXELS.get(1).setY(yPos);             //pixel[1]
                    PIXELS.get(2).setX(xPos + W); PIXELS.get(2).setY(yPos + W);         //pixel[2]
                    PIXELS.get(3).setX(xPos + (W*2)); PIXELS.get(3).setY(yPos + W);     //pixel[3]
                    break;
        }//switch
       
       int o = 0;
        //settin the fill based on the blocktype
        for (Rectangle pixel : PIXELS){ 
            pixel.setFill(bt.getColor()); 
            pixel.setStroke(Color.BLACK);
            pixel.setStrokeWidth((double)(o));
            pixel.setSmooth(true);
            ++o;
        }

    }//BlockConstructor
    
    /* Accessor for the Block's Pixel array
     * @return arraylist of pixel nodes */
    public ArrayList<Rectangle> getPixels(){ return this.PIXELS; }
   

    //TODO(zack): Turn live block into individual dead blocks..WITHOUT UPSETTING THE BALANCE OF FORCE
    /* -This is called by the main game driver (TetrisWorld extending GameWorld) to update (ie. move) each frame 
     * -if we've hit the bottom..turn them into dead blocks
     * @param SpriteManager sm, to do stuff  */
    @Override
    public void update(SpriteManager sprM){
        ArrayList<DeadBlock> toAdd = new ArrayList<DeadBlock>();
        for (int i = 3; i >= 0; i--){
            if(!isDead){
                if (!(((PIXELS.get(i).getY() >= 0) && (PIXELS.get(i).getY()) <= (localWorld.getHeight() - W)))){
                    sprM.addSpritesToBeAdded(new DeadBlock(getW(), getBlockType().getColor(), getPixels().get(i).getX(), getPixels().get(i).getY()));       
                    isDead = true;
                }else{
                    TetrisWorld.gamespeed += 1;
                    PIXELS.get(i).setY(PIXELS.get(i).getY() + (TetrisWorld.gamespeed));
                }
            }else{
                sprM.addSpritesToBeAdded(new DeadBlock(getW(), getBlockType().getColor(), getPixels().get(i).getX(), getPixels().get(i).getY()));       
            } 
        }    
        if (isDead){
            sprM.addSpritesToBeRemoved(this);
            localWorld.generateBlock();
        }
    }
    
    /* This should be the only event handler needed for the sprites, called by the scene's event handling methods
     * @param KeyCode c */
    public void handleKeyPressed(KeyCode c){
        double bottomEdge = (PIXELS.get(bt.getBottommost()).getY() + W) + W; //Bottommost plus height of pixel
        double leftEdge = PIXELS.get(bt.getLeftmost()).getX(); 
        double rightEdge = PIXELS.get(bt.getRightmost()).getX() + W; //Rightmost pixel's x position plus the width of the pixel
        System.out.println("Leftmost pixel: "+bt.getLeftmost()+
                           "\nRightmost pixel: "+bt.getRightmost()+
                           "\nBottommost pixel: "+bt.getBottommost());
        switch(c){
            case UP:{                   
                rotate();
            } break;
            
            case DOWN:{ //accelerate down
                if(!(bottomEdge >= localWorld.getHeight())){
                    move(0,W);
                }
            } break;
                    
            case RIGHT:{//move right
                if(!(rightEdge >= localWorld.getWidth())){
                    move(W,0);
                }
            } break;
                    
            case LEFT:{ //move left
                if(!(leftEdge <= 0)){
                    move(-W,0);
                }
            } break;
                    
            default:{   //do nothing
            } break;
        }
    }//handleKeyPress

    /* Probably doesn't have to do anything for tetris
     * @param KeyCode c */
    public void handleKeyReleased(KeyCode c){
    }
    
    /* Probably doesn't have to do anything for tetris
     * @param KeyCode c */
    public void handleKeyTyped(KeyCode c){
    }

    /* Moves the block based on the newX and newY
     * @param dx
     * @param dy    */
    public void move(double dx, double dy){    
        for(int i = 0; i <= 3; i++){
            double currentX = PIXELS.get(i).getX();
            PIXELS.get(i).setX(currentX + dx);
            double currentY = PIXELS.get(i).getY();
            PIXELS.get(i).setY(currentY + dy);
        }
    }

    /* -Called by the game driver's checkCollisions method, checks if the liveBlock collides with a DeadBlock (and should then die)
     * -Checks if any of this blocks pixels are in any other blocks pixels
     * -other (the param) has to be a deadblock...no way you can run into another live block
     * @return boolean (true or false there is a collision)
     * @param other block */
    @Override
    public boolean collides(Sprite other){
        DeadBlock db = (DeadBlock)other;
        for (Rectangle pixel : PIXELS){                                                                             //TODO:$$$$$ POTENTIAL FOR ONE OFFS HERE ################
            if (((pixel.getX() >= (db.getPixel().getX() - W)) && (pixel.getX() < (db.getPixel().getX() + W)))) {     //If its within W (blockwidth) of the other block
                if (pixel.getY() >= db.getPixel().getY() + W){                                                          //and is >= the same depth as the other block
                    isDead = true;
                    return true;                                                                                    //Collision indeed
                }
            }
        }
        return false; //otherwise no collision
    }

    /* This is important so that DeadBlocks can get the block type stuff, like color
     * @return this block's BlockType bt    */
    public BlockType getBlockType(){ return this.bt; }

    /* Important because this is how you grab every pixels dimenions (x and y)         
     * @return W (the pixel width and height)   */
    public double getW(){ return this.W; }

    /* -Handles the possible positions for each block types
     * -Adjusts the global position var
     * @return nextposition    */
    public int getNextPosition(){
        switch(bt){
            case I:
                if (position == 1){ 
                    position = 0;
                    break;
                } else {
                    position = 1;
                    break;
                }
            case J:
                if (position == 3){ 
                    position = 0;
                    break;
                } else {
                    return ++position;
                }
            case L:
                if(position == 3){ 
                    position = 0;
                    break;
                } else {
                    return ++position;
                }
            case O:
                break;  //O doesn't have more than one position
            case S:
                if (position == 1){ 
                    position = 0;
                    break;
                } else {
                    position = 1;
                    break;
                } 
            case T:
                if (position == 3){ 
                    position = 0;
                    break;
                } else {
                    return ++position;
                }
            case Z:
                if (position == 1){ 
                    position = 0;
                    break;
                } else {
                    position = 1;
                    break;
                }
            default: 
                return ++position;
        }
        return position;
    }

    /* Block's rotate method:
     *      -TODO(zack): Is there a smarter way to do this, rather than just cases?
     *      -checks and then moves to the next position
     * @return& @param: NONE */
    public void rotate(){
        int nextPosition = getNextPosition();
        bt.checkRotate(nextPosition);
        double bx = PIXELS.get(0).getX();   //get blocks x and y
        double by = PIXELS.get(0).getY();
        
        switch(bt){
            case I:{
                if (nextPosition == 1){
                    PIXELS.get(1).setX(bx);             //set new xs   
                    PIXELS.get(2).setX(bx);
                    PIXELS.get(3).setX(bx);
                    PIXELS.get(1).setY(by + W);         //set new ys
                    PIXELS.get(2).setY(by + W*2);   
                    PIXELS.get(3).setY(by + W*3);
                } else if (nextPosition == 0){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx + W*2);   
                    PIXELS.get(3).setX(bx + W*3);
                    PIXELS.get(1).setY(by);             //set new ys
                    PIXELS.get(2).setY(by);
                    PIXELS.get(3).setY(by);
                }
            } break;
            case J:{
                if (nextPosition == 1){
                    PIXELS.get(1).setX(bx - W);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx - W);
                    PIXELS.get(1).setY(by);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W*2);
                } else if (nextPosition == 2){
                    PIXELS.get(1).setX(bx);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx - W*2);
                    PIXELS.get(1).setY(by - W);             //set new ys
                    PIXELS.get(2).setY(by - W);
                    PIXELS.get(3).setY(by - W);
                } else if (nextPosition == 3){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx + W);   
                    PIXELS.get(3).setX(bx + W);
                    PIXELS.get(1).setY(by);             //set new ys
                    PIXELS.get(2).setY(by - W);
                    PIXELS.get(3).setY(by - W*2);
                } else if (nextPosition == 0){
                    PIXELS.get(1).setX(bx);         //set new xs
                    PIXELS.get(2).setX(bx + W);   
                    PIXELS.get(3).setX(bx + W*2);
                    PIXELS.get(1).setY(by + W);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W);
                }
            } break;    
            case L:{
                if ( nextPosition == 0){
                    PIXELS.get(1).setX(bx - W*2);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx);
                    PIXELS.get(1).setY(by + W);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W);
                } else if (nextPosition == 1){
                    PIXELS.get(1).setX(bx - W);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx - W);
                    PIXELS.get(1).setY(by - W*2);             //set new ys
                    PIXELS.get(2).setY(by - W);
                    PIXELS.get(3).setY(by);
                } else if (nextPosition == 2){
                    PIXELS.get(1).setX(bx + W*2);         //set new xs
                    PIXELS.get(2).setX(bx + W);   
                    PIXELS.get(3).setX(bx);
                    PIXELS.get(1).setY(by - W);             //set new ys
                    PIXELS.get(2).setY(by - W);
                    PIXELS.get(3).setY(by - W);
                } else if (nextPosition == 3){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx + W);   
                    PIXELS.get(3).setX(bx + W);
                    PIXELS.get(1).setY(by + W*2);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by);
                }
            } break;
            case O:{    //does no rotating
            } break;
            case S:{
                if (nextPosition == 0){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx);
                    PIXELS.get(1).setY(by);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W);
                } else if (nextPosition == 1){
                    PIXELS.get(1).setX(bx);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx - W);
                    PIXELS.get(1).setY(by + W);             //set new ys
                    PIXELS.get(2).setY(by - W);
                    PIXELS.get(3).setY(by);
                }
            } break;
            case T:{
                if (nextPosition == 0){
                    PIXELS.get(1).setX(bx - W);         //set new xs
                    PIXELS.get(2).setX(bx);   
                    PIXELS.get(3).setX(bx + W);
                    PIXELS.get(1).setY(by + W);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W);
                } else if (nextPosition == 1){
                    PIXELS.get(1).setX(bx - W);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx - W);
                    PIXELS.get(1).setY(by - W);             //set new ys
                    PIXELS.get(2).setY(by);
                    PIXELS.get(3).setY(by + W);
                } else if (nextPosition == 2){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx);   
                    PIXELS.get(3).setX(bx - W);
                    PIXELS.get(1).setY(by - W);             //set new ys
                    PIXELS.get(2).setY(by - W);
                    PIXELS.get(3).setY(by - W);
                } else if (nextPosition == 3){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx + W);   
                    PIXELS.get(3).setX(bx + W);
                    PIXELS.get(1).setY(by + W);             //set new ys
                    PIXELS.get(2).setY(by);
                    PIXELS.get(3).setY(by - W);
                }
            } break;
            case Z:{
                if (nextPosition == 0){
                    PIXELS.get(1).setX(bx + W);         //set new xs
                    PIXELS.get(2).setX(bx + W);   
                    PIXELS.get(3).setX(bx + W*2);
                    PIXELS.get(1).setY(by);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W);
                } else if (nextPosition == 1){
                    PIXELS.get(1).setX(bx);         //set new xs
                    PIXELS.get(2).setX(bx - W);   
                    PIXELS.get(3).setX(bx - W);
                    PIXELS.get(1).setY(by + W);             //set new ys
                    PIXELS.get(2).setY(by + W);
                    PIXELS.get(3).setY(by + W*2);
                }
            } break;
            default:{}break;
        }
    }//rotate
}//Block
