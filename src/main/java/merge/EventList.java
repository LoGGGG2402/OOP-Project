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
                    System.out.println("equal: " + entity1.getName() + "||" + event.getName());
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
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getForeName("Trận Bạch Đằng lần thứ nhất năm 938"));
        System.out.println(getForeName("Trận Bạch Đằng, Ngô Quyền đánh bại quân Nam Hán trên sông Bạch Đằng"));
    }
}
