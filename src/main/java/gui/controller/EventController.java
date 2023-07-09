package gui.controller;

import entity.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    @FXML
    public Label eName;
    @FXML
    public Label eTime;
    @FXML
    public Label eCommander;
    @FXML
    public Label eReason;
    @FXML
    public Label eResult;
    @FXML
    public Label eBelli;
    @FXML
    public Label eStrength;
    @FXML
    public Label eLoca;
    @FXML
    public Label eLoss;
    @FXML
    public ImageView eImage;
    @FXML
    public Label eRel;
    @FXML
    public Label eSrc;
    @FXML
    public Label eDes;

    public void setLabel(Event event) {
        eName.setText(event.getName());
        eDes.setText(event.getDescription().replace("/n", "/n/t"));

        if (event.getTime() == null) {
            eTime.setText("Không rõ");
        } else {
            eTime.setText(event.getTime());
        }

        if (event.getCommanders() == null) {
            eCommander.setText("Không rõ");
        } else {
            eCommander.setText(event.getCommanders().replace("|" ,"\n"));
        }
        if (event.getSource() == null) {
            eSrc.setText("Không rõ");
        } else {
            String[] webList = event.getSource().split(", ");

            TextFlow textFlow = new TextFlow();
            for (int i = 0; i < webList.length; i++) {
                String website = webList[i];
                Hyperlink hyperlink = new Hyperlink(website);
                hyperlink.setFont(new Font(14));
                String finalWebsite = website.replaceAll("\\s+", "");
                hyperlink.setOnAction(e -> {
                    try {
                        Desktop.getDesktop().browse(new URI(finalWebsite));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                });
                if (i > 0) {
                    textFlow.getChildren().add(new Text("; "));
                }
                textFlow.getChildren().add(hyperlink);
            }
            eSrc.setGraphic(textFlow);
        }

        if (event.getReason() == null) {
            eReason.setText("Không rõ");
        } else {
            eReason.setText(event.getReason());
        }
        if (event.getResult() == null) {
            eResult.setText("Không rõ");
        } else {
            eResult.setText(event.getResult());
        }
        if (event.getBelligerents() == null) {
            eBelli.setText("Không rõ");
        } else {
            eBelli.setText(event.getBelligerents().replace("|" ,"\n"));
        }
        if (event.getStrength() == null) {
            eStrength.setText("Không rõ");
        } else {
            eStrength.setText(event.getStrength());
        }
        if (event.getLocation() == null) {
            eLoca.setText("Không rõ");
        } else {
            eLoca.setText(event.getLocation());
        }
        if (event.getLosses() == null) {
            eLoss.setText("Không rõ");
        } else {
            eLoss.setText(event.getLosses().replace("|" ,"\n"));
        }
        if (event.getStrength() == null) {
            eStrength.setText("Không rõ");
        } else {
            eStrength.setText(event.getStrength().replace("|" ,"phe ta và phe địch gồm có"));
        }
        if (event.getImage() != null) {
            String imagePath = "C:\\Users\\LamPhuss\\IdeaProjects\\OOP-Project\\" + event.getImage();
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(imagePath));
                eImage.setImage(image);
            } catch (FileNotFoundException e) {
                //System.err.println("Image file not found: " + e.getMessage());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
