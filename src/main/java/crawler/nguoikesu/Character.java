package crawler.nguoikesu;

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

public class Character extends NguoiKeSu{
    @Override
    protected Vector<String> getUrl() {
        Vector<String> figureUrl = new Vector<>();
        String urlConnect = getBaseUrl() + "/nhan-vat";
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get figure url and name
                document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__items.blog-items > div > div > div > h2").forEach(element -> figureUrl.add(element.select("a").attr("href")));

                // Get next page url
                String nextPageUrl = document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__navigation.w-100 > div > nav > ul > li:nth-child(13) > a").attr("href");
                System.out.print("\rCrawling " + figureUrl.size() + " urls");
                if (nextPageUrl.equals("")) {
                    System.out.println("\rCrawling url done");
                    break;
                }
                urlConnect = getBaseUrl() + nextPageUrl;


            } catch (IOException | URISyntaxException e) {
                System.out.println("Error: " + urlConnect);
            }
        }
        return figureUrl;
    }
    @Override
    protected JsonObject getEntity(String url){
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#content > div.com-content-article.item-page.page-list-items > div:nth-child(2) > h2").text();
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
                String value = "";
                Element valueElement = element.select(">td").first();
                if (valueElement != null) {
                    String replacedText = valueElement.html().replace("<br>", "; ");
                    value = Jsoup.parse(replacedText).text();
                }
                if (key.isEmpty()){
                    key = element.select(">td:nth-child(1)").text();
                    valueElement = element.select(">td:nth-child(2)").first();
                    if (valueElement != null) {
                        String replacedText = valueElement.html().replace("<br>", "; ");
                        value = Jsoup.parse(replacedText).text();
                    }
                }
                if (key.isEmpty() || key.equals(".")){
                    if(value.isEmpty() || value.equals(".")){
                        continue;
                    }
                    properties.addProperty(value, "");
                }
                properties.addProperty(key, value);
            }

            entity.add("properties", properties);

            // Get description
            StringBuilder description = new StringBuilder();
            articleBody.select("p").forEach(element -> description.append(element.text()).append("\n"));
            entity.addProperty("description", description.toString());

            // Get image
            if (articleBody.select("img").first() != null){
                String image = articleBody.select("img").first().attr("data-src");
                entity.addProperty("image", getImg(image.contains("http") ? image : (getBaseUrl() + image)));
            }

            // Get all document
            String allDocument = document.select("#content > div.com-content-article.item-page.page-list-items > div.com-content-article__body").text();
            entity.addProperty("allDocument", allDocument);

            entity.addProperty("source", url);

            System.out.print("\rCrawling " + url + " done has name " + name);

            //
            if (entity.get("name").getAsString().contains("nhà ") || entity.get("name").getAsString().contains("Nhà ")){
                return null;
            }
        } catch (IOException | URISyntaxException | NullPointerException e) {
            
            return null;
        }
        return entity;
    }

    public static void main(String[] args) {
        new Character();
    }
}
