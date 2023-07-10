package merge;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import entity.Character;
import entity.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class EntityList {
    private final ObservableList<Entity> entities = FXCollections.observableArrayList();
    private final List<Entity> baseEntities = new ArrayList<>();

    protected EntityList(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        JsonObject jsonObject = new JsonObject();
        for (File file : listOfFiles) {
            if (file.isFile() && !file.getName().equals("info.json")) {
                try(FileReader fileReader = new FileReader(file)) {
                    JsonArray jsonArray = new Gson().fromJson(new JsonReader(fileReader), JsonArray.class);
                    if (path.contains("Character")){
                        for (JsonElement element: jsonArray){
                            Character character = new Character(element.getAsJsonObject());
                            baseEntities.add(character);
                        }
                    }
                    if (path.contains("Dynasty")){
                        for (JsonElement element: jsonArray){
                            entity.Dynasty dynasty = new entity.Dynasty(element.getAsJsonObject());
                            baseEntities.add(dynasty);
                        }
                    }
                    if (path.contains("Event")){
                        for (JsonElement element: jsonArray){
                            entity.Event event = new entity.Event(element.getAsJsonObject());
                            baseEntities.add(event);
                        }
                    }
                    if (path.contains("Festival")){
                        for (JsonElement element: jsonArray){
                            entity.Festival festival = new entity.Festival(element.getAsJsonObject());
                            baseEntities.add(festival);
                        }
                    }
                    if (path.contains("Monument")){
                        for (JsonElement element: jsonArray){
                            entity.Monument monument = new entity.Monument(element.getAsJsonObject());
                            baseEntities.add(monument);
                        }
                    }
                    jsonObject.addProperty(file.getName().split("\\.")[0], jsonArray.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        init();
        merge();
        jsonObject.addProperty("all", baseEntities.size());
        jsonObject.addProperty("merged", entities.size());
        try(FileWriter fileWriter = new FileWriter(path + "/info.json")) {
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addEntity(Entity entity) {
        entities.add(entity);
    }

    protected void mergeEntity(Entity oldEntity, Entity newEntity) {
        entities.set(entities.indexOf(oldEntity), oldEntity.merge(newEntity));
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public List<Entity> getBaseEntities() {
        return baseEntities;
    }

    protected abstract void merge();
    protected abstract void init();

    protected Entity getEntity(String name) {
        for (Entity entity: entities) {
            if(entity == null) {
                continue;
            }
            if (entity.getName().contains(name) || name.contains(entity.getName()) ) {
                return entity;
            }
        }
        return null;
    }


    public static List<String> getForeName(String text) {
        List<String> foreNames = new ArrayList<>();
        text = text.trim();

        text = text.replace(", ", " a ").replace(".", "").replace(";", "").replace(":", "").replace("?", "").replace("!", "").replace(" - ", "-").replace("-", " - ");

        String[] words = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();


        for (int i = 0; i < words.length; i++) {
            if (words[i].isEmpty()){
                continue;
            }
            if (java.lang.Character.isUpperCase(words[i].charAt(0))) {
                stringBuilder.append(words[i]);
                stringBuilder.append(" ");
            } else {
                if (stringBuilder.length() > 0) {
                    foreNames.add(stringBuilder.toString().trim());
                    stringBuilder = new StringBuilder();
                }
            }
            if (i == words.length - 1 && stringBuilder.length() > 0) {
                foreNames.add(stringBuilder.toString().trim());
            }
        }


        return foreNames;
    }

    protected boolean matchYear(String year, String year2, int tolerance) {
        if (year == null || year2 == null) {
            return false;
        }
        if (year.equals(year2)) {
            return true;
        }
        Pattern pattern = Pattern.compile("(?<!\\d)\\d{3}(?!\\d)|\\d{4}");

        Matcher matcherOld = pattern.matcher(year);
        Matcher matcherNew = pattern.matcher(year2);

        List<String> oldYears = new ArrayList<>();
        List<String> newYears = new ArrayList<>();

        while (matcherOld.find()){
            oldYears.add(matcherOld.group());
        }

        while (matcherNew.find()){
            newYears.add(matcherNew.group());
        }

        for (String oldYear: oldYears){
            int oldYearInt = Integer.parseInt(oldYear);
            for (String newYear: newYears){
                int newYearInt = Integer.parseInt(newYear);
                if (Math.abs(oldYearInt - newYearInt) <= tolerance){
                    return true;
                }
            }
        }
        return false;
    }
    protected boolean containSource(String oldSource, String newSource) {
        if (oldSource == null || newSource == null) {
            return false;
        }
        return oldSource.contains(newSource.substring(0, 10));
    }

}
