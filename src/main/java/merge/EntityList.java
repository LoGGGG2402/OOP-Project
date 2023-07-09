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
import org.apache.commons.lang3.math.NumberUtils;

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

    protected void merge() {
        for (Entity entity : getBaseEntities()) {
            Entity oldEntity = getEntity(entity.getName());
            if (oldEntity != null) {
                mergeEntity(oldEntity, entity);
            } else {
                addEntity(entity);
            }
        }
    }
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

    protected boolean matchYear(String year, String year2) {
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
                if (Math.abs(oldYearInt - newYearInt) <= 2){
                    return true;
                }
            }
        }
        return false;
    }

    protected static List<String> getForeName(String name){
        String pos = name;
        List<String> listDynasty = new ArrayList<>();

        Pattern pattern2 = Pattern.compile("[AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXY][^ ]+", Pattern.CASE_INSENSITIVE);

        int nextSpace = nextIndex(pos);

        StringBuilder dynasty = new StringBuilder();

        boolean found = false;


        while (nextSpace != -1){
            String nextWord = pos.substring(0, nextSpace);
            if (found){
                if (pattern2.matcher(nextWord.trim()).matches()){
                    dynasty.append(nextWord).append(" ");
                }
                else {
                    if (!dynasty.isEmpty())
                        listDynasty.add(dynasty.toString().trim().replace(")", "").replace("-", ""));
                    dynasty = new StringBuilder();
                    found = false;
                }
            }
            if (pattern2.matcher(nextWord).matches()){
                found = true;
                dynasty.append(nextWord).append(" ");
            }
            pos = pos.substring(nextSpace + 1);
            nextSpace = nextIndex(pos);
        }
        return listDynasty;
    }

    protected static int nextIndex(String pos){
        if (pos.contains(",") && pos.contains(".") && pos.contains(" ")){
            return NumberUtils.min(pos.indexOf(" "), pos.indexOf(","), pos.indexOf("."));
        } else if (pos.contains(",") && pos.contains(" ")){
            return NumberUtils.min(pos.indexOf(" "), pos.indexOf(","));
        } else if (pos.contains(".") && pos.contains(" ")){
            return NumberUtils.min(pos.indexOf(" "), pos.indexOf("."));
        } else if (pos.contains(",") && pos.contains(".")){
            return NumberUtils.min(pos.indexOf(","), pos.indexOf("."));
        } else if (pos.contains(",")){
            return pos.indexOf(",");
        } else if (pos.contains(".")){
            return pos.indexOf(".");
        } else {
            return pos.indexOf(" ");
        }
    }

}
