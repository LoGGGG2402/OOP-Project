package entity;

import com.google.gson.JsonObject;

public class Monument extends Entity{
    private String location;
    private String type;
    public Monument(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.has("description") ? jsonObject.get("description").getAsString() : "");
    }
}
