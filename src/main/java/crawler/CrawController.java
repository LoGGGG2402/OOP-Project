package crawler;

import crawler.disanvanhoa.Monument;

public class CrawController {
    private CrawController() {
    }
    public static void crawl() {
        // disanvanhoa
        new Monument();
        // nguoikesu
        new crawler.nguoikesu.Character();
        new crawler.nguoikesu.Event();
        new crawler.nguoikesu.Dynasty();
        new crawler.nguoikesu.Monument();
        // vansu
        new crawler.vansu.Character();
        new crawler.vansu.Dynasty();
        // wikipedia
        new crawler.wikipedia.Character();
        new crawler.wikipedia.Dynasty();
        new crawler.wikipedia.Event();
        new crawler.wikipedia.Festival();


    }

}
