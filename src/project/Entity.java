//João Craveiro 2013136429
//João Faria 2013136446 

package project;
/**
 * Class Entity is the main class of all the entities in the Environment.
 * It has all the common variables of the different types of entities and some common methods.
 * This is an abstract class.
 */
public abstract class Entity {
    private static int numberOfEntities;
    protected  int id;
    protected String colour;
    protected String shape;
    protected Coordinates coords;

    /**
     * Simple class constructor.
     */
    public Entity(String colour, String shape, int x, int y) {
        numberOfEntities++;
        this.id = numberOfEntities;
        this.colour = colour;
        this.shape = shape;
        this.coords = new Coordinates(x, y);
    }

    /**
     * Getter that returns the Coordinates of the entity.
     */
    public Coordinates getCoords () {

        return  coords;
    }

    /**
     * Sets the number of entities.
     */
    public static void setNumberOfEntities (int x) {
        numberOfEntities = x;
    }

    /**
     * Getter that returns the colour of the entity.
     */
    public String getColour() {

        return this.colour;
    }

    /**
     * Getter that returns the shape of the entity.
     */
    public String getShape() {

        return this.shape;
    }

    /**
     * Getter that returns the id of the entity;
     */
    public int getId() {
        return id;
    }


    /**
     * Method called by an Entity that checks if a given Entity occupies the same position as it;
     * @param toCheck is the entity that we are verifying.
     * @return This method returns -1 if the entities aren't in the same position and 1 if they are.
     */
    protected int checkSamePosition(Entity toCheck) {
        if (this.getCoords().getX() == toCheck.getCoords().getX() && this.getCoords().getY() == toCheck.getCoords().getY()) {
            return 1;
        }
        return -1;
    }

}
