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
    private String dad;
    private String mom;
    private String partner;
    private String children;
    private String relatives;

    public Character(JsonObject jsonObject) {
        super(jsonObject);
    }

    public Entity merge(Entity entity){
        Character character = (Character) entity;
        if (character.getDescription() != null && (getDescription() == null || character.getDescription().trim().length() > getDescription().trim().length())) {
                setDescription(character.getDescription());
        }

        if (character.getDob() != null && (getDob() == null || character.getDob().trim().length() > getDob().trim().length())) {
                dob = character.getDob();

        }


        if (character.getPosition() != null && (getPosition() == null || character.getPosition().trim().length() > getPosition().trim().length())) {
                position = character.getPosition();

        }

        if (character.getDad() != null && (getDad() == null || character.getDad().trim().length() > getDad().trim().length())) {
                dad = character.getDad();

        }

        if (character.getMom() != null && (getMom() == null || character.getMom().trim().length() > getMom().trim().length())) {
            mom = character.getMom();
        }

        if (character.getPartner() != null && (getPartner() == null || character.getPartner().trim().length() > getPartner().trim().length())) {
                partner = character.getPartner();
        }

        if (character.getChildren() != null && (getChildren() == null || character.getChildren().trim().length() > getChildren().trim().length())) {
                children = character.getChildren();

        }

        if (character.getRelatives() != null && (getRelatives() == null || character.getRelatives().trim().length() > getRelatives().trim().length())) {
                relatives = character.getRelatives();

        }

        if (character.getImage() != null && getImage() == null) {
            setImage(character.getImage());
        }

        appendAllDocument(character.getAllDocument());

        for (String key: character.getProperties().keySet()){
            if (!getProperties().has(key)){
                addProperty(key, character.getProperties().get(key).getAsString());
            }
        }

        setSource(getSource() + " , " + character.getSource());
        return this;
    }

    @Override
    protected void getPropertiesFromJson(JsonObject jsonObject) {
        processDad(jsonObject);
        processMom(jsonObject);
        processPartner(jsonObject);
        processChildren(jsonObject);
        processRelatives(jsonObject);
        processPosition(jsonObject);
        processDob(jsonObject);
    }

    private void processDob(JsonObject jsonObject) {

        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        String description = "{}";
        if (jsonObject.has("description")) {
            description = jsonObject.get("description").getAsString();
        }

        //process from Vansu
        if (jsonObject.get("source").getAsString().contains("https://vansu.vn"))
        {
            if (properties.has("Sinh") || properties.has("Năm sinh")) {
                dob = properties.get("Năm sinh").getAsString();
            }
        }
        //process from Nguoikesu
        else if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            if (properties.has("Sinh") && !properties.get("Sinh").getAsString().matches("\\D+"))
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
                Pattern pattern = Pattern.compile("[\\d?]{1,4}\\s* ?[-–] ?\\s*[\\d?]{1,4}(?=[)])");
                Matcher matcher = pattern.matcher(description);
                if (matcher.find() && !matcher.group().matches("\\D+")) {
                    dob = matcher.group();
                }
                else
                {
                    //pattern = Pattern.compile("sinh\\s+[^)]+");
                    //pattern = Pattern.compile("[\\d?]{0,4}[ ]?[-–][ ]?[\\d?]{0,2}[ ]?tháng[ ][\\d?]{1,2}[ ]?năm[ ][\\d?]{0,4}[ ]?");
                    pattern = Pattern.compile("[\\d?]{0,2} ?tháng [\\d?]{1,2} năm [\\d?]{0,4} ?[-–] ?[\\d?]{0,2} ?tháng [\\d?]{1,2} năm [\\d?]{0,4} ?", Pattern.CANON_EQ);
                    matcher = pattern.matcher(description);
                    if (matcher.find() && !matcher.group().matches("\\D+"))
                    {
                        dob = matcher.group();
                    }
                    else
                    {
                        //pattern = Pattern.compile("[\\d?]{0,2} ?tháng [\\d?]{1,2} năm [\\d?]{0,4} ?[-–] [\\d?]{0,2} ?tháng [\\d?]{1,2} năm [\\d?]{0,4} ?");
                        //pattern = Pattern.compile("[\\d?]{0,4}[ ]?[-–][ ]?[\\d?]{0,2}[ ]?tháng[ ][\\d?]{1,2}[ ]?năm[ ][\\d?]{0,4}[ ]?");
                        pattern = Pattern.compile("[\\d?]{0,4} ?[-–] ?[\\d?]{0,2} ?tháng ?[\\d?]{1,2} ?[năm, ]{0,3} [\\d?]{0,4} ?");
                        matcher = pattern.matcher(description);
                        if (matcher.find() && !matcher.group().matches("\\D+"))
                        {
                            dob = matcher.group();
                        }
                        else
                        {
                            //pattern = Pattern.compile("[\\d?]{0,4}[ ]?[-–][ ]?[\\d?]{0,2}[ ]?tháng[ ][\\d?]{1,2}[ ]?năm[ ][\\d?]{0,4}[ ]?");
                            pattern = Pattern.compile("sinh\\s+[^).]+");
                            matcher = pattern.matcher(description);
                            if (matcher.find() && !matcher.group().matches("\\D+")) {
                                dob = matcher.group();
                            }
                        }
                    }
                }
            }
        }
        //Process from Wikipedia
        else if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org")) {
            //process property "dob"
            if (properties.has("Sinh") && properties.has("Mất") && !properties.get("Sinh").getAsString().matches("\\D+")) {
                dob = properties.get("Sinh").getAsString() + " - " + properties.get("Mất").getAsString();
            } else if (properties.has("Sinh") && !(properties.has("Mất")) && !properties.get("Sinh").getAsString().matches("\\D+")) {
                dob = properties.get("Sinh").getAsString() + " - " + "?";
            } else if (!(properties.has("Sinh")) && properties.has("Mất")) {
                dob = "?" + " - " + properties.get("Mất").getAsString();
            } else {
                dob = "? - ?";
            }

        }

        String location = "";
        if (jsonObject.get("source").getAsString().contains("https://vansu.vn") && (properties.has("Tỉnh thành"))) {
            if (!properties.get("Tỉnh thành").getAsString().equals("Không rõ"))
                location = properties.get("Tỉnh thành").getAsString();
            dob = location + ", " + dob;
        }



        if (dob != null && dob.contains(";")) {
            dob = dob.replace(";", "");
        }

        if (dob != null && dob.matches("\\D+"))
        {
            dob = null;
        }
    }

    private void processPosition(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        String description = "{}";
        if (jsonObject.has("description")) {
            description = jsonObject.get("description").getAsString();
        }


        //process from Vansu
        if (jsonObject.get("source").getAsString().contains("https://vansu.vn"))
        {
            Pattern pattern = Pattern.compile("^(.*?)(?=, (?:con|quê|sinh ngày|sinh quán|sinh|tên|cháu|nguyên quán|không rõ))", Pattern.CANON_EQ);
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
        else if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
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
                    if (key.equals("Chức vụ")) {
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

            else if (properties.has("Chức quan cao nhất"))
            {
                position = properties.get("Chức quan cao nhất").getAsString();
            }


            //else if (properties.entrySet().isEmpty() || properties.entrySet().size() == 1)
            else if (StringUtils.isEmpty(position))
            {
                String regex = "(?<=là nhà).*?(?=\\.)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(description);
                if (matcher.find()) {

                    position = "nhà " + matcher.group();
                }
                else if (description.contains("là một"))
                {
                    regex = "(?<=là một).*?(?=\\.)";
                    pattern = Pattern.compile(regex);
                    matcher = pattern.matcher(description);
                    if (matcher.find()) {

                        position = matcher.group();
                    }
                }
                else if (jsonObject.get("name").getAsString().equals("Ngô Thì Nhậm"))
                {
                    position = "danh sĩ, nhà văn đời hậu Lê–Tây Sơn, người có công lớn trong việc giúp triều Tây Sơn đánh lui quân Thanh";
                }
                else if (properties.has("Vua Việt Nam (chi tiết...)"))
                {
                    position = "Vua Việt Nam";
                }

                else
                {
                    regex = "(?<=là).*?(?=\\.)";
                    pattern = Pattern.compile(regex);
                    matcher = pattern.matcher(description);
                    if (matcher.find()) {
                        position = matcher.group();
                    }
                }
            }


            else if (StringUtils.isEmpty(position))
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
        else if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            position = "Vua Việt Nam";
        }


    }

    private void processDad(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        //Process for Nguoikesu and Wikipedia
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com")
            || jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            Pattern pattern_dad = Pattern.compile("Cha|Thân phụ|Bố", Pattern.CANON_EQ);
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

        if (dad != null && dad.contains(";")) {
            dad = dad.replace(";", "");
        }

    }

    private void processMom(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();

        //process for Nguoikesu and Wikipedia
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com")
            || jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            Pattern pattern_mom = Pattern.compile("Mẹ|Thân mẫu|Cha mẹ", Pattern.CANON_EQ);
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

        if (mom != null && mom.contains(";")) {
            mom = mom.replace(";", "");
        }
    }

    private void processPartner(JsonObject jsonObject) {
        JsonObject properties = jsonObject.get("properties").getAsJsonObject();
        //process for Nguoikesu and Wikipedia
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
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
        else if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
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
        if (jsonObject.get("source").getAsString().contains("https://nguoikesu.com"))
        {
            Pattern pattern_children = Pattern.compile("Con cái|Con|Hậu duệ|Hậu duệ Hậu duệ", Pattern.CANON_EQ);
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
        else if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org"))
        {
            Pattern pattern_children = Pattern.compile("Con cái|Con|Hậu duệ|Hậu duệ Hậu duệ", Pattern.CANON_EQ);
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
        if (jsonObject.get("source").getAsString().contains("https://vi.wikipedia.org") && (StringUtils.isEmpty(dad) && (properties.has("Tiền nhiệm"))))
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


    @Override
    public String toString() {
        return "Name: " + this.getName() + "\n" + "DOB: " + this.dob + "\n" + "Position: " + this.position
               + "\n" + "Partner: " + this.partner
               + "\n" + "Mom: " + this.mom
               + "\n" + "Dad: " + this.dad
               + "\n" + "Children: " + this.children
               + "\n" + "Relatives: " + this.relatives
               + "\n" + "Source: " + this.getSource()
               + "\n" + "Number of sources: " + this.getSource().split(" , ").length
               + "\n" + "Description: " + this.getDescription();
    }


    public static void main(String[] args) throws FileNotFoundException {
        JsonArray jsonArray = JsonParser.parseReader(new FileReader("data/Character/Văn Sử.json")).getAsJsonArray();
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
