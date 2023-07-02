package entity;

import com.google.gson.JsonObject;

public class Event extends Entity{
    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    private String time;
    private String location;

    public Event(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.has("description") ? jsonObject.get("description").getAsString() : "");
    }
}
