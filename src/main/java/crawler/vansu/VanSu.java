package crawler.vansu;

import crawler.Crawler;

public abstract class VanSu extends Crawler {
    protected static final String BASE_URL = "https://vansu.vn";
    protected static final String TITLE = "Văn Sử";
    @Override
    protected void init() {
        setTitle(TITLE);
        setBaseUrl(BASE_URL);
    }

}
