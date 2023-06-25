package MergerData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MergeCharacter extends MergerData.Merge{
    private final ArrayList<JsonArray> character = new ArrayList<>();
    private final JsonObject allCharacter = new JsonObject();

    private void readData(String path){
        String[] listFile = new File(path).list();
        if (listFile == null) return;

        for (String file : listFile) {
            File f = new File(path + "/" + file);
            try {
                FileReader fileReader = new FileReader(f);
                character.add(JsonParser.parseReader(fileReader).getAsJsonArray());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void merge(){
        for (JsonArray json : character) {
            for (JsonElement element : json) {
                JsonObject object = element.getAsJsonObject();

                String name = object.get("name").getAsString();
                String description = object.get("description").getAsString();
                JsonObject properties = object.get("properties").getAsJsonObject();

                if (!allCharacter.has(name)){
                    JsonArray array = new JsonArray();
                    array.add(object);
                    allCharacter.add(name, array);
                }else {
                    JsonArray array = allCharacter.get(name).getAsJsonArray();
                    for (JsonElement e : array) {
                        JsonObject oldProperties = e.getAsJsonObject().get("properties").getAsJsonObject();
                        JsonObject newProperties = properties.deepCopy();

                        Map<String, String> oldNewVal = new HashMap<>();


                        int count = 0;
                        for (String e1: newProperties.keySet()){
                            for (String e2: oldProperties.keySet()){
                                if (e1.contains(e2) || e2.contains(e1)){
                                    count++;
                                    String key;
                                    String value;
                                    if (e2.contains(e1)) key = e2;
                                    else key = e1;

                                    String value1 = newProperties.get(e1).getAsString();
                                    String value2 = oldProperties.get(e2).getAsString();

                                    newProperties.remove(e1);
                                    oldProperties.remove(e2);

                                    if (value1.contains(value2)) value = value1;
                                    else if (value2.contains(value1)) value = value2;
                                    else continue;

                                    oldNewVal.put(key, value);
                                }
                            }
                        }
                        if (oldNewVal.size() >= count * 0.75){
                            for (String key: oldNewVal.keySet()){
                                newProperties.addProperty(key, oldNewVal.get(key));
                            }
                        }
                    }
                }

            }
        }
    }

    private void writeData(){
        File directory = new File("data/MergedData");
        if (!directory.exists()) {
            if (directory.mkdirs())
                System.out.println("Directory is created!");
            else
                System.out.println("Failed to create directory!");
        }

        try {
            File file = new File("data/MergedData/Character.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(allCharacter.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public static void main(String[] args) {
        MergeCharacter mergeCharacter = new MergeCharacter();
        mergeCharacter.readData("data/Character");
        mergeCharacter.merge();
        mergeCharacter.writeData();
    }
}
