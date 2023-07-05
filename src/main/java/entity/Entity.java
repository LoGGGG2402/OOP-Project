package entity;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity implements Serializable {
    private final String name;
    private String description;
    private final JsonObject properties = new JsonObject();

    protected Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected void addProperty(String key, String value) {
        properties.addProperty(key, value);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonObject getProperties() {
        return properties;
    }

    public List<String> relatedEntity(List<String> charactersNames) {
        String property = properties.toString();
        List<String> relatedCharacters = new ArrayList<>();
        for (String character : charactersNames) {
            if (property.contains(character) || description.contains(character)) {
                relatedCharacters.add(character);
            }
        }
        return relatedCharacters;
    }

}
