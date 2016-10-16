//João Craveiro 2013136429
//João Faria 2013136446 
package project;
import java.util.ArrayList;

/**
 * Created by jcraveiro on 19/11/14.
 */
public class Hamming extends Agent {

    /**
     * Class constructor that calls parent constructor and initializes class specific variables.
     */
    public Hamming(String colour, String shape, int x, int y, int FOV, int maxHops) {
        super(colour, shape, x, y, FOV, maxHops);
    }

    /**
     * Implementation of the max Hamming distance policy.
     * In this implementation, in the first iteration, as there aren't any objects in the agent's memory to compare the difference, the method returns the first seen object.
     * In the following iterations, if there is only one object in the FOV, the agent doesn't bother to calculate any Hamming differences and simply returns the object.
     * If there is more than one object in the FOV the method calculates the difference between an object and those in memory, selects the lowest value and adds it to hammingDistances, a variable that contains each object's hamming distance.
     * Then recurring to the selectHighest method we iterate through the hammingDistances array to select the highest hamming distance value, as there is a direct correlation between the index of the hamming distance in the hammingDistance array and the index of the objects in the nextPossibleObjects ArrayList we can select teh correct InanimateObject and return it.
     * @param nextPossibleObjects ArrayList that contains all next possible objects from which the agent will choose one depending on it's policy.
     * @return it returns the next object to which the agent will move.
     */
    public InanimateObject getNextObject(ArrayList<InanimateObject> nextPossibleObjects) {
        int [] hammingDistances = new int [nextPossibleObjects.size()];
        int index;

        if (memory.size() == 0) {
            return nextPossibleObjects.get(0);
        }

        if (nextPossibleObjects.size() == 1) {
            return nextPossibleObjects.get(0);
        }

        for (int i = 0; i < nextPossibleObjects.size(); i++) {
            InanimateObject object = nextPossibleObjects.get(i);
            int tempValue = 4;

            for(InanimateObject memoryObject: memory) {
                int tempDistance = InanimateObject.getHammingDistance(object, memoryObject);
                if (tempDistance < tempValue) {
                    tempValue = tempDistance;
                }
            }
            hammingDistances[i] = tempValue;
        }

        index = selectHighest(hammingDistances);

        return nextPossibleObjects.get(index);
    }

    /**
     * The method iterates through an int array and returns the index of the element with the highest value.
     * @param array Array to iterate through
     * @return The method returns the index of the highest value in the array
     */
    private int selectHighest(int [] array) {
        int index = -1; //index of the highest hamming distance.
        int temp = -1; //value will be used to select the highest possible value of hamming distance.

        for (int j = 0; j < array.length; j++) {

            if (array[j] > temp) {
                temp = array[j];
                index = j;
            }
        }
        return index;
    }


}
