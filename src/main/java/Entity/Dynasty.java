package Entity;

import com.google.gson.JsonObject;

import java.util.List;

public class Dynasty extends Entity{
    private String founder;
    private String language;
    private String capital;
    private String timeLine;
    private List<String> kings;

    public Dynasty(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());

    }
}
