package gui.controller;

import entity.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    @FXML
    private Label eName;

    @FXML
    private Label eDes;

    @FXML
    private Label eTime;

    @FXML
    private Label ePlace;



    public void setLabel(Event event) {
        eName.setText(event.getName());
        eDes.setText(event.getDescription());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
