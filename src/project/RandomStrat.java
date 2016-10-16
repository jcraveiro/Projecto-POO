//João Craveiro 2013136429
//João Faria 2013136446 
package project;
import java.util.ArrayList;
import java.util.Random;
/**
 * Child class of Agent this is the implementation of the agent with the random policy.
 */
public class RandomStrat extends Agent {

    /**
     * This variable is used to generate a random value.
     */
    private Random randomGenerator;

    /**
     * Class constructor that calls parent constructor and initializes class specific variables.
     */
    public RandomStrat(String colour, String shape, int x, int y, int FOV, int maxHops) {
        super(colour, shape, x, y, FOV, maxHops);
        this.randomGenerator =  new Random();
    }

    /**
     * Random policy implementation of the getNextObject method.
     * This implementation chooses a random InanimateObject from the ArrayList of InanimateObjects in it's FOV.
     * @param nextPossibleObjects ArrayList that contains all next possible objects from which the agent will choose one depending on it's policy.
     * @return The method returns the next object to which the agent will move.
     */
    protected InanimateObject getNextObject(ArrayList<InanimateObject> nextPossibleObjects) {
        int index = randomGenerator.nextInt(nextPossibleObjects.size());
        InanimateObject nextObject =  nextPossibleObjects.get(index);
        return  nextObject;
    }

}
