package crawler.nguoikesu;

import crawler.Crawler;
public abstract class NguoiKeSu extends Crawler {
    private static final String BASE_URL = "https://nguoikesu.com";
    private static final String TITLE = "Người Kể Sử";

    protected void init() {
        setTitle(TITLE);
        setBaseUrl(BASE_URL);
    }
}
