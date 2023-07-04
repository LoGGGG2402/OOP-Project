package crawler.wikipedia;

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

public class Event extends Wikipedia {
    protected JsonArray getEntities() {
        String url = "/Niên_biểu_lịch_sử_Việt_Nam";
        JsonArray entities = new JsonArray();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(getBaseUrl()+"/wiki" + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", getBaseUrl()+"/wiki"  + url);
            Elements rows=document.select("dl,p");
            for(int i=6;i<rows.size()-1;i++) {
                if (rows.get(i).tag().toString().equals("p")) {
                    if (!rows.get(i+1).tag().toString().equals("dl")) {
                        JsonObject entity = new JsonObject();
                        JsonObject properties= new JsonObject();
                        properties.addProperty("Thời gian", rows.get(i).select("b").text());
                        entity.addProperty("name", rows.get(i).text().replace(rows.get(i).select("b").text(), ""));
                        entity.add("properties",properties);

                            entity.addProperty("description", getDescription(rows.get(i).text().replace(rows.get(i).select("b").text(), "")));
                        entities.add(entity);
                    }
                    else {
                        String year = rows.get(i).text();
                        Element nextRow = rows.get(i+1);
                        Elements internalRows = nextRow.select("dd");
                        for (Element row1 : internalRows) {
                            JsonObject entity = new JsonObject();
                            JsonObject properties= new JsonObject();
                            properties.addProperty("Thời gian", row1.select("b").text() + " năm " + year);
                            entity.addProperty("name", row1.text().replace(row1.select("b").text(), ""));
                            entity.add("properties",properties);
                            entity.addProperty("description",getDescription( row1.text().replace(row1.select("b").text(), "")));
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
    public String getDescription(String name){
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(getBaseUrl()+"/wiki/" + name.replace(" ","_")).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", getBaseUrl()+"/wiki/"  + name.replace(" ","_"));
            return document.select("#mw-content-text > div.mw-parser-output > p").first().text();
        } catch(IOException | URISyntaxException e){
            System.out.println("Can't find :"+e.getMessage());
        }
        return "";
    }

    @Override
    protected Vector<String> getUrl() {
        return null;
    }

    @Override
    protected JsonObject getEntity(String url) {
        return null;
    }

    public static void main(String[] args){
        new Event();
    }
}
