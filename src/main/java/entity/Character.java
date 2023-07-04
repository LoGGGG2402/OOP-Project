package entity;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

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

    public void merge(Character character){
        if (character.getDescription().trim().length() > getDescription().trim().length()){
            setDescription(character.getDescription());
        }
        if (bod.trim().length() < character.getBod().trim().length()){
            bod = character.getBod();
        }
        if (character.getPosition().trim().length() > position.trim().length()) {
            position = character.getPosition();
        }

        if (character.getDad().trim().length() > dad.trim().length()) {
            dad = character.getDad();
        }

        if (character.getMom().trim().length() > mom.trim().length()) {
            mom = character.getMom();
        }

        if (character.getPartner().trim().length() > partner.trim().length()) {
            partner = character.getPartner();
        }

        if (character.getChildren().trim().length() > children.trim().length()) {
            children = character.getChildren();
        }

        if (character.getRelatives().trim().length() > relatives.trim().length()) {
            relatives = character.getRelatives();
        }

        for (String key: character.getProperties().keySet()){
            if (!getProperties().has(key)){
                addProperty(key, character.getProperties().get(key).getAsString());
            }
        }
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
