/**
 * The story class that will manage all characters and produce an output
 * Created by Sam on 10/04/2019.
 */

import java.util.*;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import edu.mit.jwi.*;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.data.IHasCharset;
import edu.mit.jwi.data.IHasLifecycle;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.IStemmer;

public class Story {
//    final private String theme;

    LinkedList<Character> characters = new LinkedList<Character>();
    LinkedList<String> names = new LinkedList<String>();
    LinkedList<Node> locations = new LinkedList<Node>();
    String story = "";


    Scanner reader = new Scanner(System.in);

    // construct the URL to the Wordnet dictionary directory
    String wnhome = System.getenv("WNHOME");
    //THIS NEEDS TO BE CHANGED TO YOUR INSTALLATION OF WORDNET
    String path = "C:\\Program Files (x86)\\WordNet\\2.1\\dict";
    private Random rand = new Random();

    URL url = null;
    IDictionary dict = null;

    Boolean characterInteraction = false;

    public Story() {
        if (dict == null){
            initDictionary();
        }

        createCharacter();
        createCharacter();
        System.out.println("Generating your story...");
        generateNameList();
        generateLocations();

        for (int x = 0; x < 10; x ++){
            runStory();
        }
        System.out.println("\n" + story);

//        for (Character character : characters){
//            System.out.println("Character: " + character.getName() + " location " + character.getCurrentLocation());
//        }
    }

    /**
     * Gets a template and runs it as a story
     */
    private void runStory(){
        Character character = characters.get(getRandomCharacter());

//        Random rand = new Random();
        int x = rand.nextInt();

        String template;
        LinkedList<Character> templateCharacters = new LinkedList<Character>();
        LinkedList<Node> templateLocations = new LinkedList<Node>();

        Node characterLocation = character.getCurrentLocation();

        templateCharacters.add(character);
        if (characterLocation != null){
            //Checking if other characters are in this location
            x = 0;
            for (Character person : characters){
                if(person.getCurrentLocation() != null) {
                    if (characterLocation.getName().equals(person.getCurrentLocation().getName()) && person != character) {
                        templateCharacters.add(person);
                    } 
                }
                x ++;
            }
        }

        if (templateCharacters.size() > 1 && (rand.nextInt(2) == 3 || characterInteraction == false)){
            // Two characters are in the same place, can have them interact
            template = Template.getInteractionTemplate();
            templateLocations.add(character.getCurrentLocation());
            characterInteraction = true;
        }
        else if (characterLocation != null && rand.nextInt(5) == 1){
            template = Template.getLocationSpecifcTemplates();
            characterInteraction = false;
        }
        else {
            // Run a normal story template
            templateLocations.add(getRandomLocation());
            if (templateLocations.get(0) == character.getCurrentLocation()){
                template = Template.getTemplate();
            }
            else{
                template = Template.getTravelTemplate();
            }


            characterInteraction = false;
        }

        if (templateLocations.isEmpty()){
            templateLocations.add(getRandomLocation());
        }

        fillTemplate(template, templateCharacters, templateLocations);
    }

    /**
     * Populates the template with characters and words
     * @param template
     */
    private void fillTemplate(String template, LinkedList<Character> templateCharacters, LinkedList<Node> templateLocations){
        Character character1 = templateCharacters.get(0);
        Character character2 = null;
        if (templateCharacters.size() > 1){
            character2 = templateCharacters.get(1);
        }

        Node location1 = templateLocations.get(0);
        Node location2 = null;
        if (templateLocations.size() > 1){
            location2 = templateLocations.get(1);
        }

        String storyString = "";

        String[] templateParts = template.split(" ");

//        Random rand = new Random();

        for (String part : templateParts){
            if (part.equals("CHARACTER")){
                storyString += character1.getName();
            }
            else if (part.equals("CHARACTER2")){
                storyString += character2.getName();
            }
            else if (part.equals("LOCATION")){
                storyString += location1.getName();
                character1.setCurrentLocation(location1);
            }
            else if (part.equals("LOCATION2")){
                storyString += location2.getName();
                character2.setCurrentLocation(location2);
            }
            else if (part.contains("C-")){
                String changeWord = part.replace("C-", "");
                LinkedList<String> hyponyms = getHyponyms(changeWord);
                String word = getRandomStringFromList(hyponyms);
                if(word == null) word = changeWord;
                storyString += word;
            }
            else if (part.contains("S-")){
                String changeWord = part.replace("S-", "");
                LinkedList<String> synonyms = getSynonyms(changeWord);
                String word = getRandomStringFromList(synonyms);
                if(word == null) word = changeWord;
                storyString += word;
            }
            else {
                storyString += part;
            }

            storyString += " ";
        }
//        System.out.println("Template: " + template);
//        System.out.println("Story String: " + storyString);
        story += storyString + "\n";

    }

