package Crawler.Wikipedia;
import Crawler.Crawler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;
public class Character extends Wikipedia {

    protected Vector<String> getUrl() {
        Vector<String> characterUrl = new Vector<>();
        String baseUrl = "https://vi.wikipedia.org/wiki";
        String urlConnect = baseUrl + "/Vua_Việt_Nam";
        while (true) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);
                // Get character url
                Elements tables = document.select("table[cellpadding = 0] tbody");
                for(Element table: tables){
                    Elements rows=table.select("tr[style = height:50px;]");
                    for(Element row : rows)
                        characterUrl.add(row.select("td").get(1).select("a").get(0).attr("href"));
                }
                return characterUrl;


            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected JsonObject getEntity(String url) {
        String baseUrl = "https://vi.wikipedia.org";
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(baseUrl+url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
            Elements table = document.getElementsByClass("infobox").select("[style=width:22em]>tbody>tr");
            entity.addProperty("Tên",table.get(0).text());
            table.remove(0);
            for(Element e:table) {
                String key = e.select("th").text();
                String value=e.select("td").text();
                if(!key.equals("") && !key.equals("Thông tin chung")){
                    entity.addProperty(key,value);}
            }
        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }
        return entity;
    }
}
