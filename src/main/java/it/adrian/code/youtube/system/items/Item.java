package it.adrian.code.youtube.system.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;
    public float hover;
    public float lastHover;

    public Id getId() {
        return id;
    }
    public Snippet getSnippet() {
        return snippet;
    }
}