package Entity;

import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private String name;
    private String description;
    private Map<String, String> properties = new HashMap<String, String>();

    public Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Entity(String name) {
        this.name = name;
    }

    public Entity(Element element) {
        this.name = element.attr("name");
        this.description = element.attr("description");
        for (Element property : element.select("property")) {
            properties.put(property.attr("key"), property.attr("value"));
        }
    }

    public void addProperty(String key, String value) {
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

    public String toString() {
        return toElement().toString();
    }
}
