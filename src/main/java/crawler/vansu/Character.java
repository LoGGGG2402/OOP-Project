package crawler.vansu;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Vector;

public class Character extends VanSu{
    @Override
    protected Vector<String> getUrl() {
        Vector<String> characterUrl = new Vector<>();
        String urlConnect = baseUrl + "/viet-nam/viet-nam-nhan-vat";
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get character url
                document.select("body > div.ui.container > table > tbody > tr > td:nth-child(1) > a:nth-child(1)").forEach(element -> characterUrl.add(element.select("a").attr("href")));

                // Get next page url
                String nextPageUrl = document.select("body > div.ui.container > table > tfoot > tr > th > div > a:has(i.right.chevron.icon)").attr("href");
                System.out.print("\rCrawling " + characterUrl.size() + " urls");
                if (nextPageUrl.equals("")) {
                    break;
                }
                urlConnect = nextPageUrl;
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return characterUrl;
    }
    @Override
    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("body > div.ui.container > div > div.active.section").text();
            entity.addProperty("name", name);

            // Get table
            Element tBody = document.select("body > div.ui.container > table > tbody").first();

            // Get information
            assert tBody != null;
            Elements information = tBody.select("tr:not(:last-child)");

            JsonObject properties = new JsonObject();
            for (Element element : information) {
                String key = Objects.requireNonNull(element.select(">td").first()).text();
                String value = Objects.requireNonNull(element.select(">td").last()).text();
                properties.addProperty(key, value);
            }
            entity.add("properties", properties);



            // Get description
            String description = Objects.requireNonNull(tBody.select("> tr > td").last()).text();
            entity.addProperty("description", description);





        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public static void main(String[] args) {
        new Character();
    }
}
