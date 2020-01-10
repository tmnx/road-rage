/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is a Human vehicle sub-class of the AbstractVehicle class.
 * A Human never reverse and move in random left, right, or straight. It is always
 * on grass or crosswalks. It travel through yellow and red cross-walk light.
 * 
 * @author Minh Nguyen
 * @version 26 October 2018
 */
public final class Human extends AbstractVehicle {
    
    /**
     * A constant to hold the death time of the Human.
     */
    private static final int DEATH_TIME = 45;
    
    /**
     * A constructor for the Human vehicle.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        // Passes arguments to the super class constructor to construct a Human:
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    /**
     * Returns if the Human can pass with given terrain type and light.
     * 
     * Note: Human travel through yellow and red cross-walk lights, but not
     * green cross-walk light.
     * 
     * @param theTerrain is the terrain type.
     * @param theLight is the light color
     * @return a boolean expression of whether the vehicle can pass or not.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        
        return (isCrosswalk(theTerrain)
                        && (theLight == Light.YELLOW || theLight == Light.RED))
                        || isTerrainValid(theTerrain);
    }
    
    /**
     * Returns a direction the Human prefers to move.
     * 
     * Note: a Human move in random left, right, straight and only travel on grass
     * and cross-walk.
     * 
     * @param theNeighbors is the map of the neighboring terrain.
     * @return direction is the direction the vehicle prefers to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        // List to hold valid directions:
        final List<Direction> validDirections = new LinkedList<>();
        final Direction direction;
        
        // If CROSSWALK is available, pick CROSSWALK:
        if (isCrosswalk(theNeighbors.get(getDirection()))) {
            direction = getDirection();
        } else if (isCrosswalk(theNeighbors.get(getDirection().left()))) {
            direction = getDirection().left();
        } else if (isCrosswalk(theNeighbors.get(getDirection().right()))) {
            direction = getDirection().right();
        } else {
            // Else, check for direction with valid terrain:
            if (isTerrainValid(theNeighbors.get(getDirection()))) {
                validDirections.add(getDirection());
            }
            if (isTerrainValid(theNeighbors.get(getDirection().left()))) {
                validDirections.add(getDirection().left());
            }
            if (isTerrainValid(theNeighbors.get(getDirection().right()))) {
                validDirections.add(getDirection().right());
            }
            
            if (!validDirections.isEmpty()) {
                // Pick a random direction
                direction = pickRandom(validDirections);
            } else {
                // If left, right, or straight are invalid, reverse
                direction = getDirection().reverse();
            }
        }
        
        return direction;
    }
    
    /**
     * Returns if the terrain type of a certain direction is a cross-walk.
     * A helper method for chooseDirection.
     * 
     * @param theTerrain is the type of terrain.
     * @return a boolean expression of whether the terrain type is a cross-walk.
     */
    private boolean isCrosswalk(final Terrain theTerrain) {
        
        return theTerrain == Terrain.CROSSWALK;
    }
    
    /**
     * A helper method to determine if the terrain type is valid for the Human.
     * 
     * @param theTerrain is the terrain type.
     * @return boolean expression of whether the terrain type is valid for the vehicle.
     */
    @Override
    protected boolean isTerrainValid(final Terrain theTerrain) {
        
        return theTerrain == (Terrain.GRASS);
    }

}
