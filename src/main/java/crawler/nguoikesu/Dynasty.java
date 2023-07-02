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

public class Dynasty extends NguoiKeSu{
    private JsonObject getPeriod(String url){
        JsonObject period = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#content > div.com-content-article.item-page > div.page-header > h1").text();
            period.addProperty("name", name);

            // Get description
            String description = document.select("#content > div.com-content-category-blog.blog > div.category-desc.clearfix > p").text();
            if (!description.equals("")) {
                period.addProperty("description", description);
            }

            // Article
            Element article = document.select("#content > div.com-content-article.item-page > div.com-content-article__body").first();
            assert article != null;
            article.select("sup, #toc").remove();

            if (article.select(":has(h3), :has(h2)").size() > 0){
                JsonObject emperor = new JsonObject();
                for (Element element : article.select(">h3, >h2")) {
                    // Get name
                    String emperorName = element.text();

                    // Get description
                    Element nextSibling = element.nextElementSibling();
                    StringBuilder emperorDescription = new StringBuilder();
                    while (nextSibling != null && !nextSibling.tagName().equals("h3")) {
                        if (nextSibling.tagName().equals("p")) {
                            emperorDescription.append(nextSibling.text());
                        }
                        nextSibling = nextSibling.nextElementSibling();
                    }

                    emperor.addProperty(emperorName, emperorDescription.toString());
                }
                period.add("emperor", emperor);
            }else {
                // Get description
                String periodDescription = article.select("p:not(.lead)").text();
                period.addProperty("description", periodDescription);
            }

            System.out.println("      "+name);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


        return period;
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
            String name = document.select("#content > div.com-content-category-blog.blog > h1").text();
            dynasty.addProperty("name", name);

            // Get description
            String description = document.select("#content > div.com-content-category-blog.blog > div.category-desc.clearfix > p").text();
            dynasty.addProperty("description", description);

            System.out.println(name);

            // Get period
            Elements listPeriod = document.select("#content > div.com-content-category-blog.blog > div.com-content-category-blog__items.blog-items.items-leading > div.com-content-category-blog__item.blog-item > div > div > h2 > a");
            JsonObject periods = new JsonObject();
            listPeriod.forEach(period -> {
                JsonObject periodObject = getPeriod(getBaseUrl() + period.attr("href"));
                periods.add(String.valueOf(periodObject.remove("name")), periodObject);
            });



            dynasty.add("periods", periods);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return dynasty;
    }
    @Override
    protected Vector<String> getUrl() {
        String url = getBaseUrl() + "/dong-lich-su";
        Vector<String> dynastiesUrl = new Vector<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            Element listBlock = document.select("#Mod88 > div > div > ul").first();

            assert listBlock != null;
            Elements listDynasties = listBlock.select("> li > a");

            listDynasties.forEach(dynasty ->
            {
                dynastiesUrl.add(dynasty.attr("href"));
                System.out.print("\r"+dynasty.attr("href"));
            });
            System.out.println();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return dynastiesUrl;
    }

    public static void main(String[] args) {
        new Dynasty();
    }
}
