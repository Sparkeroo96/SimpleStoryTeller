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

    public static String getInteractionTemplate(){
        Random rand = new Random();
        return characterInteractions[rand.nextInt(characterInteractions.length)];
    }

    static String[] templates = new String[]{
            "CHARACTER TRAVEL LOCATION",
            "CHARACTER DO C-ACTIVITY",
            "LOCATION IS C-ADJECTIVE"
    };

    static String[] characterInteractions = new String[]{
            "CHARACTER SPOKE TO CHARACTER2",
            "CHARACTER PLAYED C-SPORT WITH CHARACTER2",
            "CHARACTER PLAYED C-GAME WITH CHARACTER2",
            "CHARACTER BEAT CHARACTER2",
            "CHARACTER UPSET CHARACTER2",
            "CHARACTER MADE CHARACTER2 C-EMOTION",
    };
}
