/**
 * The story class that will manage all characters and produce an output
 * Created by Sam on 10/04/2019.
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import edu.mit.jwi.*;
import edu.mit.jwi.data.IHasCharset;
import edu.mit.jwi.data.IHasLifecycle;
import edu.mit.jwi.item.IExceptionEntry;
import edu.mit.jwi.item.IExceptionEntryID;
import edu.mit.jwi.item.IHasVersion;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IIndexWordID;
import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.IStemmer;

public class Story {
//    final private String theme;

    LinkedList<Character> characters = new LinkedList<Character>();
    Scanner reader = new Scanner(System.in);

    // construct the URL to the Wordnet dictionary directory
    String wnhome = System.getenv("WNHOME");
    //THIS NEEDS TO BE CHANGED TO YOUR INSTALLATION OF WORDNET
    String path = "C:\\Program Files (x86)\\WordNet\\2.1\\dict";

    URL url = null;
    IDictionary dict = null;


    public Story() {
        if (dict == null){
            initDictionary();
        }

        createCharacter();

        printCharacters();
    }

    /**
     * Creates a new character to be added to the story
     */
    private void createCharacter(){
        System.out.println("Give your character a name:");
        String name = reader.nextLine();

        Character newCharacter = new Character(name);

//        System.out.println("Describe them in one word, such as adventurous or intelligent:");
//        String descriptor = reader.nextLine();

        addCharacter(newCharacter);
    }

    private void addCharacter(Character character){
        characters.add(character);
    }

    private void printCharacters(){
        for (int x = 0; x < characters.size(); x ++){
            System.out.println("Character: " + characters.get(x));
            System.out.println("Character name " + characters.get(x).getName());
        }
    }

    /**
     * Generate a number of locations for characters
     */
    private void generateLocations(){
        Random rand = new Random();
        int numberLocations = rand.nextInt(3);
        numberLocations ++;

        for (int x = 0; x < numberLocations; x ++){
            createLocation();
        }
    }

    /**
     * creates a location node
     * @return
     */
    private Node createLocation(){

        return null;
    }

    /**
     * Initilises the dicitionary for the Story
     */
    private void initDictionary(){
        try {
            url = new URL("file", null, path);
        }
        catch(MalformedURLException e){
            System.out.println("Something wrong with url");
            e.printStackTrace();
        }
        System.out.println("URL: " + url);
        dict = new Dictionary(url);
        try {
            dict.open();
        }
        catch(IOException e){
            System.out.println("Cant open dict");
            e.printStackTrace();
        }

        IIndexWord idxWord = dict.getIndexWord("travel", POS.NOUN);
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord word = dict.getWord(wordID);
//        getSynonyms("Travel");

    }

    /**
     * Function gets words with similar meanings
     * @param searchWord The string you want the synonyms for
     */
    private ISynset getSynonyms(String searchWord){
        // look up first sense of the word "dog "

        LinkedList<String> synonyms = new LinkedList<String>();

        IIndexWord idxWord = dict.getIndexWord (searchWord, POS . NOUN );

        for (int x = 0; x < idxWord.getWordIDs().size(); x ++) {
            IWordID wordID = idxWord.getWordIDs().get(x); // 1st meaning
            IWord word = dict.getWord(wordID);
            ISynset synset = word.getSynset();

            // iterate over words associated with the synset
            for (IWord w : synset.getWords()) {
                String wLemma = w.getLemma();
                if (checkListContainsWord(synonyms, wLemma) == false){
                    synonyms.add(wLemma);
                }
            }
        }
         return null;
    }

    /**
     * Checks to see if a list contains a word already
     * @param list The list to search
     * @param search The word to search for
     * @return True if the word is found
     */
    private boolean checkListContainsWord(LinkedList<String> list, String search){

        for (int x = 0; x < list.size(); x ++){
            if (list.get(x).equals(search)){
                return true;
            }
        }

        return false;
    }
}
