package merge;
import entity.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MergeCharacter extends merge.Merge{
    ArrayList<Character> characters = new ArrayList<>();

    Map<String, Character> properties = new HashMap<>();

    public MergeCharacter() {
        String path = "data/Character";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Character character = (Character) objectInputStream.readObject();
                    characters.add(character);
                    objectInputStream.close();
                    fileInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // (?<!\d)\d{3}(?!\d)|\d{4}

}
