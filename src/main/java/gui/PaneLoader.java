package gui;

import entity.*;
import entity.Character;
import gui.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class PaneLoader {
    private ScrollPane view;
    private Pane menu;

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
                CharacterController controller = loader.getController();
                //System.out.println(((Character) entity).getRelatives().isBlank());
                controller.setLabel((Character) entity);
            }
            if (filename.equals("festivalScene")) {
                FestivalController controller = loader.getController();
                controller.setLabel((Festival) entity);
            }
            if (filename.equals("eventScene")) {
                EventController controller = loader.getController();
                controller.setLabel((Event) entity);
            }

            if (filename.equals("monumentScene")) {
                MonumentController controller = loader.getController();
                controller.setLabel((Monument) entity);
            }

            if (filename.equals("dynastyScene")) {
                DynastyController controller = loader.getController();
                controller.setLabel((Dynasty) entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return view;
    }
    public Pane getMenu(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("menuScene.fxml"));
            menu = loader.load();

        } catch (Exception e) {
            System.out.println(e);
        }
        return menu;
    }
}
