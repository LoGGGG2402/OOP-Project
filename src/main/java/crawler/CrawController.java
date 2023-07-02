package crawler;

public class CrawController {
    private CrawController() {
    }
    public static void crawl() {
        // disanvanhoa
        new crawler.disanvanhoa.Monuments();
        // nguoikesu
        new crawler.nguoikesu.Character();
        new crawler.nguoikesu.Event();
        new crawler.nguoikesu.Dynasty();
        new crawler.nguoikesu.Monuments();
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
