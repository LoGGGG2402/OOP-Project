package gui.Controller;

import gui.Entity.Government;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class governmentController implements Initializable {
    @FXML
    public Label gdEnd;
    @FXML
    public Label gType;
    @FXML
    public Label gFigure;
    @FXML
    public Label gFes;
    @FXML
    public Label gPlace;
    @FXML
    public Label gEvent;
    @FXML
    private Label gName;
    @FXML
    private Label gDes;
    @FXML
    private Label gdFound;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setLabel(Government government) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/mainScene.fxml"));
        Parent root = loader.load();
        mainController mainController = loader.getController();
        gName.setText(government.getName());
        gdFound.setText(government.getGovDateFounded());
        gdEnd.setText(government.getGovDateEnded());
        gType.setText(government.getGovType());
        gDes.setText(government.getDescription());
    }

    /*public void setHyperLink(Hyperlink itemToHyperlink){

        itemToHyperlink.setOnAction(event -> {
            ObservableList<Entity> govCheck = FXCollections.observableArrayList(governments);
            ObservableList<Entity> chrCheck = FXCollections.observableArrayList(characters);
            ObservableList<Entity> monuCheck = FXCollections.observableArrayList(monument);
            ObservableList<Entity> fesCheck = FXCollections.observableArrayList(festival);
            ObservableList<Entity> evnCheck = FXCollections.observableArrayList(events);
            if (checkEntityInList(itemToHyperlink,chrCheck)) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPane("figureScene",itemToHyperlink);
                mainBorder.setCenter(view);
                searchBar.setVisible(false);
                backButton.setVisible(true);
            }
            if (checkEntityInList(itemToHyperlink, govCheck)) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPane("governmentScene",itemToHyperlink);
                mainBorder.setCenter(view);
                searchBar.setVisible(false);
                backButton.setVisible(true);

            }
            if (checkEntityInList(itemToHyperlink,monuCheck)) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPane("placeScene",itemToHyperlink);
                mainBorder.setCenter(view);
                searchBar.setVisible(false);
                backButton.setVisible(true);
            }
            if (checkEntityInList(itemToHyperlink,fesCheck)) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPane("festivalScene",itemToHyperlink);
                mainBorder.setCenter(view);
                searchBar.setVisible(false);
                backButton.setVisible(true);
            }
            if (checkEntityInList(itemToHyperlink,evnCheck)) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPane("eventScene",itemToHyperlink);
                mainBorder.setCenter(view);
                searchBar.setVisible(false);
                backButton.setVisible(true);
            }
        });
    }*/
}
