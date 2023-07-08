package gui.controller;

import entity.Monument;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MonumentController implements Initializable {
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
    public void setLabel(Monument place) {
        pName.setText(place.getName());

        pDes.setText(place.getDescription());
    }

}
