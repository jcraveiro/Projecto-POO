//João Craveiro 2013136429
//João Faria 2013136446 
package project;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class created to handle the coordinates and coordinates related methods of all entities.
 */
public class Coordinates {
    private int x;
    private int y;

    /**
     * Class constructor.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter to return the x position of the entity.
     */
    public int getX() {

        return x;
    }

    /**
     *  Getter to return the x position of the entity.
     */
    public int getY() {

        return y;
    }

    /**
     * Method that updates the coordinates of an agent to those of the received new coordinates.
     * @param agent Agent whose coordinates are going to be updated.
     * @param newCoordinates New coordinates of the agent.
     */
    public static void updateCoords(Agent agent, Coordinates newCoordinates) {

        agent.coords.x = newCoordinates.x;
        agent.coords.y = newCoordinates.y;

    }


    /**
     * Method that is used when there is no object in the sight of the agent.
     * It checks all possible coordinates inside the agent's fov except the current one and chooses a random one inside the environment for the agent to move.
     */
    public void addFOV(int fov) {
        Random randomGenerator = new Random();
        ArrayList<Coordinates> possibleCoordinates = new ArrayList<Coordinates>();
        int index;
        int x = this.getX();
        int y = this.getY();

        for (int row = y - fov; row <= y + fov; row++) {
            for (int col = x - fov; col <= x + fov; col++) {
                if (row == y && col == x ) {
                } else {
                    if (inEnvironment(col, row) == 1) {
                        Coordinates tempCoords = new Coordinates(col, row);
                        possibleCoordinates.add(tempCoords);
                    }
                }
            }
        }

        index = randomGenerator.nextInt(possibleCoordinates.size());
        this.x = possibleCoordinates.get(index).getX();
        this.y = possibleCoordinates.get(index).getY();
    }


    /**
     * Method that receives two different coordinates and returns the distance between them.
     * @param coords_1 First coordinate received.
     * @param coords_2 Second coordinate received.
     * @return This method returns the difference between both received coordinates.
     */
    public static int distanceBetween(Coordinates coords_1, Coordinates coords_2) {
        int xDif;
        int yDif;

        xDif = Math.abs(coords_1.x - coords_2.x);
        yDif = Math.abs(coords_1.y - coords_2.y);

        if (xDif < yDif) {
            return yDif;
        } else {
            return xDif;
        }
    }

    /**
     * Method that checks if Coordinates are inside the Environment
     * @return This method returns -1 in case of error or 1 in case of success
     */
    public int inEnvironment(int x ,int y) {

        if (x  <  0 || y < 0) {
            return -1;
        }

        if(x >= Environment.getX() || y >= Environment.getY()) {
            return -1;
        }

        return 1;
    }
}
