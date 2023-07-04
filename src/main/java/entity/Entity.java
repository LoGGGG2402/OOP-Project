package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity implements Serializable {
    private final String name;
    private String description;
    private final Map<String, String> properties = new HashMap<>();

    protected Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

}
