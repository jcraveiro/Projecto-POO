//João Craveiro 2013136429
//João Faria 2013136446 
package project;
import java.util.ArrayList;

/**
 * Child class of Agent this class implements the methods of the closest strategy.
 */
public class Closest extends Agent {

    /**
     * Class constructor that calls parent constructor.
     */
    public Closest(String colour, String shape, int x, int y, int FOV, int maxHops) {

        super(colour, shape, x, y, FOV, maxHops);
    }

    /**
     * This method will select the next object to which the agent will move.
     * If there is only one object in the agent's FOV it selects that one in any other case  it calculates the distance between the agent and the objects in it's FOV.
     * The method then chooses the one that is closest.
     * @param nextPossibleObjects ArrayList that contains all next possible objects from which the agent will choose one depending on it's policy.
     * @return  It returns the next object to which the agent will move.
     */
    public InanimateObject getNextObject(ArrayList<InanimateObject> nextPossibleObjects) {
       if (nextPossibleObjects.size() == 1) {
            return nextPossibleObjects.get(0);
        } else {
            InanimateObject nextObject = nextPossibleObjects.get(0);
            int distance = Coordinates.distanceBetween(this.getCoords(), nextObject.getCoords());

            for (InanimateObject object : nextPossibleObjects) {
                if (Coordinates.distanceBetween(this.getCoords(), object.getCoords()) < distance) {
                    nextObject = object;
                    distance = Coordinates.distanceBetween(this.getCoords(), object.getCoords());
                }
            }
            return nextObject;
        }
    }
}
