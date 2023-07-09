package entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Festival extends Entity{
    private String date;
    private String location;
    private String relatedCharacter;
    public Festival(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {
        processDate(jsonObject);
        processLocation(jsonObject);
        processRelatedCharacter(jsonObject);
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
    private void processDate(JsonObject jsonObject)
    {
        date = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (properties.has("Ngày bắt đầu (âm lịch)"))
        {
            if (!properties.get("Ngày bắt đầu (âm lịch)").getAsString().isEmpty())
            {
                date = properties.get("Ngày bắt đầu (âm lịch)").getAsString() +
                        " Âm lịch";
            }
        }
    }
    private void processLocation(JsonObject jsonObject)
    {
        location = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (properties.has("Vị trí"))
        {
            location = properties.get("Vị trí").getAsString();
        }
    }
    private void processRelatedCharacter(JsonObject jsonObject)
    {
        relatedCharacter = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (properties.has("Nhân vật liên quan"))
        {
            relatedCharacter = properties.get("Nhân vật liên quan").getAsString();
        }
    }

    public String toString()
    {
        return "Name: " + this.getName() + "\n"
                + "Date: " + this.getDate() + "\n"
                + "Location: " + this.getLocation() + "\n"
                + "Related character: " + this.getRelatedCharacter();
    }
}
