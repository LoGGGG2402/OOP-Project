package Website;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

public class HistoryTeller {
    private static final String baseUrl = "https://nguoikesu.com/";
    private static final String title = "Người Kể Sử";
    private final Vector<String> figure = new Vector<>();
    private final Vector<String> festival = new Vector<>();
    private final Vector<String> locations = new Vector<>();
    private final Vector<String> events = new Vector<>();
    private final Vector<String> dynasties = new Vector<>();
    private Vector<String> getUrl(String url, Vector<String> list) {
        Vector<String> figureUrl = new Vector<>();
        String urlConnect = url;
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get figure url and name
                document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__items.blog-items > div > div > div > h2").forEach(element -> {
                    figureUrl.add(element.select("a").attr("href"));
                    list.add(element.select("a").text());
                });

                // Get next page url
                String nextPageUrl = document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__navigation.w-100 > div > nav > ul > li:nth-child(13) > a").attr("href");

                if (nextPageUrl.equals("")) {
                    break;
                }
                urlConnect = baseUrl + nextPageUrl;

            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return figureUrl;
    }
    private JsonObject getEntity(String url){
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#content > div.com-content-article.item-page > div.page-header > h2").text();
            if (name.equals("")) {
                name = document.select("#content > div.com-content-article.item-page > div.page-header > h1").text();
            }
            entity.addProperty("name", name);

            // Article Body
            Element articleBody = document.select("#content > div.com-content-article.item-page > div.com-content-article__body").first();

            // Get properties
            JsonObject properties = new JsonObject();
            assert articleBody != null;
            Element info = articleBody.select("div.infobox > table > tbody").first();
            if (info != null)
                info.select("tr:has(th):has(td)").forEach(element -> {
                String key = element.select("th").text();
                // Change <br> to , in value
                Element valueElement = element.select("td").first();
                assert valueElement != null;
                Elements brTags = element.select("br");
                for (Element brTag : brTags) {
                    TextNode comma = new TextNode(";\n ");
                    brTag.replaceWith(comma);
                }
                String value = valueElement.text();
                properties.addProperty(key, value);
            });
            entity.add("properties", properties);


            // Get description
            // Remove all <sup> tag
            articleBody.select("sup").remove();
            StringBuilder description = new StringBuilder();
            articleBody.select("p:not(.lead), h3 ").forEach(element -> description.append(element.text()).append("\n"));
            entity.addProperty("description", description.toString());


        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public static void main(String[] args) {
        HistoryTeller historyTeller = new HistoryTeller();
        System.out.println(historyTeller.getEntity("https://nguoikesu.com//dong-lich-su/hong-bang-va-van-lang/ky-hong-bang-thi"));
        System.out.println(historyTeller.getEntity("https://nguoikesu.com//nhan-vat/ho-chi-minh"));
    }

}
