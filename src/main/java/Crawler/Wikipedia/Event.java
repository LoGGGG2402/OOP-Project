package Crawler.Wikipedia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.ArrayList;
import java.util.List;

public class Event  {
    protected static JsonArray getEntity(String url) {
        String baseUrl = "https://vi.wikipedia.org/wiki";
        JsonArray entities = new JsonArray();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(baseUrl + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", baseUrl + url);
            Elements rows=document.select("dl,p");

           for(int i=6;i<rows.size()-1;i++) {
                if (rows.get(i).tag().toString().equals("p")) {
                    if (!rows.get(i+1).tag().toString().equals("dl")) {
                        JsonObject entity = new JsonObject();
                        entity.addProperty("Thời gian", rows.get(i).select("b").text());
                        entity.addProperty("Sự Kiện", rows.get(i).text().replace(rows.get(i).select("b").text(), ""));
                        entities.add(entity);
                    }
                    else {
                        String year = rows.get(i).text();
                        Element nextRow = rows.get(i+1);
                        Elements internalRows = nextRow.select("dd");
                        for (Element row1 : internalRows) {
                            JsonObject entity = new JsonObject();
                            entity.addProperty("Thời gian", row1.select("b").text() + " năm " + year);
                            entity.addProperty("Sự Kiện", row1.text().replace(row1.select("b").text(), ""));
                            entities.add(entity);
                        }
                    }
                }
            }
        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }
        return entities;
    }
}
