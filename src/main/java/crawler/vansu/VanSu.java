package crawler.vansu;

import crawler.Crawler;

public abstract class VanSu extends Crawler {
    private static final String BASE_URL = "https://vansu.vn";
    private static final String TITLE = "Văn Sử";
    @Override
    protected void init() {
        setTitle(TITLE);
        setBaseUrl(BASE_URL);
    }

}
