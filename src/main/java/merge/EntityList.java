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

public abstract class EntityList {
    private final ObservableList<Entity> entities = FXCollections.observableArrayList();
    private final List<String> listName = new ArrayList<>();
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
        listName.add(entity.getName());
    }

    protected void mergeEntity(Entity oldEntity, Entity newEntity) {
        entities.set(entities.indexOf(oldEntity), oldEntity.merge(newEntity));
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<String> getListName() {
        return listName;
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
}
