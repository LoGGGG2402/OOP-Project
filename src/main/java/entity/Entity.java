package entity;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private final String name;
    private String description;
    private String image;
    private String source;
    private String allDocument;
    private final JsonObject properties = new JsonObject();

    protected Entity(JsonObject jsonObject) {
        this.name = jsonObject.get("name").getAsString();
        this.description = jsonObject.has("description") ? jsonObject.get("description").getAsString() : null;
        this.image = jsonObject.has("image") && !jsonObject.get("image").isJsonNull() ? jsonObject.get("image").getAsString() : null;
        this.source = jsonObject.has("source") ? jsonObject.get("source").getAsString() : null;
        this.allDocument = jsonObject.has("allDocument") ? jsonObject.get("allDocument").getAsString() : description;
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
            if (isRelated(character)) {
                relatedCharacters.add(character);
            }
        }
        return relatedCharacters;
    }


    public boolean isRelated(String characterName) {
        if (name.equals(characterName)) {
            return false;
        }
        if (allDocument != null && allDocument.contains(characterName)) {
            return true;
        }
        return properties.toString().contains(characterName);
    }

    public String getAllDocument() {
        return allDocument;
    }

    public void appendAllDocument(String allDocument) {
        if (this.allDocument == null) {
            this.allDocument = allDocument;
        } else {
            this.allDocument += allDocument;
        }
    }

    public abstract Entity merge(Entity entity);
}
