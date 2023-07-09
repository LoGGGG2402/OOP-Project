package merge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Dynasty;
import entity.Entity;

import java.util.List;

public class DynastyList extends EntityList{

    public DynastyList() {
        super("data/Dynasty");
    }

    @Override
    protected void init() {
    }
    @Override
    protected void merge() {
        for (Entity entity: getBaseEntities()) {
            Dynasty dynasty = (Dynasty) entity;
            boolean found = false;
            for (Entity entity1: getEntities()) {
                Dynasty dynasty1 = (Dynasty) entity1;
                if (equalDynasty(dynasty1, dynasty)){
                    mergeEntity(dynasty1, dynasty);
                    found = true;
                }
            }
            if (!found){
                addEntity(dynasty);
            }
        }
    }


    private boolean equalDynasty(Dynasty oldDynasty, Dynasty newDynasty) {
        String oldName = oldDynasty.getName().replace("III", "thứ ba")
                .replace("II", "thứ hai").replace("IV", "thứ tư")
                .replace("I", "thứ nhất").replace("oà", "òa").trim().toLowerCase();
        String newName = newDynasty.getName().replace("III", "thứ ba")
                .replace("II", "thứ hai").replace("IV", "thứ tư")
                .replace("I", "thứ nhất").replace("oà", "òa").trim().toLowerCase();

        if (oldName.contains(newName) || newName.contains(oldName)) {
            return true;
        }

        if (oldDynasty.getFounder() == null || newDynasty.getFounder() == null) {
            return false;
        }
        String oldFounder = oldDynasty.getFounder().replace("\"", "");
        String newFounder = newDynasty.getFounder().replace("\"", "");

        List<String> oldFounderList = getForeName(oldFounder);
        List<String> newFounderList = getForeName(newFounder);
        for (String oldFou: oldFounderList) {
            for (String newFou: newFounderList) {
                if (oldFou.contains(newFou) || newFou.contains(oldFou)) {
                    int x = oldFou.split(" ").length;
                    int y = newFou.split(" ").length;
                    if (x >= 2 && y >= 2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        DynastyList dynastyList = new DynastyList();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (Entity entity: dynastyList.getEntities()) {
            Dynasty dynasty = (Dynasty) entity;
            System.out.println(gson.toJson(dynasty));
        }
    }
}