    /**
     * Creates a new character to be added to the story
     */
    private void createCharacter(){
        System.out.println("Give your character a name:");
        String name = reader.nextLine();

        Character newCharacter = new Character(name, "person");

        addCharacter(newCharacter);
    }

    /**
     * Adds a character to the list
     * @param character The character to add
     */
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
     * Returns a random character from the pool
     * @return a character node
     */
    private int getRandomCharacter(){
//        Random rand = new Random();
//        return characters.get(rand.nextInt(characters.size()));
        return rand.nextInt(characters.size());
    }

    /**
     * Returns a random location from the pool
     * @return a location node
     */
    private Node getRandomLocation(){
//        Random rand = new Random();
        return locations.get(rand.nextInt(locations.size()));
    }

    /**
     * Functions uses webscraper to generate a list of names that can be used for characters or locations
     * Stores them as a list of strings
     */
    private void generateNameList(){
        List<HtmlElement> items = WebScraper.scrapeName();

        if (items == null){
            System.out.println("Something has gone wrong with the webscraper collecting names exiting");
            System.exit(0);
        }

        for(HtmlElement item : items){
            String temp = item.asText();
            String[] split = temp.split(" ");

            for (String word : split){
                names.add(word);
            }
        }
    }

    private String getRandomName(){
        if (names.size() == 0){
            generateNameList();
        }

        return getRandomStringFromList(names);
//        Random rand = new Random();
//        int pos = rand.nextInt(names.size());
//
//        String returnString = names.remove(pos);
//        return returnString;
    }

    private String getRandomStringFromList(LinkedList<String> list){
        if (list.size() == 0){
            return null;
        }

//        Random rand = new Random();
        int pos = rand.nextInt(list.size());

        String returnString = list.remove(pos);
        return returnString;
    }

    private void printNames(){
        int x = 0;
        for (String name : names){
            x ++;
        }
    }

    /**
     * Generate a number of locations for characters
     */
    private void generateLocations(){
//        Random rand = new Random();
        int numberLocations = rand.nextInt(3);
        numberLocations ++;

        for (int x = 0; x < numberLocations; x ++){
            Node location = createLocation();
            locations.add(location);
        }
    }

    /**
     * creates a location node
     * @return
     */
    private Node createLocation(){
        String name = getRandomName();
        String locationType = Node.getRandomLocationType();

        LinkedList<String> synonyms = getSynonyms(locationType);

//        Random rand = new Random();
        name = name + " " + synonyms.get(rand.nextInt(synonyms.size()));
        Node locationNode = new Node(name, locationType);
        return locationNode;
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
    private LinkedList<String> getSynonyms(String searchWord){
        // look up first sense of the word "dog "
        LinkedList<String> synonyms = new LinkedList<String>();

        IIndexWord idxWord = dict.getIndexWord(searchWord, POS . NOUN );


        int wordIdsSize;
        try{
            wordIdsSize = idxWord.getWordIDs().size();
        }
        catch (Exception e){
            System.out.println("Exception " + e);
            synonyms.add(searchWord);
            return synonyms;

        }


        for (int x = 0; x < wordIdsSize; x ++) {
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
         return synonyms;
    }

    /**
     * A method of getting a type of something
     * give it sport and you'll get "football, running, rugby..."
     * @param searchWord The word to look for examples of
     */
    private LinkedList<String> getHyponyms(String searchWord){
        // get the synset of 'input_word'
        IIndexWord idxWord = dict.getIndexWord (searchWord, POS.NOUN ) ;
        IWordID wordID = idxWord.getWordIDs().get(0) ; // 1st meaning
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();

        // get the hypernyms
        List <ISynsetID> hypernyms = synset.getRelatedSynsets(Pointer.HYPONYM);

        // print out each h y p e r n y m s id and synonyms
        List<IWord> words ;
        LinkedList<String> returnList =  new LinkedList<String>();

        for(ISynsetID sid : hypernyms) {
            words = dict . getSynset ( sid ) . getWords () ;
//            System . out . print ( sid + " {") ;
            for(Iterator<IWord> i = words.iterator(); i . hasNext () ;) {
                String newWord = i.next().getLemma();
                if (checkListContainsWord(returnList, newWord) == false){
                    returnList .add(newWord);
                }
            }
        }

        return returnList;
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

    /**
     * Compares two nodes to see if they are the same
     * @param x first node for comparison
     * @param y second node for comparison
     * @return True if they are the same
     */
    private boolean compareNodes(Node x, Node y){
        if (x.getName().equals(y.getName()) && x.getType().equals(y.getType())){
            return true;
        }

        return false;
    }
}
