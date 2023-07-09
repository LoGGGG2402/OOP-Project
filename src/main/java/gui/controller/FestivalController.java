package gui.controller;


import entity.Festival;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FestivalController {
    @FXML
    public Label fName;
    @FXML
    public Label fDate;
    @FXML
    public Label fRel;
    @FXML
    public Label fSrc;
    @FXML
    public Label fDes;
    @FXML
    public Label fPlace;
    @FXML
    public ImageView fImage;
    public void setLabel(Festival festival) {

        System.out.println(festival);
        fName.setText(festival.getName());
        fDes.setText(festival.getDescription().replace("/n","/n/t"));
        fDate.setText(festival.getDate());
        if (festival.getLocation() == null) {
            fPlace.setText("Không rõ");
        } else {
            fPlace.setText(festival.getLocation());
        }
        if (festival.getSource() == null) {
            fSrc.setText("Không rõ");
        } else {
            Hyperlink hyperlink = new Hyperlink(festival.getSource());
            hyperlink.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(festival.getSource()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });
            hyperlink.setFont(new Font(14));
            fSrc.setGraphic(hyperlink);
        }
        if (festival.getImage()!=null) {
            String imagePath = "C:\\Users\\LamPhuss\\IdeaProjects\\OOP-Project\\" + festival.getImage();
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(imagePath));
                fImage.setImage(image);
            } catch (FileNotFoundException e) {
                //System.err.println("Image file not found: " + e.getMessage());
            }
        }
    }
}
