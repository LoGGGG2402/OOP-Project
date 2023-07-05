package entity;

import com.google.gson.JsonObject;

public class Monument extends Entity{
    private String location;
    private String type;
    public Monument(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {

    }
}
