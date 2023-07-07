package gui.Controller;

import gui.Entity.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class eventController implements Initializable {
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
        eTime.setText(event.getEvTime());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
