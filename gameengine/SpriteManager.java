/* Manages the little objects called sprites
 * 
 * @author  Zack Raver
 * @date    12/21/14
 * 
 * groups together all the objects 
 */

package gameengine;

import java.util.*;

@SuppressWarnings("unchecked")
public class SpriteManager {

    //all the sprite objects currently in play
    private final static List<Sprite> GAME_ACTORS = new ArrayList<Sprite>();

    //a global single threaded list used to check collisions againt other
    //sprite objects
    private final static List<Sprite> CHECK_COLLISION_LIST = new ArrayList<Sprite>();

    //a global single threaded list used to cleanup sprite objects
    private final static Set<Sprite> CLEAN_UP_SPRITES = new HashSet<Sprite>();

    //a global single threaded list used to add in sprite objects
    private final static Set<Sprite> ADD_IN_SPRITES = new HashSet<Sprite>();

    //return all actors
    public List<Sprite> getAllSprites(){ return this.GAME_ACTORS; }

    //VarArgs of sprites to be added to the game
    public void addSprites(Sprite... sprites) {
        GAME_ACTORS.addAll(Arrays.asList(sprites));
    }

    //VarArgs of sprites to be removed from the game
    public void removeSprites(Sprite... sprites){
        GAME_ACTORS.removeAll(Arrays.asList(sprites));
    }

    //Returns a set of sprites to be removed from the game
    public Set<Sprite> getSpritesToBeRemoved() { return CLEAN_UP_SPRITES; }

    //Add sprite objects to the hashset to be removed
    public void addSpritesToBeRemoved(Sprite... sprites) {
        if (sprites.length > 1){
            CLEAN_UP_SPRITES.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
            CLEAN_UP_SPRITES.add(sprites[0]);
        }
    }

    //Add sprite objects to the list to be added
    public void addSpritesToBeAdded(Sprite... sprites){
        if (sprites.length > 1){
            ADD_IN_SPRITES.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
            ADD_IN_SPRITES.add(sprites[0]);
        }
    }

    //returns a list of sprite objects to assist in collision checks
    //this is a temporary and a is a copy of all current sprite objects 
    // (cope of GAME_ACTORS)
    public List<Sprite> getCollisionsToCheck(){ return CHECK_COLLISION_LIST; }

    //Clears the list of sprite objects in the collision check list 
    public void resetCollisionsToCheck(){
        CHECK_COLLISION_LIST.clear();
        CHECK_COLLISION_LIST.addAll(GAME_ACTORS);
    }

    //Removes sprite objects and nodes from all temporary 
    //collections such as CLEAN_UP_SPRITES.
    //The sprite to be removed will be removed from 
    // GAME_ACTORS also
    public void cleanupSprites(){
        //remove from actors list
        GAME_ACTORS.removeAll(CLEAN_UP_SPRITES);
        //add the queued sprites
        GAME_ACTORS.addAll(ADD_IN_SPRITES);
        //reset the clean up sprites
        ADD_IN_SPRITES.clear();
        CLEAN_UP_SPRITES.clear();
    }
}
