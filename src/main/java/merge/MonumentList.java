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
        int i = 0;
        for (Entity entity: getBaseEntities()) {
            boolean found = false;
            if(nameList != null && nameList.contains(entity.getName())){
                Entity entity1 = getEntities().get(nameList.indexOf(entity.getName()));
                if (entity1 != null && !containSource(entity1.getSource(), entity.getSource())){
                    System.out.println(i++ + " " + entity.getName());
                    mergeEntity(entity1, entity);
                    found = true;
                }
            }
            if (!found){
                addEntity(entity);
                nameList.add(entity.getName());
            }
        }
        System.out.println(nameList.size());
    }

    @Override
    protected void init() {
        nameList = new ArrayList<>();
    }

    public static void main(String[] args) {
        new MonumentList();
    }
}
