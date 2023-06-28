package Entity;

import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    private String name;
    private String description;
    private Map<String, String> properties = new HashMap<String, String>();

    protected Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Entity(String name) {
        this.name = name;
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

    public Element toElement() {
        Element element = new Element("entity");
        element.attr("name", name);
        element.attr("description", description);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            Element property = new Element("property");
            property.attr("key", entry.getKey());
            property.attr("value", entry.getValue());
            element.appendChild(property);
        }
        return element;
    }
}
