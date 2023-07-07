package gui;

import entity.Character;
import gui.Controller.characterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;

public class FxmlLoader {
    private ScrollPane view;

    public ScrollPane getPane(String filename, Object entity) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename + ".fxml"));

            view = loader.load();
            /*
            if (filename.equals("governmentScene")) {
                governmentController controller = loader.getController();
                controller.setLabel((Government) entity);
            }*/
            if (filename.equals("characterScene")) {
                characterController controller = loader.getController();
                //System.out.println(((Character) entity).getRelatives().isBlank());
                controller.setLabel((Character) entity);
            }
            /*
            if (filename.equals("festivalScene")) {
                festivalController controller = loader.getController();
                controller.setLabel((Festival) entity);
            }
            if (filename.equals("placeScene")) {
                placeController controller = loader.getController();
                controller.setLabel((Place) entity);
            }
            if (filename.equals("eventScene")) {
                eventController controller = loader.getController();
                controller.setLabel((Event) entity);
            }
            */
        } catch (Exception e) {
            System.out.println(e);
        }
        return view;
    }



}
