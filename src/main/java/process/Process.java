package process;

import entity.Character;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import merge.*;

import java.util.ArrayList;
import java.util.List;

public class Process {
    private final ObservableList<Character> characters = FXCollections.observableArrayList();
    private final ObservableList<Monument> monuments = FXCollections.observableArrayList();
    private final ObservableList<Dynasty> dynasties = FXCollections.observableArrayList();
    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private final ObservableList<Festival> festivals = FXCollections.observableArrayList();

    private final List<String> characterNames = new ArrayList<>();

    public Process(){
        CharacterList charList = new CharacterList();
        charList.getEntities().forEach(character -> {
            characters.add((Character) character);
            characterNames.add(character.getName());
        });

        DynastyList dynList = new DynastyList();
        dynList.getEntities().forEach(dynasty -> dynasties.add((Dynasty) dynasty));
        EventList eventList = new EventList();
        eventList.getEntities().forEach(event -> events.add((Event) event));
        FestivalList festivalList = new FestivalList();
        festivalList.getEntities().forEach(festival -> festivals.add((Festival) festival));
        MonumentList monList = new MonumentList();
        monList.getEntities().forEach(monument -> monuments.add((Monument) monument));

        System.out.println("Characters: " + characters.size());

        System.out.println("Characters11: " + characters.size());
    }

//    private void addCharacterRelationships(){
//        AtomicInteger count = new AtomicInteger();
//        try(ExecutorService executorService = Executors.newFixedThreadPool(200)) {
//            for (Character character : characters) {
//                executorService.execute(() -> {
//                    for (String name : characterNames) {
//                        if (character.isRelated(name)) {
//                            character.addRelatedCharacter(name);
//                        }
//                    }
//                    System.out.println(count.getAndIncrement());
//                    for (Dynasty dynasty : dynasties) {
//                        if (character.isRelated(dynasty.getName()) || dynasty.isRelated(character.getName())) {
//                            character.addRelatedDynasty(dynasty.getName());
//                            dynasty.addRelatedCharacter(character.getName());
//                        }
//                    }
//
//                    for (Event event : events) {
//                        if (character.isRelated(event.getName()) || event.isRelated(character.getName())) {
//                            character.addRelatedEvent(event.getName());
//                            event.addRelatedCharacter(character.getName());
//                        }
//                    }
//
//                    for (Festival festival : festivals) {
//                        if (character.isRelated(festival.getName()) || festival.isRelated(character.getName())) {
//                            character.addRelatedFestival(festival.getName());
//                            festival.addRelatedCharacter(character.getName());
//                        }
//                    }
//
//                    for (Monument monument : monuments) {
//                        if (character.isRelated(monument.getName()) || monument.isRelated(character.getName())) {
//                            character.addRelatedMonument(monument.getName());
//                            monument.addRelatedCharacter(character.getName());
//                        }
//                    }
//                });
//            }
//
//        }
//    }
//
//    private void addDynastyRelationships(){
//        for (Dynasty dynasty: dynasties){
//            for (Dynasty dynasty1: dynasties){
//                if (dynasty.isRelated(dynasty1.getName()) || dynasty1.isRelated(dynasty.getName())){
//                    dynasty.addRelatedDynasty(dynasty1.getName());
//                    dynasty1.addRelatedDynasty(dynasty.getName());
//                }
//            }
//            for (Event event: events){
//                if (dynasty.isRelated(event.getName()) || event.isRelated(dynasty.getName())){
//                    dynasty.addRelatedEvent(event.getName());
//                    event.addRelatedDynasty(dynasty.getName());
//                }
//            }
//            for (Festival festival: festivals){
//                if (dynasty.isRelated(festival.getName()) || festival.isRelated(dynasty.getName())){
//                    dynasty.addRelatedFestival(festival.getName());
//                    festival.addRelatedDynasty(dynasty.getName());
//                }
//            }
//            for (Monument monument: monuments){
//                if (dynasty.isRelated(monument.getName()) || monument.isRelated(dynasty.getName())){
//                    dynasty.addRelatedMonument(monument.getName());
//                    monument.addRelatedDynasty(dynasty.getName());
//                }
//            }
//        }
//    }
//
//    private void addMonumentRelationships(){
//        for (Monument monument: monuments){
//            for (Monument monument1: monuments){
//                if (monument.isRelated(monument1.getName()) || monument1.isRelated(monument.getName())){
//                    monument.addRelatedMonument(monument1.getName());
//                    monument1.addRelatedMonument(monument.getName());
//                }
//            }
//            for (Event event: events){
//                if (monument.isRelated(event.getName()) || event.isRelated(monument.getName())){
//                    monument.addRelatedEvent(event.getName());
//                    event.addRelatedMonument(monument.getName());
//                }
//            }
//            for (Festival festival: festivals){
//                if (monument.isRelated(festival.getName()) || festival.isRelated(monument.getName())){
//                    monument.addRelatedFestival(festival.getName());
//                    festival.addRelatedMonument(monument.getName());
//                }
//            }
//        }
//    }
//
//    private void addEventRelationships(){
//        for (Event event: events){
//            for (Event event1: events){
//                if (event.isRelated(event1.getName()) || event1.isRelated(event.getName())){
//                    event.addRelatedEvent(event1.getName());
//                    event1.addRelatedEvent(event.getName());
//                }
//            }
//            for (Festival festival: festivals){
//                if (event.isRelated(festival.getName()) || festival.isRelated(event.getName())){
//                    event.addRelatedFestival(festival.getName());
//                    festival.addRelatedEvent(event.getName());
//                }
//            }
//        }
//    }
//
//    private void addFestivalRelationships(){
//        for (Festival festival: festivals){
//            for (Festival festival1: festivals){
//                if (festival.isRelated(festival1.getName()) || festival1.isRelated(festival.getName())){
//                    festival.addRelatedFestival(festival1.getName());
//                    festival1.addRelatedFestival(festival.getName());
//                }
//            }
//        }
//    }


    public ObservableList<Character> getCharacters() {
        return characters;
    }

    public ObservableList<Monument> getMonuments() {
        return monuments;
    }

    public ObservableList<Dynasty> getDynasties() {
        return dynasties;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    public ObservableList<Festival> getFestivals() {
        return festivals;
    }

    public List<String> getCharacterNames() {
        return characterNames;
    }
}
