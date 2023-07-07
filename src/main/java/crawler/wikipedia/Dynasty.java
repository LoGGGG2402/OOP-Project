package crawler.wikipedia;

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
import java.util.*;

public class Dynasty extends Wikipedia {
    protected Vector<String> getUrl() {
        Vector<String> dynastyUrl = new Vector<>();
        String urlConnect = getBaseUrl()+"/wiki" + "/Lịch_sử_Việt_Nam";
        while (true) {
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
                dynastyUrl.remove(dynastyUrl.size()-1);
                return dynastyUrl;


            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        JsonObject properties = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
            Elements table=document.getElementsByClass("infobox").select("tbody>tr");

            List<String> attributes=new ArrayList<>() ;
            try {
                entity.addProperty("name",table.get(0).text());
            } catch (IndexOutOfBoundsException e) {
                entity.addProperty("name",document.select("#firstHeading > span").text());
            }
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
                    properties.addProperty(key,value);
                }
                else if(row.select("th").text().equals("Hoàng đế")){
                    row=row.nextElementSibling().nextElementSibling();
                    List<String> kings=new ArrayList<>();
                    while (!row.nextElementSibling().select("th").text().equals("Lịch sử")) {
                        kings.add(row.select("td").text());
                        row = row.nextElementSibling();
                    }
                    properties.addProperty("Hoàng đế",kings.toString().substring(1, kings.toString().length()-1));

                }
            }
            entity.add("properties",properties);
            entity.addProperty("description",document.select("#mw-content-text > div.mw-parser-output > p").first().text());

            // Get image
            if(document.select("#content").select("img").first() != null){
                String image = document.select("#content").select("img").first().attr("src");
                entity.addProperty("image", getImg(image.contains("http")?image:"https:"+image));
            }

            // get allDocuments
            String allDocuments = document.select("#content").text();
            entity.addProperty("allDocument", allDocuments);
        } catch(IOException | URISyntaxException e){
            e.printStackTrace();
            return null;
        }
        return entity;
    }
    public static void main(String[] args){
        new Dynasty();
    }

    @Override
    protected JsonArray getEntities() {
        Vector<String> characterUrl = getUrl();

        JsonArray character = new JsonArray();
        characterUrl.forEach(url -> {
            JsonObject entity;
            if(url.contains(getBaseUrl())){
                entity = getEntity(url);
            }else {
                entity = getEntity(url.charAt(0)=='/'?getBaseUrl()+url:getBaseUrl()+"/" +url);
            }
            if (entity != null){
                entity.addProperty("source", getBaseUrl());
                character.add(entity);}
        });
        return character;
    }
}