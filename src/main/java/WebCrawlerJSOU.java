import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebCrawlerJSOU {

    private final String startingNode;

    public WebCrawlerJSOU(String startingNode) {
        this.startingNode = startingNode;
    }

    public void startCrawling() throws IOException {

        Document doc = Jsoup.connect("https://cinemagia.ro").get();
        Elements elements = doc.select("a");
        for (Element element:elements){
            System.out.println(element.attr("abs:href"));
        }

    }
}
