package merge;

import entity.Entity;
import entity.Event;

import java.util.List;

public class EventList extends EntityList{
    public EventList() {
        super("data/Event");
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



    public static void main(String[] args) {
//        var EventList = new EventList();
//        for (Entity entity: EventList.ge/tEntities()){
//            Event event = (Event) entity;
//
//        }
    }
}
