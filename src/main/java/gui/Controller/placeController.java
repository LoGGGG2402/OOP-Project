package gui.Controller;

import gui.Entity.Place;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class placeController implements Initializable {
    @FXML
    public Label pName;
    @FXML
    public Label pType;
    @FXML
    public Label pFigure;
    @FXML
    public Label pFestival;
    @FXML
    public Label pEvent;
    @FXML
    public Label pGovernments;
    @FXML
    public Label pDes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setLabel(Place place) {
        pName.setText(place.getName());
        pType.setText(place.getPlType());
        pDes.setText(place.getDescription());
    }

}
