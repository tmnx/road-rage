/*
 * TCSS 305 - Autumn 2018
 * Assignment 3 - Road Rage
 */

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a unit tests class for the Truck class.
 * 
 * @author Minh Nguyen
 * @version 26 October 2018
 */
public class TruckTest {
    
    /**
     * A constant field of the number of times to repeat a test
     * to have a high probability that all random possibilities
     * have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /**
     * An instance field to hold a Truck vehicle to test the Truck class.
     */
    private Truck myTruck;

    /**
     * Construct a Truck vehicle to use for testing the Truck class.
     */
    @Before
    public void setUp() {
        myTruck = new Truck(1, 7, Direction.SOUTH);
    }

    /**
     * Test method for the Truck constructor with initializing x, y coordinates.
     * The Truck is expected to have the given x = 1 and y = 7.
     */
    @Test
    public void testTruckConstructorXY() {
        
        assertEquals("Truck x coordinate is not initialized correctly.",
                     1, myTruck.getX());
        
        assertEquals("Truck y coordinate is not initialized correctly.",
                     7, myTruck.getY());
    }
    
    /**
     * Test method for the Truck constructor with initializing direction.
     * The Truck is expected to have the given direction South.
     */
    @Test
    public void testTruckConstructorDirection() {
        
        assertEquals("Truck direction is not initialized correctly.",
                     Direction.SOUTH, myTruck.getDirection());
    }
    
    /**
     * Test method for the Truck constructor with initializing death time.
     * The Truck is expected to have the given death time of zero.
     */
    @Test
    public void testTruckConstructorDeathTime() {
        
        assertEquals("Truck death time is not initialized correctly.",
                     0, myTruck.getDeathTime());
    }
    
    /**
     * Test method for the Truck constructor with initializing alive status.
     * The Truck is expected to be alive when it is constructed.
     */
    @Test
    public void testTruckConstructorAlive() {
        
        assertTrue("Truck alive status is not initialized correctly.",
                   myTruck.isAlive());
    }

    /**
     * Test method for the canPass method.
     * 
     * Note: Truck travel only on street, light, and crosswalks. It cannot
     * pass when the crosswalk light is red. Otherwise, it can pass.
     */
    @Test
    public void testCanPass() {
        
        final List<Terrain> validTerrains = new ArrayList<>();

        // Add all valid types of terrains to list.
        validTerrains.add(Terrain.LIGHT);
        validTerrains.add(Terrain.CROSSWALK);
        validTerrains.add(Terrain.STREET);
        
        // Test each terrain type:
        for (final Terrain terrain : Terrain.values()) {
            
            // Test each light condition:
            for (final Light light : Light.values()) {
                
                if (terrain == Terrain.LIGHT) {
                    // Truck can pass LIGHT under any light condition.
                    assertTrue("Truck should be able to pass LIGHT with "
                               + light, myTruck.canPass(terrain, light));
                    
                } else if (terrain == Terrain.CROSSWALK) {
                    // Truck can pass CROSSWALK if light is not RED.
                    
                    if (light == Light.GREEN) {
                        assertTrue("Truck should be able to pass CROSSWALK w/ GREEN light",
                                   myTruck.canPass(terrain, light));
                    } else if (light == Light.YELLOW) {
                        assertTrue("Truck should be able to pass CROSSWALK w/ YELLOW light",
                                   myTruck.canPass(terrain, light));
                    } else {
                        assertFalse("Truck should not be able to pass CROSSWALK w/ RED light",
                                    myTruck.canPass(terrain, light));
                    }
                } else if (terrain == Terrain.STREET) {
                    // Truck can pass STREET under any light condition.
                    assertTrue("Truck should be able to pass STREET with "
                               + light, myTruck.canPass(terrain, light));
                    
                } else if (!validTerrains.contains(terrain)) {
                    // Truck cannot pass with invalid terrains under any light condition.
                    assertFalse("Truck should not be able to pass "
                                + terrain + " with light " + light,
                                myTruck.canPass(terrain, light));
                }
            }
        }  
    }

    /**
     * Test method for the chooseDirection method of choosing random direction with streets.
     */
    @Test
    public void testChooseDirectionSurroundedByStreet() {
        
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        
        // Surround Truck by STREET:
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        neighbors.put(Direction.WEST, Terrain.STREET);
        
        boolean north = false;
        boolean east = false;
        boolean south = false;
        boolean west = false;
        
        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++) {
            final Direction direction = myTruck.chooseDirection(neighbors);
            
            if (direction == Direction.NORTH) { // reverse
                north = true;
            } else if (direction == Direction.EAST) {
                east = true;
            } else if (direction == Direction.SOUTH) {
                south = true;
            } else {
                west = true;
            }
        }
        
