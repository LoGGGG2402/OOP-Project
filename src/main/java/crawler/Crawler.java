package crawler;

public abstract class Crawler {
    protected Crawler(){
        crawl();
    }
    protected abstract void crawl();
}
