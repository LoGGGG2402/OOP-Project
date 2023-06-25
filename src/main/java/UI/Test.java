package UI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test {
    public static void main(String[] args) {
        String jsonString = "{\"key1\": \"value1\", \"key2\": \"value2\"}";

        // Đọc JSON thành đối tượng Gson
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        // Thay đổi giá trị của khóa "key1"
        jsonObject.addProperty("key1", "new value");

        // Chuyển đổi lại thành chuỗi JSON
        String updatedJsonString = gson.toJson(jsonObject);
        System.out.println(updatedJsonString);
    }
}