        assertTrue("Truck chooseDirection did not validly pick randomly.",
                    east & west & south);
        
        assertFalse("Truck reversed invalidly.", north);
    }
    
    /**
     * Test for chooseDirection method of choosing random direction with crosswalks.
     */
    @Test
    public void testChooseDirectionSurroundedByCrosswalk() {
        
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        
        // Surround Truck by CROSSWALK:
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.CROSSWALK);
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        
        boolean north = false;
        boolean east = false;
        boolean south = false;
        boolean west = false;
        
        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++) {
            final Direction direction = myTruck.chooseDirection(neighbors);
            
            if (direction == Direction.NORTH) { // reverse
                north = true;
            } else if (direction == Direction.EAST) {
                east = true;
            } else if (direction == Direction.SOUTH) {
                south = true;
            } else {
                west = true;
            }
        }
        
        assertTrue("Truck chooseDirection did not validly pick randomly.",
                   east & west & south);
       
        assertFalse("Truck reversed invalidly.", north);
    }
    
    /**
     * Test for chooseDirection method of reversing.
     */
    @Test
    public void testChooseDirectionOnStreetMustReverse() {
        
        for (final Terrain terrain : Terrain.values()) {
            
            // If terrain is invalid:
            if (!(terrain == Terrain.STREET
                            || terrain == Terrain.LIGHT
                            || terrain == Terrain.CROSSWALK)) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                
                neighbors.put(Direction.NORTH, Terrain.STREET);
                neighbors.put(Direction.EAST, terrain);
                neighbors.put(Direction.SOUTH, terrain);
                neighbors.put(Direction.WEST, terrain);
                
                // Truck must reverse since surrounded by invalid terrains:
                assertEquals("Truck should have reversed.",
                             Direction.NORTH, myTruck.chooseDirection(neighbors));
            }
        }
        
    }
    
    /**
     * Test for chooseDirection method of choosing right when it is the only option.
     */
    @Test
    public void testChooseDirectionRight() {
        
        for (final Terrain terrain : Terrain.values()) {
            
            // If terrain is invalid:
            if (!(terrain == Terrain.STREET
                            || terrain == Terrain.LIGHT
                            || terrain == Terrain.CROSSWALK)) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                
                neighbors.put(Direction.NORTH, Terrain.STREET);
                neighbors.put(Direction.EAST, Terrain.LIGHT);
                neighbors.put(Direction.SOUTH, terrain);
                neighbors.put(Direction.WEST, terrain);
                
                // Truck must turn right since surrounded by invalid terrains.
                // Truck should not reverse in this situation.
                assertEquals("Truck should have turned right.",
                             Direction.EAST, myTruck.chooseDirection(neighbors));
            }
        }
    }
    
    /**
     * Test for chooseDirection method of choosing left when it is the only option.
     */
    @Test
    public void testChooseDirectionLeft() {
        
        for (final Terrain terrain : Terrain.values()) {
            
            // If terrain is invalid:
            if (!(terrain == Terrain.STREET
                            || terrain == Terrain.LIGHT
                            || terrain == Terrain.CROSSWALK)) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                
                neighbors.put(Direction.NORTH, Terrain.STREET);
                neighbors.put(Direction.EAST, terrain);
                neighbors.put(Direction.SOUTH, terrain);
                neighbors.put(Direction.WEST, Terrain.CROSSWALK);
                
                // Truck must turn left since surrounded by invalid terrains.
                // Truck should not reverse in this situation.
                assertEquals("Truck should have turned left.",
                             Direction.WEST, myTruck.chooseDirection(neighbors));
            }
        }
        
    }
    
    /**
     * Test for chooseDirection method of choosing random left or right.
     */
    @Test
    public void testChooseDirectionRightLeft() {
        
        for (final Terrain terrain : Terrain.values()) {
            
            // If terrain is invalid:
            if (!(terrain == Terrain.STREET
                            || terrain == Terrain.LIGHT
                            || terrain == Terrain.CROSSWALK)) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                
                neighbors.put(Direction.NORTH, Terrain.STREET);
                neighbors.put(Direction.EAST, Terrain.CROSSWALK);
                neighbors.put(Direction.SOUTH, terrain);
                neighbors.put(Direction.WEST, Terrain.LIGHT);
                
                // Truck must turn left or right since surrounded by invalid terrains.
                // Truck should not reverse in this situation.
                // A List of valid directions:
                final List<Direction> validDirection = new ArrayList<>();
                validDirection.add(Direction.EAST);
                validDirection.add(Direction.WEST);
                
                assertTrue("Truck should turn left or right.",
                           validDirection.contains(myTruck.chooseDirection(neighbors)));
                
            }
        }
    }
     
}
