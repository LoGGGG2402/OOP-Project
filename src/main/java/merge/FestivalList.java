package merge;

import entity.Entity;

public class FestivalList extends EntityList{

        public FestivalList() {
            super("data/Festival");
        }

        @Override
        protected void merge() {
                for (Entity entity: getBaseEntities()) {
                        addEntity(entity);
                }
        }

        @Override
        protected void init() {

        }
}
