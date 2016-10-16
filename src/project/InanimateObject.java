//João Craveiro 2013136429
//João Faria 2013136446 
package project;
/**
 * Child class of Entity objects of this class are the objects that populate the Environment of the simulation.
 */
public class InanimateObject extends  Entity {
    /**
     * Type of the inanimate object (chair, table, trash can).
     */
    private String type;

    /**
     * Class constructor that calls parent constructor and initializes class specific variables.
     */
    public InanimateObject(String colour, String shape, int x, int y, String type) {
        super(colour, shape, x, y);
        this.type = type;
    }

    /**
     * Getter that returns the type of the inanimate object.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Static method that receives two objects and returns the hamming distance between them.
     *
     * @param object1 first object
     * @param object2 second object
     * @return The possible return values are ints in the range of [0, 3] that represent the difference between two objects
     */
    public static int getHammingDistance(InanimateObject object1, InanimateObject object2) {
        int difference = 0;

        if (!object1.getType().equals(object2.getType())) {
            difference += 1;
        }

        if (!object1.getShape().equals(object2.getShape())) {
            difference += 1;
        }

        if (!object1.getColour().equals(object2.getColour())) {
            difference += 1;
        }
        return difference;
    }

}
