package crawler.disanvanhoa;

import crawler.Crawler;

public abstract class DiSanVanHoa extends Crawler{
    private static final String BASE_URL = "http://dsvh.gov.vn";
    private static final String TITLE = "Di Sản Văn Hóa";

    @Override
    protected void init() {
        setTitle(TITLE);
        setBaseUrl(BASE_URL);
    }
}
