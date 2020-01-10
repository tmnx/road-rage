/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */
package model;

import java.util.Map;

/**
 * This is a Bicycle vehicle sub-class of the AbstractVehicle.
 * A Bicycle prefers to travel on trails, even though it can travel on streets,
 * cross-walks and lights. It stops for yellow and red traffic/cross-walk lights.
 * 
 * @author minhnguyen
 * @version 26 October 2018
 */
public class Bicycle extends AbstractVehicle {
    
    /**
     * A constant of the death time of the Bicycle.
     */
    private static final int DEATH_TIME = 35;
    
    /**
     * A constructor to construct a Bicycle vehicle.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir)  {
        // passes arguments to the parent class to construct an ATV vehicle.
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    /**
     * Returns if the Bicycle can pass with given terrain type and light.
     * 
     * @param theTerrain is the terrain type.
     * @param theLight is the light color
     * @return a boolean expression of whether the vehicle can pass or not.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        
        return (isTerrainValid(theTerrain) || isTrail(theTerrain))
                        && !((theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.LIGHT)
                        && (theLight == Light.RED || theLight == Light.YELLOW));
    }

    /**
     * Returns a direction the Bicycle would like to move.
     * 
     * @param theNeighbors is the map of the neighboring terrain.
     * @return direction is the direction the vehicle prefers to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        final Direction direction;
        
        // If TRAIL is available, pick TRAIL:
        if (isTrail(theNeighbors.get(getDirection()))) {
            direction = getDirection();
        } else if (isTrail(theNeighbors.get(getDirection().left()))) {
            direction = getDirection().left();
        } else if (isTrail(theNeighbors.get(getDirection().right()))) {
            direction = getDirection().right();
        } else {
            // If TRAIL is not available, check for valid direction:
            if (isTerrainValid(theNeighbors.get(getDirection()))) {
                direction = getDirection();
            } else if (isTerrainValid(theNeighbors.get(getDirection().left()))) {
                direction = getDirection().left();
            } else if (isTerrainValid(theNeighbors.get(getDirection().right()))) {
                direction = getDirection().right();
            } else {
                // Reverse as a last resort
                direction = getDirection().reverse();
            }
        }
        
        return direction;
    }
    
    /**
     * A helper method that return if the terrain type of a certain direction is a trail.
     * 
     * @param theTerrain is the type of terrain.
     * @return a boolean expression of whether the terrain type is a trail.
     */
    private boolean isTrail(final Terrain theTerrain) {
        
        return theTerrain == Terrain.TRAIL;
    }

}
