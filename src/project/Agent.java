//João Craveiro 2013136429
//João Faria 2013136446 
package project;

import my.interfaceUI.UIResults;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Child class of Entity and parent Class of all three types of Agents.
 * This is an abstract class as only it's child classes are instantiated.
 * This class has all the variables specific to all the agents such has their memory and some agent related methods.
 */
public abstract class Agent extends Entity{
    /**
     * FOV stands for field of view which is the distance in units that the agent can see.
     */
    protected int FOV;

    /**
     * maxHops represents the max number of movements the agent can do before ending it's run in the environment.
     */
    private int maxHops;

    /**
     * distanceTravelled represents the distance travelled by the agent.
     */
    private int distanceTravelled = 0;

    /**
     * Memory that stores the InanimateObjects that the agent has "captured".
     */
    protected ArrayList<InanimateObject> memory;

    /**
     * ArrayList that is refreshed every movement that holds the InanimateObjects in the field of view of the agent.
     */
    protected ArrayList<InanimateObject> nextPossibleObjects;

    /**
     * ArrayList that saves all the positions of the Agent.
     */
    protected ArrayList<Coordinates>  stepSequence;

    /**
     * Class constructor that calls parent constructor and initializes class specific variables.
     */
    public Agent(String colour, String shape, int x, int y, int FOV, int maxHops) {
        super(colour, shape, x, y);
        this.FOV = FOV;
        this.maxHops = maxHops;
        this.memory = new ArrayList<InanimateObject>();
        this.nextPossibleObjects = new ArrayList<InanimateObject>();
        this.stepSequence = new ArrayList<Coordinates>();
    }

    /**
     * Getter that returns the nextPossibleObjects ArrayList.
     */
    public ArrayList<InanimateObject> getNextPossibleObjectsArray() {

        return  nextPossibleObjects;
    }

    /**
     * Getter that returns the max number of hops of the agent.
     */
    public int getMaxHops() {
        return  maxHops;
    }

    /**
     * Getter that returns the memory ArrayList of the agent.
     */
    public ArrayList<InanimateObject> getMemory() {
        return memory;
    }

    /**
     * Getter that returns the agent's step sequence.
     */
    public ArrayList<Coordinates> getStepSequence() {
        return stepSequence;
    }

    /**
     * Getter that returns the agent's total distance travelled.
     */
    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Debug function that prints an ArrayList of inanimate objects with their attributes.
     * @param array Arraylist that the method iterates through.
     */
    public void printArrayListInanimateObjects(ArrayList<InanimateObject> array) {

        int i = 0;

        for (InanimateObject object : array) {
            i++;
            System.out.printf("Objecto%d: Colour = %s Shape = %s Coordinates(%d, %d) Type = %s\n",i, object.getColour(), object.getShape(), object.getCoords().getX(), object.getCoords().getY(), object.getType());
        }

    }

    /**
     * Method that receives an ArrayList of coordinates and prints them in tuple format.
     * @param array ArrayList of coordinates that the method will iterate through.
     */
    public void printCoordinates(ArrayList<Coordinates> array) {
        int i = 0;

        for (Coordinates coords : array) {
            i++;
            System.out.printf("Step%d: (%d, %d)\n", i, coords.getX(), coords.getY());
        }
    }

    /**
     * Method that iterates through all InanimateObjects in the Environment and evaluates the distance between them and the agent.
     * If said distance is smaller than the agents FOV the object is added to the next possible objects ArrayList.
     * @param allObjects ArrayList of all objects in the Environment.
     * @return The method returns -1 in case of failure 1 in case of success
     */
    protected int setNextPossibleObjects(ArrayList<InanimateObject> allObjects) {
        Coordinates agentCoordinates = this.getCoords();

        for (InanimateObject object : allObjects) {
            if (Coordinates.distanceBetween(object.getCoords(), agentCoordinates) <= this.FOV && this.inMemory(object) == -1) {
                this.nextPossibleObjects.add(object);
            }
        }

        if (nextPossibleObjects.size() == 0) {
            return -1;
        }

        return 1;
    }

    /**
     * Method that clears the nextPossibleObjects ArrayList.
     */
    protected void resetNextPossibleObjects() {
        this.nextPossibleObjects.clear();
    }

    /**
     * Method that checks if an InanimateObject is in the agent's memory.
     * @param toAdd InanimateObject that we will check.
     * @return The method returns -1 if the object isn't in the memory and 1 if it is.
     */
    protected int inMemory(InanimateObject toAdd) {
        for (InanimateObject object : memory) {
            if (toAdd == object) {
                return 1;
            }
        }

        return -1;
    }

