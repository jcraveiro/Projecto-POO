//João Craveiro 2013136429
//João Faria 2013136446 

package project;
import java.io.IOException;
import java.util.ArrayList;
import my.interfaceUI.UIResults;
/**
 * This class represents the environment in which the simulation is going to be running.
 * It defines the borders of the environments and holds all necessary variables for a successful simulation.
*/
public class Environment {

    /**
     * Static variable that holds globally the max value of x in this environment.
     */
    private static int x;

    /**
     *  Static variable that holds globally the max value of y in this environment.
     */
    private static int y;

    /**
     * allObjects is an ArrayList that contains all objects in it's environment.
     */
    private ArrayList<InanimateObject> allObjects;

    /**
     * allAgents is an ArrayList that contains all agents in it's environment.
     */
    private ArrayList<Agent> allAgents;

    /**
     * Class constructor that initializes the ArrayLists;
     */
    public Environment() {
        allObjects = new ArrayList<InanimateObject>();
        allAgents = new ArrayList<Agent>();
        Entity.setNumberOfEntities(0);
    }

    /**
     * Getter that returns the value of Y.
     */
    public static int getY() {

        return y;
    }

    /**
     * Getter that returns the value of X.
     */
    public static int getX() {

        return x;
    }

    /**
     * Sets the value of x;
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the value of y;
     */
    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Agent> getAllAgents (){
        return allAgents;
    }
    
    /**
     * This methods tries to create the simulation environment if it succeds runs the simulation
     * @param teste
     * @return The method returns -1 if readConfig returns -1 else returns 1.
     */
    public int createEnvironment(Environment teste){
        FileHandling f = new FileHandling();
        try {
            if(f.readConfig("src/resources/config.txt", teste) == -1){
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            teste.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    /**
     * This method adds entities to the environment, whether they are objects or agents.
     * It first checks if the coordinates of the object are possible in the current environment.
     * Then before adding it verifies if there ara any entities of the same type in the same position.
     * @param toAdd Is the Entity we are trying to add.
     */
    public void addToEnvironment(Entity toAdd) {

        if (toAdd.getCoords().inEnvironment(toAdd.getCoords().getX(), toAdd.getCoords().getY()) == -1) {
            System.out.println("It wasn't possible to add the entity to the environment. It's coordinates were incorrect.");
            return;
        }

        if (toAdd instanceof InanimateObject) {

            for (InanimateObject iobject : allObjects) {
                if (toAdd.checkSamePosition(iobject) == 1) {
                    return;
                }
            }
            allObjects.add((InanimateObject)toAdd);
        }
        else if (toAdd instanceof Agent) {
            for (Agent agent : allAgents) {
                if (toAdd.checkSamePosition(agent) == 1) {
                    return;
                }
            }
            allAgents.add((Agent)toAdd);
        }
    }

    /**
     * Main method of the program. It iterates through all the agents and calls their routine.
     */
    public void run() throws IOException {
        
        if (allObjects.size() == 0 || allAgents.size() == 0) {
            System.out.println("Can't start simulation without agents or inanimate objects");
        }

        for (Agent agent : allAgents) {
            agent.routine(allObjects);
         
        }
    }
}
