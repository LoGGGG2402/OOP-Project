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
        super(jsonObject.get("name").getAsString(), jsonObject.has("description") ? jsonObject.get("description").getAsString() : "");
    }

    public String getBod() {
        return bod;
    }

    public String getPosition() {
        return position;
    }

    public String getDad() {
        return dad;
    }

    public String getMom() {
        return mom;
    }

    public String getPartner() {
        return partner;
    }

    public String getChildren() {
        return children;
    }

    public String getRelatives() {
        return relatives;
    }

    public String getImage() {
        return image;
    }
}
