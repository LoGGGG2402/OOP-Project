package gui.controller;

import entity.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.File;
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
    public Label chrSource;
    @FXML
    public ImageView chrImage;


    public void setLabel(Character character) {
        chrName.setText(character.getName());
        chrDes.setText(character.getDescription().replace("/n","/n/t"));
        chrDOB.setText(character.getDob());
        if (character.getPosition() == null) {
            chrPos.setText("Không rõ");
        } else {
            chrPos.setText(character.getPosition());
        }
        if (character.getSource() == null) {
            chrSource.setText("Không rõ");
        } else {
            Hyperlink hyperlink = new Hyperlink(character.getSource());
            hyperlink.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(character.getSource()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });
            hyperlink.setFont(new Font(14));
            chrSource.setGraphic(hyperlink);
        }

        if (character.getPartner() == null) {
            chrWife.setText("Không rõ");
        } else {
            chrWife.setText(character.getPartner());
        }
        if (character.getMom() == null) {
            chrMom.setText("Không rõ");
        } else {
            chrMom.setText(character.getMom());
        }
        if (character.getDad() == null) {
            chrDad.setText("Không rõ");
        } else {
            chrDad.setText(character.getDad());
        }
        if (character.getChildren() == null) {
            chrChild.setText("Không rõ");
        } else {
            chrChild.setText(character.getChildren());
        }
        if (character.getImage()!=null) {
            String imagePath = "C:\\Users\\LamPhuss\\IdeaProjects\\OOP-Project\\" + character.getImage();
            try {
                Image image = new Image(new FileInputStream(imagePath));
                chrImage.setImage(image);
            } catch (FileNotFoundException e) {
                //System.err.println("Image file not found: " + e.getMessage());
            }
        }
    }
}
