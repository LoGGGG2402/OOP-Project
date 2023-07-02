package crawler.wikipedia;

import crawler.Crawler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
public abstract class Wikipedia extends Crawler {
    protected static final String BASE_URL = "https://vi.wikipedia.org";
    protected static final String TITLE = "wikipedia";

    public void crawl() {
        Vector<String> urls = getUrl();
        JsonArray entities = new JsonArray();
        for (String url : urls) {
            try {
                entities.add(getEntity(url));
            }catch (IndexOutOfBoundsException e){
            }
        }
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

    protected JsonObject getEntity(String url) {
        return null;
    }

    protected Vector<String> getUrl() {
        return null;
    }
    protected JsonArray getEntities(String url) {
        return null;
    }
    protected void init() {
        setBaseUrl(BASE_URL);
        setTitle(TITLE);
    }

}
