package crawler.vansu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

public class Dynasty extends VanSu{
    @Override
    protected Vector<String> getUrl() {
        String urlConnect = baseUrl + "/viet-nam/nien-bieu-lich-su";
        Vector<String> urls = new Vector<>();
        try {
            System.out.println("Connecting to " + urlConnect);
            HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

            // Get url
            document.select("body > div.ui.container > div > b > a:nth-child(1)").forEach(element -> urls.add(element.attr("href")));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return urls;
    }

    @Override
    protected JsonObject getEntity(String url) {
        JsonObject dynasty = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("body > div.ui.container > h3:nth-child(2)").text();
            dynasty.addProperty("name", name);

            // Get description
            Elements listDescription = document.select("#vs_modal_number_one > div.content > p > b");
            StringBuilder description = new StringBuilder();
            listDescription.forEach(element -> description.append(element.text()).append("\n"));
            dynasty.addProperty("description", description.toString());

            // Get period
            JsonArray periods = new JsonArray();
            for (Element element : document.select("body > div.ui.container > h4")) {
                JsonObject period = new JsonObject();
                String periodName = element.text();
                period.addProperty("name", periodName);

                // Get description
                Element nextSibling = element.nextElementSibling();
                StringBuilder periodDescription = new StringBuilder();
                while (nextSibling != null && !nextSibling.tagName().equals("h4")) {
                    if (nextSibling.tagName().equals("p")) {
                        periodDescription.append(nextSibling.text());
                    }
                    nextSibling = nextSibling.nextElementSibling();
                }
                period.addProperty("description", periodDescription.toString());

                periods.add(period);
            }
            dynasty.add("period", periods);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        return dynasty;
    }

    public static void main(String[] args) {
        new Dynasty();
    }
}
