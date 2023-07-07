package crawler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.*;

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

        try(ExecutorService executorService = Executors.newFixedThreadPool(30)) {
            List<Future<JsonObject>> futures = new ArrayList<>();

            characterUrl.forEach(url -> {
                Callable<JsonObject> callable = () -> {
                    JsonObject entity;
                    if (url.contains(baseUrl)) {
                        entity = getEntity(url);
                    } else {
                        entity = getEntity(url.charAt(0) == '/' ? baseUrl + url : baseUrl + "/" + url);
                    }
                    if (entity != null) {
                        entity.addProperty("source", getBaseUrl());
                    }
                    return entity;
                };
                futures.add(executorService.submit(callable));
            });


            executorService.shutdown();

            for (Future<JsonObject> future : futures) {
                try {
                    JsonObject entity = future.get();
                    if (entity != null) {
                        character.add(entity);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
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

    // Get image from url
    protected String getImg(String url) {
        if (url.equals(getBaseUrl())) return null;
        try {
            URL imageUrl = new URI(url).toURL();

            // Generate a unique filename using a UUID
            String uniqueFileName = UUID.randomUUID() + ".png";

            try (InputStream in = new BufferedInputStream(imageUrl.openStream())) {
                // Create the destination directory if it doesn't exist
                Path directoryPath = Path.of("src/main/resources/gui/image");
                Files.createDirectories(directoryPath);

                // Copy the image to the destination path
                Path destination = directoryPath.resolve(uniqueFileName);
                Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return "image/" + uniqueFileName;

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Abstract method
    protected abstract Vector<String> getUrl();
    protected abstract JsonObject getEntity(String url);
    protected abstract void init();

    // Getter and setter
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
}
