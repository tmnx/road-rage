/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is a ATV vehicle sub-class. It's parent class is the AbstractVehicle.
 * An ATV randomly go straight, left, or right. It never reverse. An ATV can
 * travel on any terrain except walls.
 * 
 * @author Minh Nguyen
 * @version 26 October 2018
 */
public final class Atv extends AbstractVehicle {
    
    /**
     * A constant for the dead time for the ATV.
     */
    private static final int DEATH_TIME = 25;
    
    /**
     * A constructor for constructing an ATV vehicle.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     */
    public Atv(final int theX, final int theY, final Direction theDir)  {
        // passes arguments to the parent class to construct an ATV vehicle.
        super(theX, theY, theDir, DEATH_TIME);
    }
    
    /**
     * Returns if ATV can pass with given terrain type and light.
     * 
     * Note: an ATV drive through all traffic lights and cross-walk lights.
     * 
     * @param theTerrain is the terrain type.
     * @param theLight is the light color
     * @return a boolean expression of whether the vehicle can pass or not.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return isTerrainValid(theTerrain);
    }
    
    /**
     * Returns the direction an ATV would like to move.
     * 
     * Note: an ATV can travel on any terrain except walls and it randomly
     * choose to go straight, left, or right.
     * 
     * @param theNeighbors is the map of the neighboring terrain.
     * @return direction is the direction the ATV prefers to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        // List to hold valid directions
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
        
        // Pick a random direction from the valid directions
        return pickRandom(validDirections);
    }
    
    /**
     * A helper method to determine if the terrain type is valid for the ATV.
     * 
     * Note: An ATV moves on all terrain except walls.
     * 
     * @param theTerrain is  the terrain type.
     * @return boolean expression of whether the terrain type is valid for the vehicle.
     */
    @Override
    protected boolean isTerrainValid(final Terrain theTerrain) {
        
        return !(theTerrain == Terrain.WALL);
    }

}
