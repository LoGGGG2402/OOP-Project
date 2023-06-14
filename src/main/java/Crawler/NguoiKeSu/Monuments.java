package Crawler.NguoiKeSu;

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

public class Monuments extends NguoiKeSu{
    @Override
    protected JsonObject getEntity(String url){
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#content > div.com-content-article.item-page.page-list-items > div:nth-child(3) > h2").text();
            entity.addProperty("name", name);

            // Article body
            Element articleBody = document.select("#content > div.com-content-article.item-page > div.com-content-article__body").first();
            assert articleBody != null;
            articleBody.select("sup").remove();

            // Get info
            Element infoBox = articleBody.select("div.infobox > table > tbody").first();
            JsonObject properties = new JsonObject();
            if (infoBox != null) {
                Elements info = infoBox.select(">tr:has(th)");
                for (Element element : info) {
                    String key = element.select(">th").text();
                    String value = element.select(">td").text();
                    properties.addProperty(key, value);
                }
            }
            entity.add("properties", properties);

            // Get description
            articleBody.select("sup").remove();
            StringBuilder description = new StringBuilder();
            articleBody.select("p:not(.lead), h3 ").forEach(element -> description.append(element.text()).append("\n"));
            entity.addProperty("description", description.toString());

            // Get image
            String image = baseUrl + document.select("#content > div.com-content-article.item-page.page-list-items > div.com-content-article__body > div.infobox > table > tbody > tr > td > img").attr("data-src");
            entity.addProperty("image", image);

            System.out.print("\rCrawling " + name + " done");

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
    @Override
    protected Vector<String> getUrl() {
        Vector<String> figureUrl = new Vector<>();
        String urlConnect = baseUrl + "/di-tich-lich-su";
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get figure url and name
                document.select("#content > div.com-tags-tag.tag-category > div.com-tags__items > ul > li > h3 > a").forEach(element -> figureUrl.add(element.select("a").attr("href")));

                // Get next page url
                String nextPageUrl = document.select("#content > div.com-tags-tag.tag-category > div.com-tags-tag__pagination.w-100 > nav > ul > li:nth-child(6) > a").attr("href");
                System.out.print("\rCrawling " + figureUrl.size() + " urls");
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
}
