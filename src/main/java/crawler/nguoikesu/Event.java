package crawler.nguoikesu;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class Event extends NguoiKeSu{

    @Override
    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#content > div.com-content-article.item-page > div.page-header > h1").text();
            entity.addProperty("name", name);

            // Article body
            Element articleBody = document.select("#content > div.com-content-article.item-page > div.com-content-article__body").first();
            assert articleBody != null;
            articleBody.select("sup").remove();
            Elements headings = articleBody.select("h2, h3");
            if (!headings.isEmpty()) {
                Element firstHeading = headings.first();
                assert firstHeading != null;
                Element nextElement = firstHeading.nextElementSibling();
                while (nextElement != null) {
                    Element currentElement = nextElement;
                    nextElement = currentElement.nextElementSibling();
                    currentElement.remove();
                }
            }

            // Get info
            JsonObject properties = new JsonObject();

            for (Element element : articleBody.select("tr:not(:has(tr))")) {
                String key = element.select(">th").text();
                String value = element.select(">td").text();
                if (key.isEmpty()){
                    key = element.select(">td:nth-child(1)").text();
                    value = element.select(">td:nth-child(2)").text();
                }
                properties.addProperty(key, value);
            }

            entity.add("properties", properties);

            // Get description
            StringBuilder description = new StringBuilder();
            articleBody.select("p").forEach(element -> description.append(element.text()).append("\n"));
            entity.addProperty("description", description.toString());

            // Get image
            String image = BASE_URL + document.select("img:nth-child(1)").attr("data-src");
            entity.addProperty("image", image);


        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }


        return entity;
    }
    @Override
    protected Vector<String> getUrl() {
        Vector<String> urls = new Vector<>();
        String urlConnect = "https://nguoikesu.com/tu-lieu/quan-su";
        while (true){
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__items.blog-items.items-leading > div > div > div > h2 > a").forEach(element -> urls.add(element.select("a").attr("href")));

                String nextPage = document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__navigation.w-100 > div > nav > ul > li:nth-child(13) > a").attr("href");

                if (nextPage.equals("")){
                    break;
                }
                urlConnect = BASE_URL + nextPage;
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return urls;
    }

    public static void main(String[] args) {
        new Event();
    }
}
