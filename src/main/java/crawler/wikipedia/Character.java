package crawler.wikipedia;
import crawler.Crawler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import crawler.wikipedia.Wikipedia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
                Elements tables = document.select("table");
                for(Element table: tables) {
                    Elements rows = table.select("tbody > tr[style *= height:50px;]");
                    for (Element row : rows) {
                        characterUrl.add(row.select("td").get(1).getElementsByTag("a").attr("href"));

                    }
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
                if(key.contains("Hậu duệ") ){
                    Elements hd= e.select("td").select("a");
                    value="";
                    for(Element e1:hd){
                        value+=e1.text()+",";
                    }
                    value=value.substring(0,value.length()-1);
                    int indexStart=value.indexOf("[");
                    int indexEnd=value.indexOf("]");
                    while (indexStart>=0) {
                        value = value.substring(0, indexStart) + value.substring(indexEnd+1, value.length());
                        indexStart=value.indexOf("[");
                        indexEnd=value.indexOf("]");
                    }
                    entity.addProperty("Hậu duệ",value);
                }
                else if(!key.equals("") && !key.equals("Thông tin chung")){
                    int indexStart=value.indexOf("[");
                    int indexEnd=value.indexOf("]");
                    while (indexStart>=0) {
                        value = value.substring(0, indexStart) + value.substring(indexEnd+1, value.length());
                        indexStart=value.indexOf("[");
                        indexEnd=value.indexOf("]");
                    }
                    entity.addProperty(key,value);}
            }
        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }
        return entity;
    }
    public static void main(String[] args){
        new Character();
    }
}
