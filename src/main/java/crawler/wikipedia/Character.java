package crawler.wikipedia;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;
public class Character extends Wikipedia {

    protected Vector<String> getUrl() {
        return null;
    }

    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
            entity.addProperty("description",document.getElementsByClass("mw-parser-output").select("p").first().text());

            Elements table = document.getElementsByClass("infobox").select("[style=width:22em]>tbody>tr");
            JsonObject properties = new JsonObject();
            for(Element e:table) {
                String key = e.select("th").text();
                Element valueElement = e.select(">td").first();
                if (valueElement != null) {
                    String replacedText = valueElement.html().replace("<br>", "; ");
                    String value = Jsoup.parse(replacedText).text();
                    if(!key.equals("") && !key.equals("Thông tin chung")){
                        properties.addProperty(key,value);}
                }
            }
            entity.add("properties",properties);
        } catch(IOException | URISyntaxException | NullPointerException e){
            System.out.println(url);
            return entity;
        }
        return entity;
    }


    protected JsonArray getEntities(){
        JsonArray entities = new JsonArray();
        String urlConnect = getBaseUrl() + "/wiki/Vua_Việt_Nam";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);
            // Get character url

            Elements tables = document.select("table");
            for(Element table: tables) {
                Elements rows = table.select("tbody > tr[style *= height:50px;]");
                for (Element row : rows) {
                    JsonObject entity = new JsonObject();
                    entity.addProperty("name",row.select("td").get(1).text().replaceAll("\\[.*?\\]", ""));
                    JsonObject properties = new JsonObject();
                    properties.addProperty("Miếu hiệu",row.select("td").get(2).text().replaceAll("\\[.*?\\]", ""));
                    properties.addProperty("Thụy hiệu",row.select("td").get(3).text().replaceAll("\\[.*?\\]", ""));
                    properties.addProperty("Niên hiệu",row.select("td").get(4).text().replaceAll("\\[.*?\\]", ""));
                    properties.addProperty("Tên Húy",row.select("td").get(5).text().replaceAll("\\[.*?\\]", ""));
                    properties.addProperty("Thế thứ",row.select("td").get(6).text().replaceAll("\\[.*?\\]", ""));
                    properties.addProperty("Trị vì",row.select("td").get(7).text().replaceAll("\\[.*?\\]", ""));
                    JsonObject more = getEntity(getBaseUrl()+row.select("td").get(1).select("a").get(0).attr("href"));

                    if (more.has("description")) {
                        entity.addProperty("description", more.get("description").getAsString());
                    }
                    if (more.has("properties")) {
                        JsonObject moreProperties = more.get("properties").getAsJsonObject();
                        for (String key : moreProperties.keySet()) {
                            properties.addProperty(key, moreProperties.get(key).getAsString());
                        }
                    }

                    entity.add("properties",properties);
                    entities.add(entity);

                }
            }
        } catch (IOException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }


        return entities;
    }

    public static void main(String[] args) {
        new Character();
    }
}
