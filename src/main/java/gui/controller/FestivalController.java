package gui.controller;


import entity.Festival;
import javafx.fxml.FXML;
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
    public Label fLoca;
    @FXML
    public ImageView fImage;
    public void setLabel(Festival festival) {
        fName.setText(festival.getName());
        if (festival.getDescription() == null || festival.getDescription().isEmpty()) {
            fDes.setText("Không rõ");
        } else {
            fDes.setText(festival.getDescription().replace("/n","/n/t"));
        }
        if (festival.getDate() == null || festival.getDate().isEmpty()) {
            fDate.setText("Không rõ");
        } else {
            fDate.setText(festival.getDate());
        }
        if (festival.getLocation() == null || festival.getLocation().isEmpty()) {
            fLoca.setText("Không rõ");
        } else {
            fLoca.setText(festival.getLocation());
        }
        if (festival.getSource() == null || festival.getSource().isEmpty()) {
            fSrc.setText("Không rõ");
        } else {
            String[] webList = festival.getSource().split(", ");

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
            fSrc.setGraphic(textFlow);
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
