/**
 * This is the character node class, designed to hold information about a character in the story
 *
 * Created by Sam on 10/04/2019.
 */
public class Node {

    private String name;
    private String type;

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
}
