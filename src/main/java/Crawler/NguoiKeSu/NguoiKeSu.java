package Crawler.NguoiKeSu;

import Crawler.Crawler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public abstract class NguoiKeSu extends Crawler {
    protected static final String baseUrl = "https://nguoikesu.com";
    protected static final String title = "Người Kể Sử";
    protected void crawl(){
        Vector<String> characterUrl = getUrl();

        JsonArray character = new JsonArray();
        characterUrl.forEach(url -> character.add(getEntity(url.charAt(0)=='/'?baseUrl+url:baseUrl+"/" +url)));
        //get class name

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
            File file = new File("data/" + this.getClass().getSimpleName() + "/" + title + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(character.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected abstract JsonObject getEntity(String url);
    protected abstract Vector<String> getUrl();
}