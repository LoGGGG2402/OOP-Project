package gui.Controller;

import gui.Entity.Festival;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class festivalController implements Initializable {
    @FXML
    public Label fName;
    @FXML
    public Label fDate;
    @FXML
    public Label fPlace;
    @FXML
    public Label fGov;
    @FXML
    public Label fFig;
    @FXML
    public Label fEvent;
    @FXML
    public Label fDes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setLabel(Festival festival) {
        fName.setText(festival.getName());
        fDate.setText(festival.getfDate());
        fDes.setText(festival.getDescription());
    }
}
