package it.adrian.code.youtube.manage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;

public class Manager {
    private static final YouTube youTube = new YouTube();
    public static String getJsonByTitleSearching(String searchingTitle){
        return "{\"data\": " + getJsonArrayOfYourSearch(searchingTitle) + "}";
    }
    private static JsonArray getJsonArrayOfYourSearch(String title){
        youTube.doSearch(title);
        JsonArray jsonArray = new JsonArray();
        int i = 0;
        for (String tit : youTube.getSearchResult().keySet()) {
            String id = youTube.getSearchResult().get(tit);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("index", i);
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("title", tit);
            jsonArray.add(jsonObject);
            i++;
        }
        return jsonArray;
    }

    public static File downloadByIndex(int index){
        return youTube.getByIndex(index);
    }

    public static File downloadByVidId(String videoID){
        return youTube.downloadFromVidID(videoID);
    }
}
