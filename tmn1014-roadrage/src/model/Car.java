/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */

package model;

import java.util.Map;

/**
 * This is a Car vehicle sub-class. The parent class is the AbstractVehicle.
 * A Car dies if it collides with a Truck. It prefers to drive straight; if not left;
 * if not right; as last resort, turns around. It stops for red traffic light, and
 * red and yellow cross-walk lights. A Car travels only on streets, lights, and
 * cross-walk.
 * 
 * @author Minh Nguyen
 * @version 26 October 2018
 */
public class Car extends AbstractVehicle {
    
    /**
     * A constant field to hold the death time of the Car.
     */
    private static final int DEATH_TIME = 15;
    
    /**
     * A constructor to construct a Car vehicle.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     */
    public Car(final int theX, final int theY, final Direction theDir)  {
        // passes arguments to the parent class to construct a Car vehicle.
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    /**
     * Returns whether the Car can pass or not with this type of terrain
     * and light.
     * Note: A Car can only pass when the street light is not red, and the
     * cross-walk light is not red or yellow.
     * 
     * @param theTerrain is the terrain type.
     * @param theLight is the light color
     * @return a boolean expression of whether the vehicle can pass or not.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        
        return !((theTerrain == Terrain.LIGHT && theLight == Light.RED)
                        || (theTerrain == Terrain.CROSSWALK
                        && (theLight == Light.RED || theLight == Light.YELLOW)));
    }
    
    /**
     * Returns the direction the Car would like to move.
     * 
     * Note: A Car prefers to go straight. If it can't, it turns left.
     * If not, it turns right. As a last resort, it turns around.
     * 
     * @param theNeighbors is the map of the neighboring terrain.
     * @return direction is the direction the Car prefers to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        // Default direction is straight if it can:
        Direction direction = getDirection();
        final boolean isValid = isTerrainValid(theNeighbors.get(direction));
        
        // If Car cannot move straight:
        if (!isValid) {
            if (isTerrainValid(theNeighbors.get(getDirection().left()))) {
                direction = getDirection().left();
            } else if (isTerrainValid(theNeighbors.get(getDirection().right()))) {
                direction = getDirection().right();
            } else {
                // Last resort, turn around:
                direction = getDirection().reverse();
            }
        }
        
        // Return valid direction:
        return direction;
    }

}
