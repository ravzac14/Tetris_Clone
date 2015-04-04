/* Holds configurations for various block types in tetris
 *
 * @author  Zack Raver
 * @date    12/23/14
 */

package tetris;

import javafx.scene.paint.*;
import java.util.*;

public enum BlockType {
    
    //  0:  0123    1:  0                   NEED: 0, 1
    //                  1
    //                  2
    //                  3
    I(Color.AQUA),
    
    //  0:  0   1:  10  2:  321 3:   3      NEED: 0, 1, 2, 3
    //      123     2         0      2
    //              3               01
    J(Color.BLUE),
    
    //  0:    0 1:  1   2:  321 3:  03      NEED: 0, 1, 2, 3
    //      123     2       0        2
    //              30               1
    L(Color.ORANGE),
    
    //  0:  01                              NEED: 0
    //      23
    O(Color.YELLOW),
    
    //  0:   01 1:  2                       NEED: 0, 1
    //      23      30 
    //               1  
    S(Color.GREEN),
    
    //  0:   0  1:  1   2:  321 3:   3      NEED: 0, 1, 2, 3
    //      123     20       0      02
    //              3                1
    T(Color.PURPLE),
    
    // 0:   01  r1:  0                      NEED: 0, 1
    //       23     21
    //              3  
    Z(Color.RED);

    /* BlockType's fields:
     * @var: Color color is the default color for each block type
     * @var: List<BlockType> VALUES is the iterable list of enum values
     * @var: int SIZE is the length of that ^ list
     * @var: Random RANDOM is the random value to help choose which blocktype to generate    
     * @var: int leftmost keeps track of which block is farthest left
     * @var: int rightmost keeps track of which block is farthest right*/
    private final Color color;
    private static final List<BlockType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private int leftmost = -1;
    private int rightmost = -1;
    private int bottommost = -1;

    /* BlockType's constructor:
     * @param Color color  */
    BlockType(Color color){ this.color = color; } 

    /* BlockType's getColor method:
     * @return Color   */
    public Color getColor(){ return this.color; }

    /* -Need to get a random block type every time we start a new game and kill another block
     * -Pulls a random value outta the list of blocktypes
     * @return new BlockType   */
    public static BlockType randomBlockType(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    
    /* Sometimes need the leftmost index of the pixel
     * @return int leftmost */
    public int getLeftmost(){
        if(leftmost < 0){
            switch(this){
                case I:
                    leftmost = 0;
                case J:
                    leftmost = 0;
                case L:
                    leftmost = 1;
                case O:
                    leftmost = 2;
                case S:
                    leftmost = 2;
                case T:
                    leftmost = 1;
                case Z:
                    leftmost = 0;
                default:
                    leftmost = 0;
            }
        }
        return leftmost;
    }
    
    /* Sometimes need the rightmost index of the pixel
     * @return int rightmost */
    public int getRightmost(){
        if (rightmost < 0){
            switch(this){
                case I:
                    rightmost = 3;
                case J:
                    rightmost = 3;
                case L:
                    rightmost = 0;
                case O: 
                    rightmost = 1;
                case S:
                    rightmost = 1;
                case T:
                    rightmost = 3;
                case Z:
                    rightmost = 3;
                default:
                    rightmost = 3;
            } 
        }
        return rightmost;
    }

    /* Needed for moving down: the index of the bottommost pixel
     * @return bottommost */
    public int getBottommost(){
        if(bottommost < 0){
            switch(this){
                case I:
                    bottommost = 3;
                case J:
                    bottommost = 1;
                case L:
                    bottommost = 1;
                case O: 
                    bottommost = 2;
                case S:
                    bottommost = 2;
                case T:
                    bottommost = 1;
                case Z:
                    bottommost = 2;
                default:
                    bottommost = 3;
            } 
        }
        return bottommost;
    }


    /* rightmost accessor
     * @param newRightmost */
    public void setRightmost(int newRightmost){
        if (newRightmost > 3){}
        else { rightmost = newRightmost; }
    }

    /* leftmost accessor
     * @param newLeftmost */
    public void setLeftmost(int newLeftmost){
        if (newLeftmost > 3){}
        else { leftmost = newLeftmost; }
    }

    /* bottommost accessor
     * @param newBottommost */
    public void setBottommost(int newBottommost){
        if (newBottommost > 3){}
        else{ bottommost = newBottommost;}
    }

    /* -resets the bounds
     * @param int nextPosition, representing the position that you will be transitioning to*/
    public void checkRotate(int nextPosition){
        switch(this){
            case I: 
                break; //Is the same
            case J:
                if (nextPosition == 0){
                    leftmost = 0;
                    rightmost = 3;
                    bottommost = 1;
                }
                if (nextPosition == 1){
                    leftmost = 1;
                    rightmost = 0;
                    bottommost = 3;
                }
                if (nextPosition == 2){
                    leftmost = 3;
                    rightmost = 1;
                    bottommost = 0;
                }
                if (nextPosition == 3){
                    leftmost = 0;
                    rightmost = 1;
                    bottommost = 1;
                } 
            case L:
                if (nextPosition == 0){
                    leftmost = 1;
                    rightmost = 0;
                    bottommost = 3;
                }
                if (nextPosition == 1){
                    leftmost = 1;
                    rightmost = 0;
                    bottommost = 0;
                }
                if (nextPosition == 2){
                    leftmost = 0;
                    rightmost = 1;
                    bottommost = 0;
                }
                if (nextPosition == 3){
                    leftmost = 0;
                    rightmost = 1;
                    bottommost = 1;
                } 
            case O: 
                break; //Doesn't do any moving
            case S:
                if (nextPosition == 0){
                    leftmost = 2;
                    rightmost = 1;
                    bottommost = 2;
                }
                if (nextPosition == 1){
                    leftmost = 2;
                    rightmost = 0;
                    bottommost = 1;
                } 
            case T:
                if (nextPosition == 0){
                    leftmost = 1;
                    rightmost = 3;
                    bottommost = 1;
                }
                if (nextPosition == 1){
                    leftmost = 1;
                    rightmost = 0;
                    bottommost = 3;
                }
                if (nextPosition == 2){
                    leftmost = 3;
                    rightmost = 1;
                    bottommost = 0;
                }
                if (nextPosition == 3){
                    leftmost = 0;
                    rightmost = 1;
                    bottommost = 1;
                } 
            case Z:
                if (nextPosition == 0){
                    leftmost = 0;
                    rightmost = 3;
                    bottommost = 2;
                }
                if (nextPosition == 1){
                    leftmost = 2;
                    rightmost = 0;
                    bottommost = 3;
                }
                
            default:
                break; //do nothing
        } 
    }//rotatoe

}//BlockType
    
