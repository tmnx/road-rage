/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */
package model;

/**
 * This is a Taxi vehicle sub-class of the Car class. A Taxi prefers to drive straight;
 * if not left; if not right. As a last resort, it turns around.
 * It stops for red traffic light, and (temporarily) for red cross-walk lights.
 * A Taxi travels only on streets, lights, and cross-walk.
 * 
 * @author Minh Nguyen
 *@version 26 October 2018
 */
public final class Taxi extends Car {
    
    /**
     * A constant that holds the clock cycles for the cross-walk light.
     */
    private static final int CLOCK_CYCLES = 3;
    
    /**
     * An instance field to hold a counter for the cross-walk light.
     */
    private int myCount;
    
    /**
     * A constructor to construct a Taxi vehicle.
     * 
     * @param theX is the x coordinate of the vehicle.
     * @param theY is the y coordinate of the vehicle.
     * @param theDir is the direction the vehicle is facing.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        // passes arguments to the parent class to construct a Taxi vehicle.
        super(theX, theY, theDir);
        
        myCount = 0;
    }
    
    /**
     * Returns whether the Taxi can pass.
     * Note: Taxi can only pass when street light is not red, and stop
     * temporarily for red cross-walk light.
     * 
     * @param theTerrain is the terrain type.
     * @param theLight is the light color
     * @return pass is a boolean expression of whether the vehicle can pass or not.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        
        boolean pass = false;
        
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            // Count since light is red at cross-walk.
            myCount++;
            if (myCount == CLOCK_CYCLES) {
                // if count is 3, Taxi can pass.
                pass = true;
                // reset count.
                myCount = 0;
            }
        } else {
            // Determine if Taxi can pass:
            pass = !(theTerrain == Terrain.LIGHT && theLight == Light.RED)
                            && isTerrainValid(theTerrain);
            myCount = 0;
        }
        
        return pass;
    }

}
