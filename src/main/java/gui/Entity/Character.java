package gui.Entity;

import java.util.List;

public class Character extends Entity {
    private String cBirth;
    private String cDeath;
    private List<String> cGovernment;
    private List<String> cRelatives;

    private List<String> cFestivals;
    private List<String> cPlaces;
    public Character(String name, String description){
        super(name, description);
    }

    public Character(String name, String description, String cBirth, String cDeath, List<String> cGovernment, List<String> cRelatives, List<String> cFestivals, List<String> cPlaces) {
        super(name, description);
        this.cBirth = cBirth;
        this.cDeath = cDeath;
        this.cGovernment = cGovernment;
        this.cRelatives = cRelatives;
        this.cFestivals = cFestivals;
        this.cPlaces = cPlaces;
    }

    public String getcBirth() {
        return cBirth;
    }

    public String getcDeath() {
        return cDeath;
    }

    public List<String> getcGovernment() {
        return cGovernment;
    }

    public List<String> getcRelatives() {
        return cRelatives;
    }

    public List<String> getcFestivals() {
        return cFestivals;
    }

    public List<String> getcPlaces() {
        return cPlaces;
    }
}
