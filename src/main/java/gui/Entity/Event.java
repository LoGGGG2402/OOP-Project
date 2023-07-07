package gui.Entity;

import java.util.List;

public class Event extends Entity{
    private String evTime;
    private List<String> evPlace;

    public Event(String name, String description, String evTime, List<String> evPlace) {
        super(name, description);
        this.evTime = evTime;
        this.evPlace = evPlace;
    }

    public String getEvTime() {
        return evTime;
    }

    public List<String> getEvPlace() {
        return evPlace;
    }
}
