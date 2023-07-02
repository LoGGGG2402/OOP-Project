package entity;

import com.google.gson.JsonObject;

public class Festival extends Entity{
    private String date;
    private String location;
    private String relatedCharacter;

    public Festival(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.has("description") ? jsonObject.get("description").getAsString() : "");

    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getRelatedCharacter() {
        return relatedCharacter;
    }
}
