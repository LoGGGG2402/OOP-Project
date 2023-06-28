package Entity;

import com.google.gson.JsonObject;

public class Event extends Entity{
    private String time;
    private String location;

    public Event(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());
    }
}
