package merge;

import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class MonumentList extends EntityList{

    public MonumentList() {
        super("data/Monument");
    }
    private final List<String> nameList = new ArrayList<>();

    @Override
    protected void merge() {
        for (Entity entity: getBaseEntities()) {
            if(nameList.contains(entity.getName())){
                Entity entity1 = getEntities().get(nameList.indexOf(entity.getName()));
                mergeEntity(entity1, entity);
            }
            addEntity(entity);
            nameList.add(entity.getName());
        }
    }

    @Override
    protected void init() {

    }
}
