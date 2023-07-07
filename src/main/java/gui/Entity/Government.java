package gui.Entity;

import java.util.List;

public class Government extends Entity {
    private String govDateFounded;
    private String govDateEnded;
    private String govType;
    private List<String> govFigures;
    private List<String> govFestivals;
    private List<String> govPlaces;
    private List<String> govEvents;

    public Government(String name, String description, String govDateFounded, String govDateEnded, String govType, List<String> govFigures, List<String> govFestivals, List<String> govPlaces, List<String> govEvents) {
        super(name, description);
        this.govDateFounded = govDateFounded;
        this.govDateEnded = govDateEnded;
        this.govType = govType;
        this.govFigures = govFigures;
        this.govFestivals = govFestivals;
        this.govPlaces = govPlaces;
        this.govEvents = govEvents;
    }

    public String getGovDateFounded() {
        return govDateFounded;
    }

    public String getGovDateEnded() {
        return govDateEnded;
    }

    public String getGovType() {
        return govType;
    }

    public List<String> getGovFigures() {
        return govFigures;
    }

    public List<String> getGovFestivals() {
        return govFestivals;
    }

    public List<String> getGovPlaces() {
        return govPlaces;
    }

    public List<String> getGovEvents() {
        return govEvents;
    }
}
