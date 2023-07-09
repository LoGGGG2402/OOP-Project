package gui.controller;

import crawler.CrawlController;
import javafx.event.ActionEvent;

public class MenuController {

    public void crawlButtonAction(ActionEvent actionEvent) {
        CrawlController.crawl();
    }
}
