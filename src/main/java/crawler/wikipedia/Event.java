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

public class Event extends Wikipedia {
    protected JsonArray getEntities(String url) {
        JsonArray entities = new JsonArray();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(BASE_URL+"/wiki" + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", BASE_URL+"/wiki"  + url);
            Elements rows=document.select("dl,p");
           for(int i=6;i<rows.size()-1;i++) {
                if (rows.get(i).tag().toString().equals("p")) {
                    if (!rows.get(i+1).tag().toString().equals("dl")) {
                        JsonObject entity = new JsonObject();
                        JsonObject properties= new JsonObject();
                        properties.addProperty("Thời gian", rows.get(i).select("b").text());
                        entity.addProperty("Name", rows.get(i).text().replace(rows.get(i).select("b").text(), ""));
                        entity.add("Properties",properties);

                            entity.addProperty("Description", getDescription(rows.get(i).text().replace(rows.get(i).select("b").text(), "")));
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
                            entity.addProperty("Name", row1.text().replace(row1.select("b").text(), ""));
                            entity.add("Properties",properties);
                            entity.addProperty("Description",getDescription( row1.text().replace(row1.select("b").text(), "")));
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
            HttpURLConnection connection = (HttpURLConnection) new URI(BASE_URL+"/wiki/" + name.replace(" ","_")).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", BASE_URL+"/wiki/"  + name.replace(" ","_"));
            return document.getElementsByClass("mw-parser-output").select("p").first().text();
        } catch(IOException | URISyntaxException e){
            System.out.println("Can't find :"+e.getMessage());
        }
        return "";
    }
    @Override
    public void crawl() {
        JsonArray entities = new JsonArray();
        entities=getEntities("/Niên_biểu_lịch_sử_Việt_Nam");
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
            File file = new File("data/" + this.getClass().getSimpleName() + "/" + TITLE + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(entities.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        new Event();
    }
}
