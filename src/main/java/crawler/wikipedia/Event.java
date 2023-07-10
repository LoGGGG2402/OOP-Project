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
import java.util.Vector;

public class Event extends Wikipedia {
    protected JsonArray getEntities() {
        String url = "/Niên_biểu_lịch_sử_Việt_Nam";
        JsonArray entities = new JsonArray();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(getBaseUrl()+"/wiki" + url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", getBaseUrl()+"/wiki"  + url);
            Elements rows=document.select("dl,p");
            for(int i=6;i<rows.size()-1;i++) {
                if (rows.get(i).tag().toString().equals("p")) {
                    if (!rows.get(i+1).tag().toString().equals("dl")) {
                        JsonObject entity = new JsonObject();
                        JsonObject properties= new JsonObject();
                        properties.addProperty("Thời gian", rows.get(i).select("b").text());
                        entity.addProperty("name", rows.get(i).text().replace(rows.get(i).select("b").text(), ""));
                        entity.add("properties",properties);

                        JsonObject moreInfo = getMoreInfo(rows.get(i).text().replace(rows.get(i).select("b").text(), ""));
                        if (moreInfo.has("description"))
                            entity.addProperty("description", moreInfo.get("description").getAsString());

                        if (moreInfo.has("image"))
                            entity.addProperty("image", moreInfo.get("image").getAsString());

                        if (moreInfo.has("allDocument"))
                            entity.addProperty("allDocument", moreInfo.get("allDocument").getAsString());
                        if (moreInfo.has("url"))
                            entity.addProperty("source", moreInfo.get("url").getAsString());
                        else
                            entity.addProperty("source", getBaseUrl());
                        entities.add(entity);
                    }
                    else {
                        String year = rows.get(i).text();
                        Element nextRow = rows.get(i+1);
                        Elements internalRows = nextRow.select("dd");
                        for (Element row1 : internalRows) {
                            JsonObject entity = new JsonObject();
                            JsonObject properties= new JsonObject();
                            properties.addProperty("Thời gian", row1.select("b").text() + " năm " + year);
                            entity.addProperty("name", row1.text().replace(row1.select("b").text(), ""));
                            entity.add("properties",properties);
                            JsonObject moreInfo = getMoreInfo(rows.get(i).text().replace(rows.get(i).select("b").text(), ""));
                            if (moreInfo.has("description"))
                                entity.addProperty("description", moreInfo.get("description").getAsString());

                            if (moreInfo.has("image"))
                                entity.addProperty("image", moreInfo.get("image").getAsString());

                            if (moreInfo.has("allDocument"))
                                entity.addProperty("allDocument", moreInfo.get("allDocument").getAsString());

                            if (moreInfo.has("url"))
                                entity.addProperty("source", moreInfo.get("url").getAsString());
                            else
                                entity.addProperty("source", getBaseUrl());
                            entities.add(entity);

                        }
                    }
                }
            }
        } catch(IOException | URISyntaxException | NullPointerException e){
            throw new RuntimeException(e);
        }
        return entities;
    }
    public JsonObject getMoreInfo(String name){
        JsonObject moreInfo = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(getBaseUrl()+"/wiki/" + name.replace(" ","_")).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", getBaseUrl()+"/wiki/"  + name.replace(" ","_"));

            moreInfo.addProperty("description", document.select("#mw-content-text > div.mw-parser-output > p").first().text());
            if(document.select("#content").select("img").first() != null){
                String image = document.select("#content").select("img").first().attr("src");
                moreInfo.addProperty("image", getImg(image.contains("http")?image:"https:"+image));
            }

            // get allDocuments
            String allDocuments = document.select("#content").text();
            moreInfo.addProperty("allDocument", allDocuments);

            // get url
            moreInfo.addProperty("url", getBaseUrl()+"/wiki/" + name.replace(" ","_"));
        } catch(IOException | URISyntaxException | NullPointerException e){
            System.out.println("Can't find :"+e.getMessage());
        }
        return moreInfo;
    }

    @Override
    protected Vector<String> getUrl() {
        return new  Vector<> ();
    }

    @Override
    protected JsonObject getEntity(String url) {
        return null;
    }

    public static void main(String[] args){
        new Event();
    }
}
