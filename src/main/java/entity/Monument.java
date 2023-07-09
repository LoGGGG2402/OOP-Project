package entity;

import com.google.gson.JsonObject;

import java.io.FileNotFoundException;

public class Monument extends Entity{
    private String location;
    private String type;
    public Monument(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {
        processLocation(jsonObject);
        processType(jsonObject);

    }

    @Override
    public Entity merge(Entity entity) {
        this.appendAllDocument(entity.getAllDocument());
        if (this.getDescription() == null && entity.getDescription() != null) this.setDescription(entity.getDescription());
        if (this.getImage() == null && entity.getImage() != null) this.setImage(entity.getImage());
        setSource(getSource() + ", " + entity.getSource());
        JsonObject properties = entity.getProperties();
        properties.entrySet().forEach(entry -> this.addProperty(entry.getKey(), entry.getValue().getAsString()));
        if (this.type == null && ((Monument) entity).type != null) this.type = ((Monument) entity).type;
        else if (this.type != null && ((Monument) entity).type != null && this.type.length() < ((Monument) entity).type.length())
        {
            this.type = ((Monument) entity).type;
        }
        if (this.location == null && ((Monument) entity).location != null) this.location = ((Monument) entity).location;
        else if (this.location != null && ((Monument) entity).location != null && this.location.length() < ((Monument) entity).location.length())
        {
            this.location = ((Monument) entity).location;
        }
        return this;
    }

    private void processLocation(JsonObject jsonObject)
    {
        location = "";
        //process from ditich
        if (jsonObject.get("source").getAsString().contains("http://ditich.vn"))
        {
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            location = properties.get("địa chỉ").getAsString();
        }
        //process from nguoikesu
        else if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            if (properties.has("Địa chỉ"))
            {
                location = properties.get("Địa chỉ").getAsString();
            }
            if (location.isEmpty() && properties.has("Vị trí"))
            {
                location = properties.get("Vị trí").getAsString();
            }
            if (location.isEmpty() && properties.has("Khu vực"))
            {
                location = properties.get("Khu vực").getAsString();
            }
            if (properties.has("Địa điểm"))
            {
                location = properties.get("Địa điểm").getAsString();
            }
        }
    }
    private void processType(JsonObject jsonObject)
    {
        type = "";
        //process from ditich
        if (jsonObject.get("source").getAsString().contains("http://ditich.vn"))
        {
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            type = properties.get("Loại hình di tích").getAsString();
            if (properties.has("Loại hình xếp hạng"))
            {
                type = type + ", " + properties.get("Loại hình xếp hạng").getAsString();
            }
        }
        else if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            if (properties.has("Kiểu"))
            {
                type = properties.get("Kiểu").getAsString();
            }
            if (type.isEmpty() && properties.has("Dạng"))
            {
                type = properties.get("Dạng").getAsString();
            }
            if (type.isEmpty() && properties.has("Phân loại"))
            {
                type = properties.get("Phân loại").getAsString();
            }
            if (type.isEmpty() && properties.has("Di tích quốc gia đặc biệt"))
            {
                type = properties.get("Di tích quốc gia đặc biệt").getAsString();
            }
            if (type.isEmpty() && properties.has("di tích quốc gia đặc biệt"))
            {
                type = properties.get("di tích quốc gia đặc biệt").getAsString();
            }
        }
    }
    public String toString()
    {
        return "name: " + getName() + "\n"
                + "location: " + location + "\n"
                + "type: " + type + "\n";
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public static void main(String[] args) throws FileNotFoundException {


    }
}
