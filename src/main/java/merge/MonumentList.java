package merge;

import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class MonumentList extends EntityList{

    public MonumentList() {
        super("data/Monument");
    }
    private List<String> nameList;

    @Override
    protected void merge() {
        for (Entity entity: getBaseEntities()) {
            if (entity.getName() == null || entity.getName().isEmpty()) continue;
            if(nameList != null && nameList.contains(entity.getName())){
                Entity entity1 = getEntities().get(nameList.indexOf(entity.getName()));
                if (entity1 == null){
                    addEntity(entity);
                    nameList.add(entity.getName());
                    continue;
                }
                mergeEntity(entity1, entity);
            }
            addEntity(entity);
            nameList.add(entity.getName());
        }
    }

    @Override
    protected void init() {
        nameList = new ArrayList<>();
    }
}
