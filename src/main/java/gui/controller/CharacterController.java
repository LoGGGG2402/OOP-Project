package gui.controller;

import entity.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;

public class CharacterController {
    @FXML
    public Label chrName;
    @FXML
    public Label chrDOB;
    @FXML
    public Label chrRel;
    @FXML
    public Label chrDes;
    @FXML
    public Label chrChild;
    @FXML
    public Label chrWife;
    @FXML
    public Label chrDad;
    @FXML
    public Label chrMom;
    @FXML
    public Label chrPos;
    @FXML
    public Label chrSrc;
    @FXML
    public ImageView chrImage;


    public void setLabel(Character character) {
        chrName.setText(character.getName());
        if (character.getDescription() == null || character.getDescription().isEmpty()) {
            chrDes.setText("Không rõ");
        } else {
            chrDes.setText(character.getDescription().replace("/n","/n/t"));
        }
        if (character.getDob() == null || character.getDob().isEmpty()) {
            chrDOB.setText("Không rõ");
        } else {
            chrDOB.setText(character.getDob());
        }
        if (character.getPosition() == null || character.getPosition().isEmpty()) {
            chrPos.setText("Không rõ");
        } else {
            chrPos.setText(character.getPosition());
        }
        if (character.getSource() == null || character.getSource().isEmpty()) {
            chrSrc.setText("Không rõ");
        } else {
            String[] webList = character.getSource().split(", ");

            TextFlow textFlow = new TextFlow();
            for (int i = 0; i < webList.length; i++) {
                String website = webList[i];
                Hyperlink hyperlink = new Hyperlink(website);
                hyperlink.setFont(new Font(15));
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
            chrSrc.setGraphic(textFlow);
        }

        if (character.getPartner() == null || character.getPartner().isEmpty()) {
            chrWife.setText("Không rõ");
            chrWife.setStyle("-fx-font-weight: normal;");
        } else {
            chrWife.setText(character.getPartner());
            chrWife.setStyle("-fx-font-weight: bold;");
        }
        if (character.getMom() == null || character.getMom().isEmpty()) {
            chrMom.setText("Không rõ");
            chrMom.setStyle("-fx-font-weight: normal;");
        } else {
            chrMom.setText(character.getMom());
            chrMom.setStyle("-fx-font-weight: bold;");
        }
        if (character.getDad() == null || character.getDad().isEmpty()) {
            chrDad.setText("Không rõ");
            chrDad.setStyle("-fx-font-weight: normal;");
        } else {
            chrDad.setText(character.getDad());
            chrDad.setStyle("-fx-font-weight: bold;");
        }
        if (character.getChildren() == null || character.getChildren().isEmpty()) {
            chrChild.setText("Không rõ");
            chrChild.setStyle("-fx-font-weight: normal;");
        } else {
            chrChild.setText(character.getChildren());
            chrChild.setStyle("-fx-font-weight: bold;");
        }
        if (character.getImage()!=null) {
            String imagePath = "C:\\Users\\LamPhuss\\IdeaProjects\\OOP-Project\\" + character.getImage();
            try {
                Image image = new Image(new FileInputStream(imagePath));
                chrImage.setImage(image);
            } catch (FileNotFoundException e) {
            }
        }
    }
}
