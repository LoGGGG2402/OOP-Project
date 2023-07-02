package crawler.ditich;

import crawler.Crawler;

public abstract class Ditich extends Crawler{
    private static final String BASE_URL = "http://ditich.vn";
    private static final String TITLE = "Di Tích";

    protected void init() {
        setTitle(TITLE);
        setBaseUrl(BASE_URL);
    }

}
