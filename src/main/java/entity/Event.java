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
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {

    }

    @Override
    public Entity merge(Entity entity) {

        return null;
    }
}
