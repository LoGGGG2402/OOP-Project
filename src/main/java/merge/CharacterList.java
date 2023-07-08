package merge;

import entity.Character;
import entity.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.jena.ext.com.google.common.primitives.Floats.min;

public class CharacterList extends EntityList{
    Map<String, List<Character>> listHashMap;

    public CharacterList() {
        super("data/Character");
    }

    protected void init(){
        listHashMap = new HashMap<>();
    }

    // merge
    @Override
    protected void merge(){
        for(Entity entity: getBaseEntities()){
            Character character = (Character) entity;
            String name = character.getName();
            if(listHashMap.containsKey(name)){
                boolean equal = false;
                for (Character ch: listHashMap.get(name)){
                    if (equalCharacter(ch, character) && !ch.getSource().contains(character.getSource())){
                        equal = true;
                        System.out.println("equal : " + character.getName());
                        mergeEntity(ch, character);
                        break;}
                }
                if (!equal){
                    // add new
                    System.out.println("not equal : " + character.getName());
                    listHashMap.get(name).add(character);
                    addEntity(character);
                }
            } else {
                List<Character> list = new ArrayList<>();
                list.add(character);
                // add new
                listHashMap.put(name, list);
                addEntity(character);
            }
        }
    }

    // compare
    private boolean equalCharacter(Character oldChar, Character newChar) {
        boolean relativesCheck = checkRealtion(oldChar.getRelatives(), newChar.getRelatives());
        if(relativesCheck){
            return true;
        }
        boolean dobCheck = checkDob(oldChar.getDob(), newChar.getDob());
        if (dobCheck && (oldChar.getRelatives() == null || newChar.getRelatives() == null)){
            return true;
        }
        return checkPosition(oldChar, newChar);
    }

    private boolean checkDob(String oldDob, String newDob){
        if (oldDob == null && newDob == null){
            return false;
        }

        if (oldDob == null || newDob == null){
            return true;
        }

        Pattern pattern = Pattern.compile("(?<!\\d)\\d{3}(?!\\d)|\\d{4}");

        Matcher matcherOld = pattern.matcher(oldDob);
        Matcher matcherNew = pattern.matcher(newDob);

        List<String> oldYears = new ArrayList<>();
        List<String> newYears = new ArrayList<>();

        while (matcherOld.find()){
            oldYears.add(matcherOld.group());
        }

        while (matcherNew.find()){
            newYears.add(matcherNew.group());
        }

        for (String oldYear: oldYears){
            int oldYearInt = Integer.parseInt(oldYear);
            for (String newYear: newYears){
                int newYearInt = Integer.parseInt(newYear);
                if (Math.abs(oldYearInt - newYearInt) <= 2){
                    return true;
                }
            }
        }



        return false;
    }

    private boolean checkRealtion(String oldRelation, String newRelation){
        if (oldRelation == null && newRelation == null){
            return false;
        }

        if (oldRelation == null || newRelation == null){
            return true;
        }
        String[] oldRelations = ";".split(oldRelation);
        String[] newRelations = ";".split(newRelation);

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
                if(oldRelation1.contains(newRelation1) || newRelation1.contains(oldRelation1)){
                    count++;
                }
            }
        }

        return count >= min((float) oldRelations.length / 3, (float) newRelations.length / 3);
    }

    private boolean checkPosition(Character oldChar, Character newChar){
        String oldPosition = oldChar.getPosition();
        String newPosition = newChar.getPosition();
        if (oldPosition == null && newPosition == null){
            return false;
        }
        if (oldPosition == null || newPosition == null){
            return false;
        }
        String trim = oldPosition.toLowerCase().replaceAll("\\W", "").trim();
        String trim1 = newPosition.toLowerCase().replaceAll("\\W", "").trim();
        if(trim.contains(trim1) || trim1.contains(trim)) return true;

        List<String> listOldDynasty = getDynasty(oldPosition);
        listOldDynasty.addAll(getDynasty(oldChar.getDescription()));
        List<String> listNewDynasty = getDynasty(newPosition);
        listNewDynasty.addAll(getDynasty(newChar.getDescription()));

        for (String oldDynasty: listOldDynasty){
            for (String newDynasty: listNewDynasty){
                if (oldDynasty.equals(newDynasty)){
                    return true;
                }
            }
        }


        Pattern pattern = Pattern.compile("(đại|thần|tướng|[A-Z]\\S+)", Pattern.CANON_EQ);
        Matcher matcher = pattern.matcher(oldChar.getPosition());

        int count = 0;
        while (matcher.find()) {
            String word = matcher.group(1);
            if (newChar.getPosition().contains(word)){
                count++;
            }
        }

        return count >= 2;
    }

    private static List<String> getDynasty(String position){
        String pos = position;
        List<String> listDynasty = new ArrayList<>();

        Pattern pattern = Pattern.compile("nhà|đời|thời|vua", Pattern.CANON_EQ);
        Pattern pattern2 = Pattern.compile("[AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXY][^ ]+", Pattern.CANON_EQ);

        int nextSpace = nextIndex(pos);

        StringBuilder dynasty = new StringBuilder();

        boolean found = false;


        while (nextSpace != -1){
            String nextWord = pos.substring(0, nextSpace);
            if (found){
                if (pattern2.matcher(nextWord.trim()).matches()){
                    dynasty.append(nextWord).append(" ");
                }
                else {
                    if (!dynasty.isEmpty())
                        listDynasty.add(dynasty.toString().trim().replace(")", "").replace("-", ""));
                    dynasty = new StringBuilder();
                    found = false;
                }
            }
            if (pattern.matcher(nextWord).matches()){
                found = true;
            }
            pos = pos.substring(nextSpace + 1);
            nextSpace = nextIndex(pos);
        }
        return listDynasty;
    }

    private static int nextIndex(String pos){
        if (pos.contains(",") && pos.contains(".") && pos.contains(" ")){
            return NumberUtils.min(pos.indexOf(" "), pos.indexOf(","), pos.indexOf("."));
        } else if (pos.contains(",") && pos.contains(" ")){
            return NumberUtils.min(pos.indexOf(" "), pos.indexOf(","));
        } else if (pos.contains(".") && pos.contains(" ")){
            return NumberUtils.min(pos.indexOf(" "), pos.indexOf("."));
        } else if (pos.contains(",") && pos.contains(".")){
            return NumberUtils.min(pos.indexOf(","), pos.indexOf("."));
        } else if (pos.contains(",")){
            return pos.indexOf(",");
        } else if (pos.contains(".")){
            return pos.indexOf(".");
        } else {
            return pos.indexOf(" ");
        }
    }

    public ObservableList<Character> getCharacterList() {
        ObservableList<Character> list = FXCollections.observableArrayList();
        for (Entity character : getEntities()) {
            list.add((Character) character);
        }
        return list;
    }

    public static void main(String[] args) {
        new CharacterList().getEntities().forEach(System.out::println);
    }
}
