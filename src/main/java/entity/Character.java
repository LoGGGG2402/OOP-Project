package entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Character  extends Entity{
    private String dob;
    private String position;
    private String location;
    private String dad;
    private String mom;
    private String partner;
    private String children;
    private String relatives;
    private String image;

    public Character(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString(), jsonObject.has("description") ? jsonObject.get("description").getAsString() : "");
        this.image = jsonObject.has("image") &&  !jsonObject.get("image").isJsonNull() ? jsonObject.get("image").getAsString() : null;
        getPropertiesFromJson(jsonObject);
    }

    public void merge(Character character){
        if (character.getDescription().trim().length() > getDescription().trim().length()){
            setDescription(character.getDescription());
        }

        if (character.getDob().trim().length() > dob.trim().length()){
            dob = character.getDob();
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

        if (character.getImage() != null && image == null) {
            image = character.getImage();
        }

        if (character.getLocation().trim().length() > location.trim().length()) {
            location = character.getLocation();
        }

        for (String key: character.getProperties().keySet()){
            if (!getProperties().has(key)){
                addProperty(key, character.getProperties().get(key).getAsString());
            }
        }
    }

    private void getPropertiesFromJson(JsonObject jsonObject) {
        processDad(jsonObject);
        processMom(jsonObject);
        processPartner(jsonObject);
        processChildren(jsonObject);
        processRelatives(jsonObject);
        processLocation(jsonObject);
        processPostion(jsonObject);
        processDob(jsonObject);
    }

    private void processDob(JsonObject jsonObject) {

        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        String description = "{}";
        if (jsonObject.has("description")) {
            description = jsonObject.get("description").getAsString();
        }

        //process from Vansu
        if (jsonObject.get("source").getAsString().equals("https://vansu.vn"))
        {
            if (properties.has("Sinh") || properties.has("Năm sinh")) {
                dob = properties.get("Năm sinh").getAsString();
            }
        }
        //process from Nguoikesu
        else if (jsonObject.get("source").getAsString().equals("https://nguoikesu.com"))
        {
            if (properties.has("Sinh"))
            {
                if (properties.has("Mất"))
                {
                    dob = properties.get("Sinh").getAsString() + " - " + properties.get("Mất").getAsString();
                }
                else
                {
                    dob = properties.get("Sinh").getAsString();
                }
            }
            //process property "dob" from property "description"
            else
            {
                Pattern pattern = Pattern.compile("[\\d?]{1,4}\\s* ?[-–] ?\\s*[\\d?]{4}");
                Matcher matcher = pattern.matcher(description);
                if (matcher.find()) {
                    dob = matcher.group();
                }
                else
                {
                    pattern = Pattern.compile("sinh\\s+[^)]+");
                    matcher = pattern.matcher(description);
                    if (matcher.find())
                    {
                        dob = matcher.group();
                    }
                    else
                    {
                        pattern = Pattern.compile("[\\d?]{0,2} ?tháng [\\d?]{1,2} năm [\\d?]{0,4} ?[-–] [\\d?]{0,2} ?tháng [\\d?]{1,2} năm [\\d?]{0,4} ?");
                        matcher = pattern.matcher(description);
                        if (matcher.find())
                        {
                            dob = matcher.group();
                        }
                        else
                        {
                            pattern = Pattern.compile("\\D\\d{3,4}?\\s*-\\s*\\d{3,4}?\\D|-\\s*\\d{3,4}\\D|\\D\\d{3,4}\\s*-");
                            matcher = pattern.matcher(description);
                            if (matcher.find()){
                                dob = matcher.group();
                            }
                        }
                    }
                }
            }
        }
        //Process from Wikipedia
        else if (jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org")) {
            //process property "dob"
            if (properties.has("Sinh") && properties.has("Mất")) {
                dob = properties.get("Sinh").getAsString() + " - " + properties.get("Mất").getAsString();
            } else if (properties.has("Sinh") && !(properties.has("Mất"))) {
                dob = properties.get("Sinh").getAsString() + " - " + "?";
            } else if (!(properties.has("Sinh")) && properties.has("Mất")) {
                dob = "?" + " - " + properties.get("Mất").getAsString();
            } else {
                dob = "? - ?";
            }

        }

    }

    private void processPostion(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        String description = "{}";
        if (jsonObject.has("description")) {
            description = jsonObject.get("description").getAsString();
        }


        //process from Vansu
        if (jsonObject.get("source").getAsString().equals("https://vansu.vn"))
        {
            Pattern pattern = Pattern.compile("^(.*?)(?=, (?:con|quê|sinh ngày|sinh quán|sinh|tên|cháu|nguyên quán|không rõ))");
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                position = matcher.group(1).trim();
            }
            else
            {
                pattern = Pattern.compile("^[^.!?]*");
                matcher = pattern.matcher(description);
                if (matcher.find()) {
                    position = matcher.group(0).trim();
                }
            }
        }
        //process from Nguoikesu
        else if (jsonObject.get("source").getAsString().equals("https://nguoikesu.com"))
        {
            if (properties.has("Chức vụ"))
            {
                boolean found = false;
                List<String> keys = new ArrayList<>(properties.keySet());
                for (String key : keys) {
                    if (found) {
                        position = key;
                        break;
                    }

                    if (key.contains("Chức vụ")) {
                        found = true;
                    }
                }
            }
            else if (properties.has("Cấp bậc"))
            {
                position = properties.get("Cấp bậc").getAsString();
            }

            else if (properties.has("Công việc"))
            {
                position = properties.get("Công việc").getAsString();
            }

            else if (properties.has("Nghề nghiệp"))
            {
                position = properties.get("Nghề nghiệp").getAsString();
            }

            else if (properties.has("Ngành"))
            {
                position = properties.get("Ngành").getAsString();
            }

            else if (properties.entrySet().isEmpty() || properties.entrySet().size() == 1)
            {
                String regex = "(?<=là ).*?(?=\\.)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(description);
                if (matcher.find()) {
                    position = matcher.group();
                }
            }

            else
            {
                String temp = (String) properties.keySet().toArray()[1];
                if (temp.contains("("))
                    position = temp.substring(0, temp.indexOf("(")).trim();
                else
                {
                    position = temp;
                }
            }
        }
        //process from wikipedia
        else if (jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org"))
        {
            position = "Vua Việt Nam";
        }


    }

    private void processLocation(JsonObject jsonObject) {
        JsonObject properties = new JsonObject();
        if (jsonObject.get("source").getAsString().equals("https://vansu.vn") && (properties.has("Tỉnh thành"))) {
                location = properties.get("Tỉnh thành").getAsString();


        }
    }

    private void processDad(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        //Process for Nguoikesu and Wikipedia
        if (jsonObject.get("source").getAsString().equals("https://nguoikesu.com")
            || jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org"))
        {
            Pattern pattern_dad = Pattern.compile("Cha|Thân phụ|Bố");
            Matcher matcher_dad;
            for(String fieldName:properties.keySet())
            {
                matcher_dad = pattern_dad.matcher(fieldName);
                if (matcher_dad.find())
                {
                    dad = properties.get(fieldName).getAsString();
                }
            }
        }
        if (dad!=null && (dad.replace("?","").trim().equals(""))) {
            dad = null;
        }

    }

    private void processMom(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        //process for Nguoikesu and Wikipedia
        if (jsonObject.get("source").getAsString().equals("https://nguoikesu.com")
            || jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org"))
        {
            Pattern pattern_mom = Pattern.compile("Mẹ|Thân mẫu|Cha mẹ");
            Matcher matcher_mom;
            for(String fieldName:properties.keySet())
            {
                matcher_mom = pattern_mom.matcher(fieldName);
                if (matcher_mom.find())
                {
                    mom = properties.get(fieldName).getAsString();
                }
            }
        }

        if (mom!= null && mom.replace("?","").trim().equals("")) {
            mom = null;
        }
    }

    private void processPartner(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        //process for Nguoikesu and Wikipedia
        if (jsonObject.get("source").getAsString().equals("https://nguoikesu.com"))
        {
            Pattern pattern_partner = Pattern.compile("Vợ|Chồng|Phối ngẫu|Thê thiếp|Phu quân|Hoàng hậu|Hậu phi");
            Matcher matcher;
            for(String fieldName:properties.keySet())
            {
                matcher = pattern_partner.matcher(fieldName);
                if (matcher.find())
                {
                    partner = properties.get(fieldName).getAsString();
                }
            }

            if (properties.has("Hoàng hậu") && (properties.get("Hoàng hậu").getAsString().equals(""))) {
                    int index = 0;
                    for (String key : properties.keySet()) {
                        if (key.equals("Hoàng hậu")) {
                            break;
                        }
                        index++;
                    }
                    if (index + 1 < properties.keySet().size())
                    {partner = (String) properties.keySet().toArray()[index + 1];}

            }
        }
        else if (jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org"))
        {
            Pattern pattern_partner = Pattern.compile("Vợ|Chồng|Phối ngẫu|Thê thiếp|Phu quân|Hoàng hậu|Hậu phi|Hậu phi Hậu phi");
            Matcher matcher;
            for(String fieldName:properties.keySet())
            {
                matcher = pattern_partner.matcher(fieldName);
                if (matcher.find())
                {
                    partner = properties.get(fieldName).getAsString();
                }
            }
        }

        if (partner!=null && partner.replace("?","").trim().equals("")) {
            partner = null;
        }
    }

    private void processChildren(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().equals("https://nguoikesu.com"))
        {
            Pattern pattern_children = Pattern.compile("Con cái|Con|Hậu duệ|Hậu duệ Hậu duệ");
            Matcher matcher_children;
            for(String fieldName:properties.keySet())
            {
                matcher_children = pattern_children.matcher(fieldName);
                if (matcher_children.find())
                {
                    children = properties.get(fieldName).getAsString();
                }
            }

            if (properties.has("Hậu duệ") && (properties.get("Hậu duệ").getAsString().equals(""))) {
                    int index = 0;
                    for (String key : properties.keySet()) {
                        if (key.equals("Hậu duệ")) {
                            break;
                        }
                        index++;
                    }
                    if (index + 1 < properties.keySet().size())
                        children = (String) properties.keySet().toArray()[index + 1];

            }
        }
        else if (jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org"))
        {
            Pattern pattern_children = Pattern.compile("Con cái|Con|Hậu duệ|Hậu duệ Hậu duệ");
            Matcher matcher_children;
            for(String fieldName:properties.keySet())
            {
                matcher_children = pattern_children.matcher(fieldName);
                if (matcher_children.find())
                {
                    children = properties.get(fieldName).getAsString();
                }
            }
        }
    }

    private void processRelatives(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        if (jsonObject.get("source").getAsString().equals("https://vi.wikipedia.org") && (StringUtils.isEmpty(dad) && (properties.has("Tiền nhiệm"))))
                {
                    dad = properties.get("Tiền nhiệm").getAsString();


        }

        relatives = (properties.has("Họ hàng") ? properties.get("Họ hàng").getAsString() : "");

        if (dad != null)
        {
            relatives += (relatives.equals("") ? "" : "; ") + dad;
        }

        if (mom != null)
        {
            relatives += (relatives.equals("") ? "" : "; ") + mom;
        }

        if (partner != null)
        {
            relatives += (relatives.equals("") ? "" : "; ") + partner;
        }

        if (children != null)
        {
            relatives += (relatives.equals("") ? "" : "; ") + children;
        }
    }


    // Getters
    public String getDob() {
        return dob;
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

    public String getLocation() {
        return location;
    }

    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\n" + "DOB: " + this.dob + "\n" + "Location: " + this.location + "\n" + "Position: " + this.position
               + "\n" + "Partner: " + this.partner
               + "\n" + "Mom: " + this.mom
               + "\n" + "Dad: " + this.dad
               + "\n" + "Children: " + this.children
               + "\n" + "Relatives: " + this.relatives;
    }


    public static void main(String[] args) throws FileNotFoundException {
        JsonArray jsonArray = JsonParser.parseReader(new FileReader("data/Character/Người Kể Sử.json")).getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonNull()) {
                continue;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Character character = new Character(jsonObject);
            System.out.println(character);
            System.out.println("-------------------------------------------------");
        }

    }
}
