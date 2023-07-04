package entity;

import com.google.gson.JsonObject;

import java.util.List;

public class Dynasty extends Entity{
    public String getFounder() {
        return founder;
    }

    public String getLanguage() {
        return language;
    }

    public String getCapital() {
        return capital;
    }

    public List<String> getKings() {
        return kings;
    }

    private String founder;
    private String language;
    private String capital;
    private List<String> kings;

    private List<JsonObject> timeLineJson;

    public Dynasty(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.has("description") ? jsonObject.get("description").getAsString() : "");
    }
}
