package entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import merge.EntityList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dynasty extends Entity{
    public String getFounder() {
        return founder;
    }

    public String getLanguage() {
        return language;
    }

    public String getCapital() {
        return capital;
    }

    public List<String> getKings() {
        return kings;
    }

    public List<JsonObject> getTimeLineJson() {
        return timeLineJson;
    }

    private String founder;
    private String language;
    private String capital;
    private ArrayList<String> kings;

    private List<JsonObject> timeLineJson;

    public Dynasty(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {
        processFounder(jsonObject);
        processLanguage(jsonObject);
        processCapital(jsonObject);
        processKings(jsonObject);
        processTimeLineJson(jsonObject);
    }

    @Override
    public Entity merge(Entity entity) {
        Dynasty dynasty = (Dynasty) entity;
        if (dynasty.getDescription() != null && (getDescription() == null || dynasty.getDescription().trim().length() > getDescription().trim().length())) {
            setDescription(dynasty.getDescription());
        }

        if (dynasty.getFounder() != null && (getFounder() == null || dynasty.getFounder().trim().length() > getFounder().trim().length())) {
            founder = dynasty.getFounder();
        }


        if (dynasty.getLanguage() != null && (getLanguage() == null || dynasty.getLanguage().trim().length() > getLanguage().trim().length())) {
            language = dynasty.getLanguage();
        }

        if (dynasty.getCapital() != null && (getCapital() == null || dynasty.getCapital().trim().length() > getCapital().trim().length())) {
            capital = dynasty.getCapital();
        }

        appendAllDocument(dynasty.getAllDocument());

        for (String key: dynasty.getProperties().keySet()){
            if (!getProperties().has(key)){
                addProperty(key, dynasty.getProperties().get(key).getAsString());
            }
        }

        setSource(getSource() + ", " + dynasty.getSource());

        for (String king : dynasty.getKings()) {
            String kingName = king.replace("\"", "");
            boolean isExist = false;
            for (String king2 : kings) {
                String kingName2 = king2.replace("\"", "");
                List<String> reaKingName = EntityList.getForeName(kingName);
                List<String> reaKingName2 = EntityList.getForeName(kingName2);
                for (String name : reaKingName) {
                    for (String name2 : reaKingName2) {
                        if (name.equals(name2)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (isExist) {
                        break;
                    }
                }
            }
            if (!isExist) {
                System.out.println(kings);
                System.out.println("Add king: " + king);
                kings.add(king);
            }
        }

        for (JsonObject jsonObject : dynasty.getTimeLineJson()) {
            boolean isExist = false;
            for (JsonObject jsonObject2 : timeLineJson) {
                if (jsonObject2.get("name").getAsString().equals(jsonObject.get("name").getAsString())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                timeLineJson.add(jsonObject);
            }
        }



        return this;
    }


    private void processFounder(JsonObject jsonObject)
    {
        founder = null;

        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            JsonObject periods = jsonObject.get("periods").getAsJsonObject();
            if (!jsonObject.get("name").getAsString().equals("Bắc thuộc lần II")
                    && !jsonObject.get("name").getAsString().equals("Bắc thuộc lần III")
                    && !jsonObject.get("name").getAsString().equals("Hồng Bàng & Văn Lang")
                    && !jsonObject.get("name").getAsString().equals("Âu Lạc & Nam Việt"))
            {
                for(String kingName : periods.keySet())
                {
                    if (!kingName.contains("Thuộc"))
                    {
                        founder = kingName;
                        break;
                    }
                }
            }
            else if (jsonObject.get("name").getAsString().equals("Hồng Bàng & Văn Lang"))
            {
                founder = "Kinh Dương Vương";
            }
            else if (jsonObject.get("name").getAsString().equals("Âu Lạc & Nam Việt"))
            {
                founder = "An Dương Vương";
            }
            if (jsonObject.get("name").getAsString().equals("Nhà Tây Sơn"))
            {
                founder = "Quang Trung - Nguyễn Huệ";
            }
            if (jsonObject.get("name").getAsString().contains("Việt Nam")) {
                founder = null;
            }
        }
        else if (jsonObject.get("source").getAsString().contains("https://vansu.vn"))
        {
            JsonArray periods = jsonObject.get("period").getAsJsonArray();
            if (jsonObject.get("name").getAsString().contains("Trưng Nữ Vương"))
            {
                founder = "Trưng Nữ Vương";
            }
            else if (!jsonObject.get("name").getAsString().contains("Thời tiền sử"))
            {
                if(periods.size() > 0) {
                    JsonObject firstObj = periods.get(0).getAsJsonObject();
                    founder = firstObj.get("name").getAsString();
                    founder = founder.substring(founder.indexOf(".") + 2);
                }
            }
            if (jsonObject.get("name").getAsString().contains("Việt Nam")) {
                founder = null;
            }
        }
        else if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            String nameAttr = jsonObject.get("name").getAsString();
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            if (nameAttr.contains("Trưng Nữ vương") || nameAttr.contains("An Dương vương"))
            {
                founder = nameAttr;
            }
            else
            {
                if (properties.has("Hoàng đế"))
                {
                    founder = properties.get("Hoàng đế").getAsString();
                    founder = founder.substring(0, founder.indexOf(","));
                }
            }

            if (nameAttr.equals("Chiến tranh Đông Dương") || nameAttr.equals("Cộng hòa xã hội chủ nghĩa Việt Nam")) {
                founder = null;
            }
        }
    }

    private void processLanguage(JsonObject jsonObject)
    {
        language = null;
        if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            if (properties.has("Ngôn ngữ thông dụng"))
            {
                language = properties.get("Ngôn ngữ thông dụng").getAsString();
            }
        }
    }

    private void processCapital(JsonObject jsonObject)
    {
        capital = null;
        if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            if (properties.has("Thủ đô"))
            {
                capital = properties.get("Thủ đô").getAsString();
            }
            if (getName().equals("Chiến tranh Đông Dương") || getName().equals("Cộng hòa xã hội chủ nghĩa Việt Nam")) {
                capital = "Hà Nội";
            }
        }
    }

    private void processKings(JsonObject jsonObject) {
        kings = new ArrayList<>();

        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com")) {
            JsonObject periods = jsonObject.get("periods").getAsJsonObject();
            if (!jsonObject.get("name").getAsString().equals("Bắc thuộc lần II")
                    && !jsonObject.get("name").getAsString().equals("Bắc thuộc lần III")
                    && !jsonObject.get("name").getAsString().equals("Hồng Bàng & Văn Lang")
                    && !jsonObject.get("name").getAsString().equals("Âu Lạc & Nam Việt")
                    && !jsonObject.get("name").getAsString().equals("Nhà Tây Sơn")) {
                for (String kingName : periods.keySet()) {
                    if (!kingName.contains("Thuộc")) {
                        kings.add(kingName);
                    }
                }
            } else if (jsonObject.get("name").getAsString().equals("Hồng Bàng & Văn Lang")) {
                kings.add("Kinh Dương Vương");
                kings.add("Lạc Long Quân");
                kings.add("Hùng Vương");
            } else if (jsonObject.get("name").getAsString().equals("Âu Lạc & Nam Việt")) {
                kings.add("An Dương Vương");
            }

            if (jsonObject.get("name").getAsString().equals("Nhà Tây Sơn"))
            {
                kings.add("Quang Trung - Nguyễn Huệ");
            }

            if (jsonObject.get("name").getAsString().contains("Việt Nam")) {
                kings = new ArrayList<>();
            }
        }
        else if (jsonObject.get("source").getAsString().contains("https://vansu.vn"))
        {
            kings = new ArrayList<>();
            JsonArray periods = jsonObject.get("period").getAsJsonArray();
            if (jsonObject.get("name").getAsString().contains("Trưng Nữ vương"))
            {
                kings.add("Trưng Nữ Vương");
            }
            if (jsonObject.get("name").getAsString().contains("An Dương vương"))
            {
                kings.add("An Dương Vương");
            }
            else if (!jsonObject.get("name").getAsString().contains("Thời tiền sử"))
            {
                for (int i = 0; i < periods.size(); i++) {
                    JsonObject obj = periods.get(i).getAsJsonObject();
                    String nameValue = obj.get("name").getAsString();
                    nameValue = nameValue.substring(nameValue.indexOf(".") + 2);
                    kings.add(nameValue);
                }
            }

            if (jsonObject.get("name").getAsString().contains("Việt Nam")) {
                kings = new ArrayList<>();
            }
        }
        else if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            kings = new ArrayList<>();
            String nameAttr = jsonObject.get("name").getAsString();
            JsonObject properties = jsonObject.get("properties").getAsJsonObject();
            if (nameAttr.contains("Trưng Nữ Vương") || nameAttr.contains("An Dương Vương")) {
                kings.add(nameAttr);
            } else {
                if (properties.has("Hoàng đế")) {
                    String kingsListStr = properties.get("Hoàng đế").getAsString();
                    List<String> kings1 = Arrays.asList(kingsListStr.split(", "));
                    kings.addAll(kings1);
                }
            }
            if (nameAttr.equals("Chiến tranh Đông Dương") || nameAttr.equals("Cộng hòa xã hội chủ nghĩa Việt Nam")) {
                kings = new ArrayList<>();
            }
        }
    }


    private void processTimeLineJson(JsonObject jsonObject)
    {
        timeLineJson = new ArrayList<>();
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            JsonObject periods = jsonObject.get("periods").getAsJsonObject();
            for(String periodName : periods.keySet())
            {
                JsonObject period = new JsonObject();
                String name = periodName.replace("\"", "");
                period.addProperty("name", name);
                if (periods.get(periodName).getAsJsonObject().has("description"))
                {
                    period.addProperty("description", periods.get(periodName).getAsJsonObject().get("description").getAsString());
                } else if (periods.get(periodName).getAsJsonObject().has("properties")) {
                    StringBuilder description = new StringBuilder();
                    JsonObject properties = periods.get(periodName).getAsJsonObject().get("properties").getAsJsonObject();
                    for (String property : properties.keySet()) {
                        String propertyName = property.replace("\"", "");
                        description.append(propertyName).append(": ").append(properties.get(property).getAsString()).append("\\n");
                    }
                    period.addProperty("description", description.toString());
                }
                timeLineJson.add(period);
            }
        }
        else if (jsonObject.get("source").getAsString().contains("https://vansu.vn")) {
            JsonArray periods = jsonObject.get("period").getAsJsonArray();
            for(int i=0; i<periods.size(); i++)
            {
                JsonObject period = periods.get(i).getAsJsonObject();
                timeLineJson.add(period);
            }
        }

    }
    public String toString()
    {
        return getName()+ "\\n" + getKings();
    }
    public static void main(String[] args) throws FileNotFoundException {


    }
    }

