package crawler;

public class CrawlController {
    private CrawlController() {
    }
    public static void crawl() {
        // disanvanhoa
//        new crawler.disanvanhoa.Monument();
//        //di tich
//        new crawler.ditich.Monument();
        // nguoikesu
//        new crawler.nguoikesu.Character();
        new crawler.nguoikesu.Event();
//        new crawler.nguoikesu.Dynasty();
//        new crawler.nguoikesu.Monument();
        // vansu
//        new crawler.vansu.Character();
//        new crawler.vansu.Dynasty();
        // wikipedia
//        new crawler.wikipedia.Character();
//        new crawler.wikipedia.Dynasty();
//        new crawler.wikipedia.Event();
//        new crawler.wikipedia.Festival();


    }

    public static void main(String[] args) {
        crawl();
    }

}
