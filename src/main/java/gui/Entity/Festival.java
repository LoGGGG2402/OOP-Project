package gui.Entity;

import java.util.List;

public class Festival extends Entity{
    private String fDate;
    private List<String> fPlaces;
    private List<String> fGovernments;
    private List<String> fFigures;
    private List<String> fEvents;

    public Festival(String name, String description, String fDate, List<String> fPlaces, List<String> fGovernments, List<String> fFigures, List<String> fEvents) {
        super(name, description);
        this.fDate = fDate;
        this.fPlaces = fPlaces;
        this.fGovernments = fGovernments;
        this.fFigures = fFigures;
        this.fEvents = fEvents;
    }

    public String getfDate() {
        return fDate;
    }

    public List<String> getfPlaces() {
        return fPlaces;
    }

    public List<String> getfGovernments() {
        return fGovernments;
    }

    public List<String> getfFigures() {
        return fFigures;
    }

    public List<String> getfEvents() {
        return fEvents;
    }
}
