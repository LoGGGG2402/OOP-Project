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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Festival extends Wikipedia {
    protected JsonArray getEntities() {
        String url = "/wiki/Lễ_hội_Việt_Nam";
        JsonArray entity = new JsonArray();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(getBaseUrl()+url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", getBaseUrl()+url);
            Elements table=document.getElementsByClass("prettytable wikitable").select("tbody>tr");

            //get category
            Elements category=table.first().children();
            List<String> categoryList= new ArrayList<>();
            for(Element e:category) {
                categoryList.add(e.text());
            }
            //get entity
            table.remove(0);
            for(Element row:table) {
                JsonObject entity1 = new JsonObject();
                for (int i = 0; i < categoryList.size(); i++) {
                    entity1.addProperty(categoryList.get(i), row.children().get(i).text());
                }
                JsonObject entity2 = new JsonObject();
                entity2.addProperty("name",entity1.get("Lễ hội truyền thống").getAsString());
                entity1.remove("Lễ hội truyền thống");
                entity2.add("properties",entity1);

                entity.add(entity2);
            }



        } catch(IOException | URISyntaxException e){
            e.printStackTrace();
        }
        return entity;
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
        new Festival();
    }
}
