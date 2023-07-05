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
    private String image;
    private String source;
    private final JsonObject properties = new JsonObject();

    protected Entity(JsonObject jsonObject) {
        this.name = jsonObject.get("name").getAsString();
        this.description = jsonObject.has("description") ? jsonObject.get("description").getAsString() : null;
        this.image = jsonObject.has("image") && !jsonObject.get("image").isJsonNull() ? jsonObject.get("image").getAsString() : null;
        this.source = jsonObject.has("source") ? jsonObject.get("source").getAsString() : null;
        if (jsonObject.has("properties")) {
            JsonObject pro = jsonObject.get("properties").getAsJsonObject();
            pro.entrySet().forEach(entry -> this.properties.addProperty(entry.getKey(), entry.getValue().getAsString()));
        }
        getPropertiesFromJson(jsonObject);
    }

    protected abstract void getPropertiesFromJson(JsonObject jsonObject);

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
