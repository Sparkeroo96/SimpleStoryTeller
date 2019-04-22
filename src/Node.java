/**
 * This is the  node class, designed to hold information about an object in the story
 *
 * Created by Sam on 10/04/2019.
 */

import java.util.Random;

public class Node {

    private String name;
    private String type;

    static String[] locationType = {
            "street",
            "park",
            "house",
            "town"
    };

    public Node(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return node name
     */
    public String getName(){
        return name;
    }

    /**
     * returns Node type
     * @return
     */
    public String getType(){
        return type;
    }

    public static String getRandomLocationType(){
        Random rand = new Random();

        return locationType[rand.nextInt(locationType.length)];
    }
}
