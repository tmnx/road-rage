/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */

package model;

import java.util.List;
import java.util.Random;

/**
 * An abstract class that implements the Vehicle interface. This class
 * is the parent class for Vehicle sub-classes.
 * 
 * @author Minh Nguyen
 * @version 26 October 2018
 */
public abstract class AbstractVehicle implements Vehicle {
    
    /**
     * A Random that is used for generating random number to pick from a
     * Set of directions.
     */
    private static final Random RANDOM = new Random();
    
    /**
     * A final instance field that holds the initial x coordinate of the vehicle.
     */
    private final int myInitX;
    
    /**
     * A final instance field that holds the initial y coordinate of the vehicle.
     */
    private final int myInitY;
    
    /**
     * A final instance fields that holds the initial direction the vehicle was facing.
     */
    private final Direction myInitDirection;
    
    /**
     * An instance field that holds the x coordinate of the vehicle.
     */
    private int myX;
    
    /**
     * An instance field that holds the y coordinate of the vehicle.
     */
    private int myY;
    
    /**
     * An instance field that holds the direction the vehicle is facing.
     */
    private Direction myDir;
    
    /**
     * An instance field that holds the death time of the vehicle.
     */
    private int myDeathTime;
    
    /**
     * An instance fields that holds the alive status of the vehicle.
     */
    private boolean myAlive;
    
    /**
     * An instance fields that is a counter for the dead time of the vehicle.
     */
    private int myCount;
    
    /**
     * A protected constructor that initializes instance fields.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     * @param theDeathTime is the death time of the vehicle.
     */
    protected AbstractVehicle(final int theX, final int theY,
                              final Direction theDir, final int theDeathTime) {
        
        // Instantiates fields:
        myInitX = theX;
        myInitY = theY;
        myInitDirection = theDir;
        
        myX = theX;
        myY = theY;
        myDir = theDir;
        myDeathTime = theDeathTime;
        myAlive = true;
        myCount = 0;
    }

    /**
     * Updates the status of the vehicle after the collision.
     * Note: collisions only have an effect when they occur between two vehicles that
     * are alive. The vehicle with more death time is killed.
     */
    @Override
    public void collide(final Vehicle theOther) {
        if (myAlive && theOther.isAlive()) {
            myAlive = this.getDeathTime() <= theOther.getDeathTime();
        }
    }

    /**
     * Returns the number of updates between the vehicle's death and when it
     * should revive.
     */
    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    /**
     * Returns the name if the image file of the vehicle.
     */
    @Override
    public String getImageFileName() {
        
        final StringBuilder builder = new StringBuilder();
        
        builder.append(getClass().getSimpleName().toLowerCase());
        if (!isAlive()) {
            builder.append("_dead");
        }
        builder.append(".gif");
        
        return builder.toString();
    }

    /**
     * Returns the direction the vehicle is facing.
     */
    @Override
    public Direction getDirection() {
        return myDir;
    }

    /**
     * Returns the x coordinate of the vehicle.
     * 
     * @return myX is the x coordinate of the vehicle
     */
    @Override
    public int getX() {
        return myX;
    }

    /**
     * Returns the y coordinate of the vehicle.
     * 
     * @return myY is the y coordinate of the vehicle
     */
    @Override
    public int getY() {
        return myY;
    }

    /**
     * Returns whether the vehicle is alive.
     */
    @Override
    public boolean isAlive() {
        return myAlive;
    }

    /**
     * Poke the vehicle if it is dead. This notify the dead vehicle that 1 movement
     * round has passed and allow it to revive itself appropriately.
     * 
     * Note: live vehicle are never poked.
     */
    @Override
    public void poke() {
        if (!myAlive) {
            myCount++;
            if (myCount > myDeathTime) {
                myAlive = true;
                setDirection(Direction.random());
                myCount = 0;
            }
        }
    }

    /**
     * Turns the vehicle back to its initial state when it was constructed,
     * which includes position, direction and being alive.
     */
    @Override
    public void reset() {
        // Resets all fields 
        setX(myInitX);
        setY(myInitY);
        setDirection(myInitDirection);
        myAlive = true;
    }

    /**
     * Mutators for the direction the vehicle is facing.
     */
    @Override
    public void setDirection(final Direction theDir) {
        myDir = theDir;
    }

    /**
     * Mutators for the x coordinate of the vehicle.
     */
    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    /**
     * Mutators for the y coordinate of the vehicle.
     */
    @Override
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Returns a string representation of the vehicle.
     * 
     * The String is formatted as:
     * [Vehicle: name, Death Time: death time]
     * 
     * @return a String representation of the vehicle.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        
        builder.append("[Vehicle: ");
        builder.append(this.getClass().getSimpleName());
        builder.append(", Death Time: ");
        builder.append(myDeathTime);
        builder.append(", Count Moves: ");
        builder.append(myCount);
        
        return builder.toString();
    }
    
    /**
     * A helper method to determine if the terrain type is valid for the vehicle.
     * 
     * @param theTerrain is the terrain type.
     * @return boolean expression of whether the terrain type is valid for the vehicle.
     */
    protected boolean isTerrainValid(final Terrain theTerrain) {
        
        return theTerrain == Terrain.STREET
                        || theTerrain == Terrain.LIGHT
                        || theTerrain == Terrain.CROSSWALK;
        
    }
    
    /**
     * Returns a random valid direction that the Vehicle can move to.
     * A helper method for the chooseDirection method.
     * 
     * @param theList is the list of valid directions.
     * @return direction is a random valid direction.
     */
    protected Direction pickRandom(final List<Direction> theList) {
        
        return theList.get(RANDOM.nextInt(theList.size()));
    }

}
