/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */

package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is a Truck Vehicle sub-class. The parent class is AbstractVehicle.
 * The Truck survives all collisions and randomly select to go straight,
 * left, or right. As a last resort, it turns around.
 * A Truck never stop at traffic lights, but does stop for
 * red cross-walk lights only. A Truck travels only on streets, lights, and
 * cross-walk.
 * 
 * @author Minh Nguyen
 * @version 26 October 2018
 */
public final class Truck extends AbstractVehicle {
    
    /**
     * A constant of the death time of the truck.
     */
    private static final int DEATH_TIME = 0;

    /**
     * A constructor to make a Truck Vehicle.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        // passes arguments to the parent class to construct a Truck vehicle.
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    /**
     * Determines if a Truck can pass.
     * Note: a Truck can pass if the cross-walk light is not red.
     * 
     * @param theTerrain is the terrain type.
     * @param theLight is the light color
     * @return a boolean expression of whether the vehicle can pass or not.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {    
        return isTerrainValid(theTerrain)
                        && !(theTerrain == Terrain.CROSSWALK && theLight == Light.RED);
    }
    
    /**
     * Returns the direction the Truck would like to move.
     * 
     * Note: A Truck can only travel on streets, through traffic lights and cross-walk.
     * It randomly select to go straight, left, or right. As last resort, it turns around.
     * 
     * @param theNeighbors is the map of the neighboring terrain.
     * @return direction is the direction the Truck prefers to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        // List of valid terrains.
        final List<Direction> validDirections = new LinkedList<>();
        
        // Add valid terrains to list:
        if (isTerrainValid(theNeighbors.get(getDirection()))) {
            validDirections.add(getDirection());
        }
        if (isTerrainValid(theNeighbors.get(getDirection().left()))) {
            validDirections.add(getDirection().left());
        }
        if (isTerrainValid(theNeighbors.get(getDirection().right()))) {
            validDirections.add(getDirection().right());
        }
        
        // If there are no valid terrains (left, right, or straight), reverse:
        if (validDirections.isEmpty()) {
            validDirections.add(getDirection().reverse());
        }
        
        // Pick a random valid direction:
        return pickRandom(validDirections);
    }
    
}
