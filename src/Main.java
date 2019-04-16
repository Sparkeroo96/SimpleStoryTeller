/**
 * The main Class to run the Project from
 */

import java.util.Scanner;

public class Main {

    private static Story story;

    public static void main(String[] args) {
	    System.out.println("Hello World, story teller");
        createStory();
    }

    public static void createStory(){
        System.out.println("Creating a new story...");
        story = new Story();
    }

}
