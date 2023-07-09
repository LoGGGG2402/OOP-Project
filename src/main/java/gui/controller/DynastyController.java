package gui.controller;

import entity.Dynasty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

public class DynastyController  {
    @FXML
    public Label dName;
    @FXML
    public Label dFounder;
    @FXML
    public Label dCapital;
    @FXML
    public Label dKings;
    @FXML
    public Label dRel;
    @FXML
    public Label dDes;
    @FXML
    public Label dSrc;
    @FXML
    public Label dLanguage;
    @FXML
    public ImageView dImage;


    public void setLabel(Dynasty dynasty) throws IOException {
        dName.setText(dynasty.getName());
        if (dynasty.getDescription() == null || dynasty.getDescription().isEmpty()) {
            dDes.setText("Không rõ");
        } else {
            dDes.setText(dynasty.getDescription().replace("/n", "/n/t"));
        }
        if (dynasty.getFounder() == null || dynasty.getFounder().isEmpty()) {
            dFounder.setText("Không rõ");
            dFounder.setStyle("-fx-font-weight: normal;");
        } else {
            dFounder.setText(dynasty.getFounder());
            dFounder.setStyle("-fx-font-weight: bold;");
        }
        if (dynasty.getSource() == null || dynasty.getSource().isEmpty()) {
            dSrc.setText("Không rõ");
        } else {
            String[] webList = dynasty.getSource().split(", ");

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
            dSrc.setGraphic(textFlow);
        }

        if (dynasty.getCapital() == null || dynasty.getCapital().isEmpty()) {
            dCapital.setText("Không rõ");
        } else {
            dCapital.setText(dynasty.getCapital());
        }
        if (dynasty.getLanguage() == null || dynasty.getLanguage().isEmpty()) {
            dLanguage.setText("Không rõ");
        } else {
            dLanguage.setText(dynasty.getLanguage());
        }
        if (dynasty.getKings().size() == 0) {
            dKings.setText("Không rõ");
        } else {
            String kings = String.join(", ", dynasty.getKings());
            dKings.setText(kings);
        }

        if (dynasty.getImage()!=null) {
            String imagePath = "C:\\Users\\LamPhuss\\IdeaProjects\\OOP-Project\\" + dynasty.getImage();
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(imagePath));
                dImage.setImage(image);
            } catch (FileNotFoundException e) {
            }
        }
    }


}
