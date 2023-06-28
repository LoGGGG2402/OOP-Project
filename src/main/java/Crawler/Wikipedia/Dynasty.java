package Crawler.Wikipedia;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Dynasty extends Wikipedia {
    @Override
    protected Vector<String> getUrl() {
        Vector<String> dynastyUrl = new Vector<>();
        String urlConnect = baseUrl + URLEncoder.encode("/Lịch_sử_Việt_Nam", java.nio.charset.StandardCharsets.UTF_8);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);
            // Get character url
            Elements table=document.getElementsByClass("table toccolours").select("tbody").get(1).select("td");
            for(Element row:table){
                dynastyUrl.add(row.select("a").attr("href"));
            }
            System.out.println(table);
            dynastyUrl.remove(dynastyUrl.size()-1);

            return dynastyUrl;

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(baseUrl + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", baseUrl + url);
            Elements table=document.getElementsByClass("infobox").select("tbody>tr");
            List<String> attributes=new ArrayList<>() ;
            attributes.add("Vị thế");
            attributes.add("Thủ đô");
            attributes.add("Ngôn ngữ thông dụng");
            attributes.add("Tôn giáo chính");
            attributes.add("Chính phủ");
            for (Element row:table) {
                if (attributes.contains(row.select("th").text())) {
                    String key =row.select("th").text();
                    String value=row.select("td").text();
                    int indexStart=value.indexOf("[");
                    int indexEnd=value.indexOf("]");
                    while (indexStart>=0) {
                        value = value.substring(0, indexStart) + value.substring(indexEnd+1);
                        indexStart=value.indexOf("[");
                        indexEnd=value.indexOf("]");
                    }
                    entity.addProperty(key,value);
                }
                else if(row.select("th").text().equals("Hoàng đế")){
                    row= Objects.requireNonNull(row.nextElementSibling()).nextElementSibling();
                    List<String> kings=new ArrayList<>();
                    while (true) {
                        assert row != null;
                        if (Objects.requireNonNull(row.nextElementSibling()).select("th").text().equals("Lịch sử")) break;
                        kings.add(row.select("td").text());
                        row = row.nextElementSibling();
                    }
                    entity.addProperty("Hoàng đế",kings.toString().substring(1, kings.toString().length()-1));
                }
            }
        } catch(IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }
        return entity;
    }

    public static void main(String[] args) {
        new Dynasty();
    }
}
