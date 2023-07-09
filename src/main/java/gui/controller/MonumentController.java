package gui.controller;

import entity.Monument;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MonumentController implements Initializable {
    @FXML
    public Label mName;
    @FXML
    public Label mType;
    @FXML
    public Label mSrc;
    @FXML
    public Label mDes;
    @FXML
    public Label mLoca;
    @FXML
    public ImageView mImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setLabel(Monument monument) {
        mName.setText(monument.getName().replace("*",""));
        if (monument.getDescription() == null || monument.getDescription().isEmpty()) {
            mDes.setText("Không rõ");
        } else {
            mDes.setText(monument.getDescription().replace("/n", "/n/t"));
        }
        if (monument.getLocation() == null || monument.getLocation().isEmpty()) {
            mLoca.setText("Không rõ");
        } else {
            mLoca.setText(monument.getLocation());
        }
        if (monument.getType() == null || monument.getLocation().isEmpty()) {
            mType.setText("Không rõ");
        } else {
            mType.setText(monument.getType());
        }
        if (monument.getSource() == null || monument.getSource().isEmpty()) {
            mSrc.setText("Không rõ");
        } else {
            String[] webList = monument.getSource().split(", ");

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
            mSrc.setGraphic(textFlow);
        }

    }

}
