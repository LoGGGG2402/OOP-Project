package Crawler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public abstract class Crawler {
    protected String baseUrl;
    public Crawler(){
        crawl();
    }
    protected abstract void crawl();
}
