/**
 * Created by Sam on 16/04/2019.
 */

import java.util.LinkedList;

public class Character extends Node {

    Node currentLocation = null;
    LinkedList<Node> previousLocations = new LinkedList<Node>();

    public Character(String name, String type) {
        super(name, type);
    }

    public void setCurrentLocation(Node location){
        currentLocation = location;
    }
}
