/**
 * Created by Sam on 16/04/2019.
 */

import java.util.Arrays;
import java.util.Random;

public class Template {

    /**
     * Function gets a template
     *
     * @return
     */
    public static String getTemplate() {
        Random rand = new Random();
        return templates[rand.nextInt(templates.length)];
    }

    public static String getTravelTemplate() {
        Random rand = new Random();
        return travelTemplates[rand.nextInt(travelTemplates.length)];
    }

    public static String getInteractionTemplate(){
        Random rand = new Random();
        return characterInteractions[rand.nextInt(characterInteractions.length)];
    }

    public static String getLocationSpecifcTemplates(){
        Random rand = new Random();
        return locationSpecifcTemplates[rand.nextInt(locationSpecifcTemplates.length)];
    }

    static String[] templates = new String[]{
            "CHARACTER S-did C-ACTIVITY",
            "CHARACTER S-does C-ACTIVITY"
    };

    static String[] travelTemplates = new String[]{
            "CHARACTER S-went to LOCATION",
            "CHARACTER S-travelled to LOCATION"
    };

    static String[] characterInteractions = new String[]{
            "CHARACTER S-spoke to CHARACTER2",
            "CHARACTER played C-SPORT WITH CHARACTER2",
            "CHARACTER played C-GAME WITH CHARACTER2",
            "CHARACTER S-beat CHARACTER2",
            "CHARACTER S-upset CHARACTER2",
            "CHARACTER S-made CHARACTER2 C-emotion",
    };

    static String[] locationSpecifcTemplates = new String[]{
            "CHARACTER S-enjoyed LOCATION",
            "CHARACTER did not S-like LOCATION",
            "CHARACTER thinks LOCATION IS S-fun",
            "CHARACTER thinks LOCATION IS S-rubbish",
            "CHARACTER thinks LOCATION IS S-exciting",
            "CHARACTER will go back to LOCATION",
            "CHARACTER will not go back to LOCATION"
    };
}
