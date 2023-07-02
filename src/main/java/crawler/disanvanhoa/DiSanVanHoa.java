package crawler.disanvanhoa;

import crawler.Crawler;

public abstract class DiSanVanHoa extends Crawler{
    protected static final String BASE_URL = "http://dsvh.gov.vn";
    protected static final String TITLE = "Di Sản Văn Hóa";

    @Override
    protected void init() {
        setTitle(TITLE);
        setBaseUrl(BASE_URL);
    }
}
