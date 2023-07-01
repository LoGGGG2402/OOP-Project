package Entity;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    private String name;
    private String description;
    private Map<String, String> properties = new HashMap<>();

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

    public Map<String, String> getProperties() {
        return properties;
    }

}
