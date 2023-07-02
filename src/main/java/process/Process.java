package process;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.*;
import entity.Character;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Process {
    List<Monument> monuments;
    List<Character> characters;
    List<Event> events;
    List<Dynasty> dynasties;
    List<Festival> festivals;

    JsonObject info = new JsonObject();
    
    public Process() {
        readEvents();
        readCharacters();
        readMonuments();
        readDynasty();
        readFestival();
        
        
    }
    
    private void readMonuments() {
        String path = "data/Monument";
        String[] listFile = new File(path).list();
        JsonObject jsonObject = new JsonObject();
        if (listFile == null) return;
        for (String file : listFile) {
            File f = new File(path + "/" + file);
            try {
                FileReader fileReader = new FileReader(f);
                JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        monuments.add(new Monument(jsonArray.get(i).getAsJsonObject()));
                    }
                }
                jsonObject.addProperty(file, jsonArray.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        info.add("Monument", jsonObject);
    }

    private void readCharacters() {
        JsonObject jsonObject = new JsonObject();
        String path = "data/Character";
        String[] listFile = new File(path).list();
        if (listFile == null) return;
        for (String file : listFile) {
            File f = new File(path + "/" + file);
            try {
                FileReader fileReader = new FileReader(f);
                JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        characters.add(new Character(jsonArray.get(i).getAsJsonObject()));
                    }
                }
                jsonObject.addProperty(file, jsonArray.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        info.add("Character", jsonObject);
    }
    
    private void readEvents() {
        String path = "data/Event";
        String[] listFile = new File(path).list();
        JsonObject jsonObject = new JsonObject();
        if (listFile == null) return;
        for (String file : listFile) {
            File f = new File(path + "/" + file);
            try {
                FileReader fileReader = new FileReader(f);
                JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        events.add(new Event(jsonArray.get(i).getAsJsonObject()));
                    }
                }
                jsonObject.addProperty(file, jsonArray.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        info.add("Event", jsonObject);
    }
    
    private void readDynasty(){
        String path = "data/Dynasty";
        String[] listFile = new File(path).list();
        JsonObject jsonObject = new JsonObject();
        if (listFile == null) return;
        for (String file : listFile) {
            File f = new File(path + "/" + file);
            try {
                FileReader fileReader = new FileReader(f);
                JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        dynasties.add(new Dynasty(jsonArray.get(i).getAsJsonObject()));
                    }
                }
                jsonObject.addProperty(file, jsonArray.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        info.add("Dynasty", jsonObject);
    }
    
    private void readFestival(){
        String path = "data/Festival";
        String[] listFile = new File(path).list();
        JsonObject jsonObject = new JsonObject();
        if (listFile == null) return;
        for (String file : listFile) {
            File f = new File(path + "/" + file);
            try {
                FileReader fileReader = new FileReader(f);
                JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        festivals.add(new Festival(jsonArray.get(i).getAsJsonObject()));
                    }
                }
                jsonObject.addProperty(file, jsonArray.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        info.add("Festival", jsonObject);
    }
    
    
}
