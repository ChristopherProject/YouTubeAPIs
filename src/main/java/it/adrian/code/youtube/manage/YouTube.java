package it.adrian.code.youtube.manage;

import it.adrian.code.youtube.system.YTDLUtil;
import it.adrian.code.youtube.system.obj.AudioObj;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class YouTube {

    private final ArrayList<AudioObj> items = YTDLUtil.items;

    public void doSearch( String video ) {
        if (!video.isEmpty()) {
            if (!items.isEmpty()) items.clear();
            YTDLUtil.makeSearch(video, 19);
        }
    }

    public HashMap<String, String> getSearchResult() {
        final HashMap<String, String> results = new HashMap<>();
        if (!items.isEmpty()) {
            items.forEach(result -> {
                results.put(result.getTitle(), result.getVidID());
            });
        }
        return results;
    }


    public File downloadFromVidID(String vidID){
        System.out.println("[DEBUG] [Manual] [Title] " + "video-" + vidID  + " [ID] -> " + vidID);
        return YTDLUtil.downloadFileFromYTWithoutStoring(vidID);
    }
  /*

        public  void downloadFromVidID(String vidID){
        YTDLUtil.downloadFileFromYT(vidID, normalizeFilePath("video-" + vidID + ".mp3").getPath());
        System.out.println("[DEBUG] [Manual] [Title] " + "video-" + vidID  + " [ID] -> " + vidID);
    }
    public void downloadByIndex( int index ) {

        if (!items.isEmpty()) {
            AudioObj itemMusic = items.get(index);
            YTDLUtil.downloadFileFromYTWithoutStoring(itemMusic.getVidID());//, normalizeFilePath(itemMusic.getTitle() + ".mp3").getPath());
            System.out.println("[DEBUG] [Title] " + itemMusic.getTitle() + " [ID] -> " + itemMusic.getVidID());
        }
        System.runFinalization();
        System.gc();
    }


   */


    public File getByIndex(int index) {
        if (!items.isEmpty() && index >= 0 && index < items.size()) {
            AudioObj itemMusic = items.get(index);
            return YTDLUtil.downloadFileFromYTWithoutStoring(itemMusic.getVidID());
        }
        return null;
    }

    private  File normalizeFilePath( String filename ) {
        filename = filename.replaceAll("[^a-zA-Z0-9.-]", "_");
        return new File(filename);
    }
}