package it.adrian.code.youtube.system.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Search {
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    public List<Item> getItems() {
        return items;
    }
}