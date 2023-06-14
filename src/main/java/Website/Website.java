package Website;

import java.util.Vector;

public class Website {
    private String url;
    private String title;
    private Vector<String> keywords = new Vector<String>();
    public Website(String url, String title) {
        this.url = url;
        this.title = title;
    }
    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }
}
