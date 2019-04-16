import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by Sam on 16/04/2019.
 */
public class WebScraper {

    String quickNameUrl = "https://www.name-generator.org.uk/quick/";

    public void scrapeName(){
        HtmlPage page = getHtmlPage(quickNameUrl);

        List<HtmlElement> items = page.getByXPath("//div[@class='name_heading']") ;

    }

    public HtmlPage getHtmlPage(String url){
        WebClient client = new WebClient();

        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page = null;

        try {
            page = client.getPage(url);
        }catch(Exception e){
            e.printStackTrace();
        }

        return page;
    }
}
