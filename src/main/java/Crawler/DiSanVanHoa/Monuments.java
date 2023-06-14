package Crawler.DiSanVanHoa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.Vector;

public class Monuments extends DiSanVanHoa{
    private Vector<String> listMonuments(String url){
        Vector<String> list = new Vector<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(baseUrl + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = org.jsoup.Jsoup.parse(connection.getInputStream(), "UTF-8", baseUrl + url);

            document.select("#main-page > div.page-content > table > tbody > tr > td:nth-child(2) > p > a").forEach(element -> list.add(element.attr("href")));
            System.out.print("\rCrawling: " + url + " - " + list.size() + " monuments");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    private Vector<String> monuments(String url){
        Vector<String> list = new Vector<>();
        String urlConnect = baseUrl + url;
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = org.jsoup.Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get url
                document.select("#main-page > div.page-content > div.item > a").forEach(element -> list.add(element.attr("href")));

                System.out.print("\rCrawling: " + urlConnect + " - " + list.size() + " monuments");
                String nextPageUrl;
                // Get next page
                try {
                    nextPageUrl = Objects.requireNonNull(document.select("#main-page > div.page-content > div.row > div.col-md-6.text-right > nav > ul > li.active ~ li").first()).select("a").attr("href");
                }catch (NullPointerException e){
                    break;
                }

                urlConnect = baseUrl + "/" + nextPageUrl;
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }


        return list;
    }
    @Override
    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = org.jsoup.Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#main-page > div.page-content > h1").text();
            entity.addProperty("name", name);

            // Get info
            String info = document.select("#main-page > div.page-content > div.description").text();
            entity.addProperty("infoText", info);

            // Get description
            Element div = document.select("#main-page > div.page-content").first();
            if (div == null){
                return null;
            }
            div.select("div.description, div.author, h1").remove();
            StringBuilder description = new StringBuilder();
            div.select("p").forEach(element -> description.append(element.text()).append("\n"));
            entity.addProperty("description", description.toString());

        } catch (IOException | URISyntaxException e) {
            return null;
        }

        if (entity.get("name") == null && entity.get("moreInfo") == null && entity.get("description") == null)
            return null;

        System.out.print("\rCrawling " + entity.get("name").getAsString());

        return entity;
    }

    @Override
    protected Vector<String> getUrl() {
        Vector<String> list = new Vector<>();
        list.addAll(listMonuments("/danh-muc-di-tich-quoc-gia-dac-biet-1752"));
        list.addAll(listMonuments("/danh-muc-di-tich-quoc-gia-1753"));
        list.addAll(listMonuments("/di-san-van-hoa-va-thien-nhien-the-gioi-1754"));

        list.addAll(monuments("/di-tich-quoc-gia-130"));
        list.addAll(monuments("/di-tich-quoc-gia-dac-biet-2936"));
        System.out.print("\rCrawling: " + list.size() + " monuments");
        return list;
    }
}
