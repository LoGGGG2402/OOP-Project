package crawler.wikipedia;

import com.google.gson.JsonArray;
import crawler.Crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Wikipedia extends Crawler {
    private static final String BASE_URL = "https://vi.wikipedia.org";
    private static final String TITLE = "wikipedia";

    protected void init() {
        setBaseUrl(BASE_URL);
        setTitle(TITLE);
    }

    protected abstract JsonArray getEntities();
    @Override
    public void crawl() {
        JsonArray entities1 = getEntities();
        JsonArray entities = new JsonArray();
        entities1.forEach(entity -> {
            entity.getAsJsonObject().addProperty("source", getBaseUrl());
            entities.add(entity);
        });
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
            e.printStackTrace();
        }
    }

}
