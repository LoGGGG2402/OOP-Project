package crawler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public abstract class Crawler {
    private String baseUrl;
    private String title;
    protected Crawler(){
        init();
        crawl();
    }
    protected void crawl(){
        Vector<String> characterUrl = getUrl();

        JsonArray character = new JsonArray();
        characterUrl.forEach(url -> {
            JsonObject entity;
            if(url.contains(baseUrl)){
                entity = getEntity(url);
            }else {
                entity = getEntity(url.charAt(0)=='/'?baseUrl+url:baseUrl+"/" +url);
            }
            if (entity != null){
            entity.addProperty("source", getBaseUrl());
            character.add(entity);}
        });
        //get class name

        // Make directory
        File directory = new File("data/" + this.getClass().getSimpleName());
        if (!directory.exists()) {
            if (directory.mkdirs())
                System.out.println("Directory is created!");
            else
                System.out.println("Failed to create directory!");
        }

        File file = new File("data/" + this.getClass().getSimpleName() + "/" + title + ".json");

        // Write to file
        try (FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(character.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected abstract Vector<String> getUrl();
    protected abstract JsonObject getEntity(String url);

    protected void setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
    }
    protected void setTitle(String title){
        this.title = title;
    }
    protected String getBaseUrl(){
        return baseUrl;
    }
    protected String getTitle(){
        return title;
    }
    protected abstract void init();
}
