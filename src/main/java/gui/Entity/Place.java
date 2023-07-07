package gui.Entity;

import java.util.List;

public class Place extends Entity{
    private String plType;

    private List<String> plFigures;

    private List<String> plFestivals;

    private List<String> plEvents;

    private List<String> plGovernments;

    public Place(String name, String description,  String plType, List<String> plFigures, List<String> plFestivals, List<String> plEvents, List<String> plGovernments) {
        super(name, description);

        this.plType = plType;
        this.plFigures = plFigures;
        this.plFestivals = plFestivals;
        this.plEvents = plEvents;
        this.plGovernments = plGovernments;
    }




    public String getPlType() {
        return plType;
    }

    public List<String> getPlFigures() {
        return plFigures;
    }

    public List<String> getPlFestivals() {
        return plFestivals;
    }

    public List<String> getPlEvents() {
        return plEvents;
    }

    public List<String> getPlGovernments() {
        return plGovernments;
    }
}
