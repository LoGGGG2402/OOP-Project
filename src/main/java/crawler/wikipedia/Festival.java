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

public class Festival extends Wikipedia {
    protected JsonArray getEntities(String url) {
        String baseUrl = "https://vi.wikipedia.org/wiki/";
        JsonArray entity = new JsonArray();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(baseUrl + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", baseUrl + url);
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
                entity1.addProperty("name", entity1.get("Lễ hội truyền thống").getAsString());
                entity1.remove("Lễ hội truyền thống");
                entity.add(entity1);
            }



        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }
        return entity;
    }
    @Override
    public void crawl() {
        JsonArray entities = new JsonArray();
        entities=getEntities("Lễ_hội_Việt_Nam");
        // Make directory
        File directory = new File("data/" + this.getClass().getSimpleName());
        if (!directory.exists()) {
            if (directory.mkdirs())
                System.out.println("Directory is created!");
            else
                System.out.println("Failed to create directory!");
        }


        // Write to file
        try {
            File file = new File("data/" + this.getClass().getSimpleName() + "/" + getTitle() + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(entities.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Festival();
    }
}
