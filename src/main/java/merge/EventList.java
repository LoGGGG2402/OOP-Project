package merge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Entity;
import entity.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventList extends EntityList{
    public EventList() {
        super("data/Event");
        for (Entity entity: getEntities()){
            Event event = (Event) entity;
            System.out.println(event);
        }
    }

    @Override
    protected void init() {
    }

    @Override
    protected void merge(){
        for(Entity entity: getBaseEntities()){
            Event event = (Event) entity;
            boolean found = false;
            for (Entity entity1: getEntities()){
                if (equalEvent((Event) entity1, event)){
                    mergeEntity(entity1, event);
                    found = true;
                    break;
                }
            }
            if (!found){
                addEntity(event);
            }
        }
    }

    private boolean equalEvent(Event oldEvent, Event newEvent) {
        return equalForeName(oldEvent.getName(), newEvent.getName()) &&
                matchYear(oldEvent.getTime(), newEvent.getTime(), 0);
    }

    private boolean equalForeName(String oldForeName, String newForeName) {
        List<String> oldForeNameList = getForeName(oldForeName);
        List<String> newForeNameList = getForeName(newForeName);

        for (String oldName: oldForeNameList) {
            for (String newName: newForeNameList) {
                if (oldName.contains(newName) || newName.contains(oldName)) {
                    int x = oldName.split(" ").length;
                    int y = newName.split(" ").length;
                    if (x >= 2 && y >= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<String> getForeName(String text) {
        List<String> foreNames = new ArrayList<>();
        text = text.trim();

        text = text.replace(", ", " a ").replace(".", "").replace(";", "").replace(":", "").replace("?", "").replace("!", "").replace(" - ", "-").replace("-", " - ");

        String[] words = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();


        for (int i = 0; i < words.length; i++) {
            if (Character.isUpperCase(words[i].charAt(0))) {
                stringBuilder.append(words[i]);
                stringBuilder.append(" ");
            } else {
                if (stringBuilder.length() > 0) {
                    foreNames.add(stringBuilder.toString().trim());
                    stringBuilder = new StringBuilder();
                }
            }
            if (i == words.length - 1 && stringBuilder.length() > 0) {
                foreNames.add(stringBuilder.toString().trim());
            }
        }


        return foreNames;
    }

    public static void main(String[] args) {
        var EventList = new EventList();
        for (Entity entity: EventList.getEntities()){
            Event event = (Event) entity;
            System.out.println(event);
        }
    }
}
