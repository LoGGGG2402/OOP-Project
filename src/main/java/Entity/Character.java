package Entity;

import com.google.gson.JsonObject;

public class Character  extends Entity{
    private String bod;
    private String position;
    private String dad;
    private String mom;
    private String partner;
    private String children;
    private String relatives;
    private String image;

    public Character(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());
    }
}