    /**
     * Method to move the agent to  new coordinates.
     * @param nextCoords Agent's next coordinates.
     */
    protected void moveTo(Coordinates nextCoords) {
        Coordinates.updateCoords(this, nextCoords);
    }

    /**
     * Method that adds a new InanimateObject to the Agent's memory.
     * If the Object is already present in it's memory it doesn't.
     * @param toAdd InanimateObject that we will add to memory,
     */
    protected void addToMemory (InanimateObject toAdd) {
        int ArrayListSize = memory.size();
        int i;

        for (i = 0; i < ArrayListSize; i++)
        {
            InanimateObject temp = memory.get(i);
            if (temp == toAdd)
            {
                return;
            }
        }
        this.memory.add(toAdd);
    }

    /**
     * Abstract declaration of method that returns the next InanimateObject to which the agent is going to move.
     * This method is abstract because it will be implemented by all the child classes of Agent.
     * @param nextPossibleObjects ArrayList that contains all next possible objects from which the agent will choose one depending on it's policy.
     * @return The method returns the next InanimateObject to where the agent is going to move.
     */
    protected abstract InanimateObject getNextObject(ArrayList<InanimateObject> nextPossibleObjects);

    /**
     * Method that adds current location to the ArrayList that records the agent's movements.
     * As ArrayLists work with reference to objects and the agent's coordinates are constantly changing we must create a new object Coordinates with the values in order to store them properly.
     */
    protected void addToStepSequence() {
        Coordinates oldCoords = new Coordinates(this.getCoords().getX(), this.getCoords().getY());
        stepSequence.add(oldCoords);
    }

    /**
     * Method that compares the size of two ArrayLists.
     * @param allObjects First ArrayList that is going to be compared. It has all the InanimateObjects in the Environment.
     * @param memory Second ArrayList that is going to be compared. It contains the memory of the agent.
     * @return The method returns 1 if both ArrayLists have the same size or -1 if the sizes are different.
     */
    protected int compareArrayListsSize(ArrayList<InanimateObject> allObjects, ArrayList<InanimateObject> memory) {
        int allObjectsSize = allObjects.size();
        int memorySize = memory.size();

        if (memorySize == allObjectsSize) {
            return 1;
        }

        return -1;
    }

    /**
     * Method that is called in the end of each Agent's run that iterates through it's step sequence and calculate the total distance travelled by the Agent.
     * @param stepSequence Sequence of the Agent's Coordinates.
     * @return The method returns the total amount of distance travelled by the agent.
     */
    protected int calcDistanceTravalled(ArrayList<Coordinates> stepSequence) {
        int dist = 0;

        for (int i = 0; i < stepSequence.size()-1; i++) {
            dist += Math.abs((stepSequence.get(i+1).getX() - stepSequence.get(i).getX()) + (stepSequence.get(i+1).getY() - stepSequence.get(i).getY()));
        }

        return dist;
    }


    /**
     * Main method that invokes other methods to perform the agent's routine.
     * This method also writes to a log file all the information of the agent in each iteration (coordinates, perception and memory);
     * In the final iteration it writes the final statistics to the log file.
     * @param allObjects All Inanimate Objects in the environment.
     */
    public void routine(ArrayList<InanimateObject> allObjects) throws IOException {

        int hopsUsed = 0;

        FileHandling f = new FileHandling();

        if (this.id == 1) {

            f.openFileWrite("src/resources/log.txt");

        } else {
            f.openFileAppend("src/resources/log.txt");
        }

        f.writeLine("Agent " + getId());

        f.writeToLog(this, hopsUsed);
        hopsUsed++;

        while (hopsUsed <= maxHops) {
            InanimateObject nextObject;
            addToStepSequence();

            if (compareArrayListsSize(allObjects, memory) == 1) {
                distanceTravelled = calcDistanceTravalled(stepSequence);
                f.writeToLog(this, hopsUsed);
                f.writeStatsToLog(this);
                f.closeWrite();
                return;
            }

            if (setNextPossibleObjects(allObjects) == 1) { // percepcao
                nextObject = getNextObject(nextPossibleObjects);
                moveTo(nextObject.getCoords());
                addToMemory(nextObject);//memoria
            } else {
                this.coords.addFOV(FOV);
            }

            f.writeToLog(this, hopsUsed);
            resetNextPossibleObjects();
            hopsUsed++;
        }

        distanceTravelled = calcDistanceTravalled(stepSequence);
        f.writeStatsToLog(this);
        f.closeWrite();
    }

}
