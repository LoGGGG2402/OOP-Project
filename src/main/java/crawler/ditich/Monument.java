package crawler.ditich;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

public class Monument extends Ditich{
    @Override
    protected Vector<String> getUrl() {
        Vector<String> figureUrl = new Vector<>();
        String urlConnect = getBaseUrl() + "/FrontEnd/DiTich";
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get figure url and name
                document.select("#div_ditich > section > a").forEach(element -> figureUrl.add(element.select("a").attr("href")));

                // Get next page url
                String nextPageUrl = document.select("#content > div.com-tags-tag.tag-category > div.com-tags-tag__pagination.w-100 > nav > ul > li:nth-child(6) > a").attr("href");
                System.out.print("\rCrawling " + figureUrl.size() + " urls");
                if (nextPageUrl.equals("")) {
                    break;
                }
                urlConnect = getBaseUrl() + nextPageUrl;


            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return figureUrl;
    }

    @Override
    protected JsonObject getEntity(String url) {
        return null;
    }
}
