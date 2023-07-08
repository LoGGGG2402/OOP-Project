package entity;

import com.google.gson.JsonObject;

public class Festival extends Entity{
    private String date;
    private String location;
    private String relatedCharacter;
    public Festival(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {

    }

    @Override
    public Entity merge(Entity entity) {
        return null;
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
