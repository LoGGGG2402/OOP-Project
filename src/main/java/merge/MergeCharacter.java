package merge;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import entity.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.jena.ext.com.google.common.primitives.Floats.min;

public class MergeCharacter extends merge.Merge{
    ArrayList<Character> characters = new ArrayList<>();

    ArrayList<Character> mergedCharacters = new ArrayList<>();

    Map<String, List<Character>> listName = new HashMap<>();

    public MergeCharacter() {
        String path = "data/Character";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try(FileReader fileReader = new FileReader(file)) {
                    JsonArray jsonArray = new Gson().fromJson(new JsonReader(fileReader), JsonArray.class);

                    for (JsonElement element: jsonArray){
                        Character character = new Character(element.getAsJsonObject());
                        characters.add(character);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        merge();
        writeToFile("data/full/Character");
    }

    private void writeToFile(String path){
        try(FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {


            objectOut.writeObject(mergedCharacters);

            System.out.println("Danh sách các đối tượng đã được ghi vào tệp tin.");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi ghi danh sách các đối tượng vào tệp tin.");
            e.printStackTrace();
        }
    }

    // merge
    public void merge(){
        for(Character character: characters){
            String name = character.getName();
            if(listName.containsKey(name)){
                boolean equal = false;
                for (Character ch: listName.get(name)){
                    if (equalCharacter(ch, character)){
                        equal = true;
                        // remove old
                        listName.get(name).remove(ch);
                        mergedCharacters.remove(ch);
                        // merge new old
                        ch.merge(character);
                        // add new
                        listName.get(name).add(ch);
                        mergedCharacters.add(ch);
                        break;
                    }
                }
                if (!equal){
                    // add new
                    listName.get(name).add(character);
                    mergedCharacters.add(character);
                }
            } else {
                List<Character> list = new ArrayList<>();
                list.add(character);
                // add new
                listName.put(name, list);
                mergedCharacters.add(character);
            }
        }
    }


    // compare
    private boolean equalCharacter(Character oldChar, Character newChar){
        return !(!checkDob(oldChar.getBod(), newChar.getBod()) && !checkRealtion(oldChar.getRelatives(), newChar.getRelatives()));
    }

    private boolean checkDob(String oldDob, String newDob){

        Pattern pattern = Pattern.compile("(?<!\\d)\\d{3}(?!\\d)|\\d{4}");

        Matcher matcherOld = pattern.matcher(oldDob);
        Matcher matcherNew = pattern.matcher(newDob);

        while (matcherOld.find() && matcherNew.find()){
            String oldYear = matcherOld.group();
            String newYear = matcherNew.group();
            if(oldYear.equals(newYear)){
                return true;
            }
        }

        return false;
    }

    private boolean checkRealtion(String oldRelation, String newRelation){
        String[] oldRelations = oldRelation.split(";");
        String[] newRelations = newRelation.split(";");

        // lower case
        for(int i = 0; i < oldRelations.length; i++){
            oldRelations[i] = oldRelations[i].toLowerCase().trim();
        }
        for(int i = 0; i < newRelations.length; i++){
            newRelations[i] = newRelations[i].toLowerCase().trim();
        }

        int count = 0;
        for(String oldRelation1 : oldRelations){
            for(String newRelation1 : newRelations){
                if(oldRelation1.equals(newRelation1)){
                    count++;
                }
            }
        }

        return count >= min((float) oldRelations.length / 3, (float) newRelations.length / 3);
    }

}
