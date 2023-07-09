package entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class Event extends Entity{
    public String getTime() {
        return time;
    }
    public String getLocation() {
        return location;
    }
    public String getReason() {
        return reason;
    }
    public String getResult() {
        return result;
    }
    public String getBelligerents() {
        return belligerents;
    }
    public String getCommanders() {
        return commanders;
    }
    public String getStrength() {
        return strength;
    }
    public String getLosses() {
        return losses;
    }
    private String time;
    private String location;
    private String reason;
    private String result;
    private String belligerents; //phe tham chiến

    private String commanders;

    private String strength;
    private String losses;

    public Event(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {
        processTime(jsonObject);
        processLocation(jsonObject);
        processReason(jsonObject);
        processResult(jsonObject);
        processBelligerents(jsonObject);
        processCommanders(jsonObject);
        processStrength(jsonObject);
        processLosses(jsonObject);
    }

    @Override
    public Entity merge(Entity entity) {
        Event event = (Event) entity;
        if (event.getDescription() != null && (getDescription() == null || event.getDescription().trim().length() > getDescription().trim().length()))
        {
            setDescription(event.getDescription());
        }

        if (event.getTime() != null && (getTime() == null || event.getTime().trim().length() > getTime().trim().length()))
        {
            time = event.getTime();
        }

        if (event.getLocation() != null && (getLocation() == null || event.getLocation().trim().length() > getLocation().trim().length()))
        {
            location = event.getLocation();
        }

        if (event.getReason() != null && (getReason() == null || event.getReason().trim().length() > getReason().trim().length()))
        {
            reason = event.getReason();
        }

        if (event.getResult() != null && (getResult() == null || event.getResult().trim().length() > getResult().trim().length()))
        {
            result = event.getResult();
        }

        if (event.getBelligerents() != null && (getBelligerents() == null || event.getBelligerents().trim().length() > getTime().trim().length()))
        {
            belligerents = event.getBelligerents();
        }

        if (event.getCommanders() != null && (getCommanders() == null || event.getCommanders().trim().length() > getTime().trim().length()))
        {
            commanders = event.getCommanders();
        }

        if (event.getStrength() != null && (getStrength() == null || event.getStrength().trim().length() > getTime().trim().length()))
        {
            strength = event.getStrength();
        }

        if (event.getLosses() != null && (getLosses() == null || event.getLosses().trim().length() > getTime().trim().length()))
        {
            losses = event.getLosses();
        }

        if (event.getImage() != null && getImage() == null)
        {
            setImage(event.getImage());
        }

        appendAllDocument(event.getAllDocument());


        for (String key: event.getProperties().keySet()){
            if (!getProperties().has(key)){
                addProperty(key, event.getProperties().get(key).getAsString());
            }
        }

        setSource(getSource() + " , " + event.getSource());
        return this;
    }

    private void processTime(JsonObject jsonObject)
    {
        time = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (properties.has("Thời gian"))
        {
            time = properties.get("Thời gian").getAsString();
        }
    }
    private void processLocation(JsonObject jsonObject)
    {
        location = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Địa điểm"))
            {
                location = properties.get("Địa điểm").getAsString();
            }
        }
    }
    private void processReason(JsonObject jsonObject)
    {
        reason = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Nguyên nhân bùng nổ"))
            {
                reason = properties.get("Nguyên nhân bùng nổ").getAsString();
            }
        }
    }
    private void processResult(JsonObject jsonObject)
    {
        result = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Kết quả"))
            {
                result = properties.get("Kết quả").getAsString();
            }
        }
    }
    private void processBelligerents(JsonObject jsonObject)
    {
        belligerents = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Tham chiến"))
            {
                int index = 0;
                for (String key : properties.keySet()) {
                    if (key.equals("Tham chiến")) {
                        break;
                    }
                    index++;
                }
                if (index + 1 < properties.keySet().size()) {
                    String nextKey = (String) properties.keySet().toArray()[index + 1];
                    belligerents = nextKey + " | " + properties.get(nextKey).getAsString();
                }
            }
        }
    }
    private void processCommanders(JsonObject jsonObject)
    {
        commanders = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Chỉ huy"))
            {
                int index = 0;
                for (String key : properties.keySet()) {
                    if (key.equals("Chỉ huy")) {
                        break;
                    }
                    index++;
                }
                if (index + 1 < properties.keySet().size()) {
                    String nextKey = (String) properties.keySet().toArray()[index + 1];
                    commanders = nextKey + " | " + properties.get(nextKey).getAsString();
                }
            }
        }
    }
    private void processStrength(JsonObject jsonObject)
    {
        strength = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Lực lượng"))
            {
                int index = 0;
                for (String key : properties.keySet()) {
                    if (key.equals("Lực lượng")) {
                        break;
                    }
                    index++;
                }
                if (index + 1 < properties.keySet().size()) {
                    String nextKey = (String) properties.keySet().toArray()[index + 1];
                    strength = nextKey + " | " + properties.get(nextKey).getAsString();
                }
            }
        }
    }
    private void processLosses(JsonObject jsonObject)
    {
        losses = null;
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Tổn thất"))
            {
                int index = 0;
                for (String key : properties.keySet()) {
                    if (key.equals("Tổn thất")) {
                        break;
                    }
                    index++;
                }
                if (index + 1 < properties.keySet().size()) {
                    String nextKey = (String) properties.keySet().toArray()[index + 1];
                    losses = nextKey + " | " + properties.get(nextKey).getAsString();
                }
            }
        }
    }
    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\n"
                + "Time: " + this.time + "\n"
                + "Location: " + this.location + "\n";
    }
}
