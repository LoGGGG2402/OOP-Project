package crawler.ditich;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class Monument extends Ditich{
    @Override
    protected Vector<String> getUrl() {
        Vector<String> figureUrl = new Vector<>();
        String urlConnect = getBaseUrl() + "/FrontEnd/DiTich?cpage=*/*&rpage=&corder=&torder=&tpage=*/*&TEN=&LA_CDT=&LOAI_HINH_XEP_HANG=&XEP_HANG=&DIA_DANH=&TEN_HANG_MUC=&HM_LOAI_HINH_XEP_HANG=&HM_XEP_HANG=&TEN_HIEN_VAT=&HV_LOAI=&namtubo=";
        try {
            HttpURLConnection connect = (HttpURLConnection) new URI(urlConnect.replace("*/*", String.valueOf(1))).toURL().openConnection();
            connect.setRequestMethod("GET");
            connect.setReadTimeout(5);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        int page = 1;
        while (true) {
            try {
                System.out.print("\rCrawling1 " + figureUrl.size() + " urls");
                System.out.print("\rCrawling1 " + urlConnect.replace("*/*", String.valueOf(page)));
                HttpURLConnection connection = (HttpURLConnection) new URI(urlConnect.replace("*/*", String.valueOf(page))).toURL().openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", urlConnect);

                // Get figure url and name
                document.select("#div_ditich > section > a").forEach(element -> figureUrl.add(element.select("a").attr("href")));

                // Get next page url
                String nextPageUrl = document.select("#formDiTichSearchModel > div > section.hl__filter-directory__results > div > div:nth-child(1) > a:nth-child(4)").attr("class");
                System.out.print("\rCrawling1 " + figureUrl.size() + " urls");
                if (nextPageUrl.equals("right disabled")) {
                    break;
                }
                page++;
            } catch (IOException | URISyntaxException e) {
                System.out.println("Error: " + urlConnect.replace("*/*", String.valueOf(page)));
            }
        }
        return figureUrl;
    }
    @Override
    protected JsonObject getEntity(String url) {
        JsonObject entity = new JsonObject();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);

            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

            // Get name
            String name = document.select("#block-harvard-content > article > div > section > div > div.hl__library-info__features > section > h2").text();
            entity.addProperty("name", name);

            // Get info
            JsonObject properties = new JsonObject();
            document.select("div.hl__illustrated-list__list-item > span").forEach(element -> {
                if (element.text().contains(":")) {
                    String[] info = element.text().split(":");
                    for (int i = 0; i < info.length - 1; i++) {
                        if (i % 2 == 0 && i + 1 < info.length) {
                            properties.addProperty(info[i].trim(), info[i + 1].trim());
                        }
                    }
                }
            });
            properties.addProperty("địa chỉ", document.select("#block-harvard-content > article > div > section > div > div.hl__library-info__sidebar > div:nth-child(1) > section > div > div > div.hl__contact-info__address > span").text());

            // get image
            if(document.select("#block-harvard-content > article > div > section > div > div.hl__library-info__hours > section > div > img").first() != null){
                String image =  document.select("#block-harvard-content > article > div > section > div > div.hl__library-info__hours > section > div > img").first().attr("src");
                image = image.replace("\\", "/");
                image = URLEncoder.encode(image, StandardCharsets.UTF_8).replace("%3A", ":").replace("%2F", "/").replace("%25", "%").replace("%28", "(").replace("%29", ")").replace("+", "%20");
                entity.addProperty("image", getImg(image.contains("http") ? image : (getBaseUrl() + image)));
            }

            entity.add("properties", properties);
            entity.addProperty("source", url);
        } catch (IOException | URISyntaxException e) {
            return null;
        }

        if (entity.get("name") == null && entity.get("moreInfo") == null && entity.get("description") == null)
            return null;

        System.out.print("\rCrawling " + entity.get("name").getAsString());

        return entity;
    }

    public static void main(String[] args) {
        new Monument();
    }
}
