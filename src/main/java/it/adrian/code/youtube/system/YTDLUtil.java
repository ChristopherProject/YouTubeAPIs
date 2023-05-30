package it.adrian.code.youtube.system;

import com.google.gson.Gson;
import it.adrian.code.youtube.system.items.Item;
import it.adrian.code.youtube.system.items.Search;
import it.adrian.code.youtube.system.obj.AudioObj;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public final class YTDLUtil {

    public static final ArrayList<AudioObj> items = new ArrayList<>();
    private static final String API_KEY = "AIzaSyBQJoDZKMI-4-Iy1AqHkBksTbpCvhp-IWw";
    private static final String BASE_URL = "https://api.vevioz.com/api/button/mp3/";
    private static Search currentSearch;

    public  static void makeSearch(String SongName, int maxResults) {
        if (!items.isEmpty()) items.clear();
        Gson gson = new Gson();
        Search request = gson.fromJson(visitSite("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=" + maxResults + "&q=" + SongName.replace(" ", "%20") + "&type=video&key=" + API_KEY), Search.class);
        currentSearch = request;
        for (Item videos : currentSearch.getItems()) {
           items.add(new AudioObj(videos.getSnippet().getTitle(), videos.getId().getVideoId()));
        }
    }


    private static String visitSite(String urly) {
        ArrayList<String> lines = new ArrayList<String>();
        StringBuilder stuff = new StringBuilder();
        URL url;
        try {
            String line;
            url = new URL(urly);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception ignored) {}
        for (String s : lines) {
            stuff.append(s);
        }
        return stuff.toString();
    }


    public static void downloadFileFromYT(String vidID, String Path) {
        new Thread(() -> {
            final File target = new File(Path);
            if (target.exists()) {
                System.out.println("Giá presente in memoria");
                return;
            }
            try {
                final URL ugly = new URL(getDonwloadURL(vidID));
                final HttpURLConnection connection = (HttpURLConnection) ugly.openConnection();
                connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == 200) {
                    InputStream is = connection.getInputStream();
                    OutputStream out = Files.newOutputStream(target.toPath());
                    byte[] buffer = new byte[79096];
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    System.out.println("saved in " + target.getPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static File downloadFileFromYTWithoutStoring(String vidID) {
        final File target;
        try {
            target = File.createTempFile("youtube", ".mp3");
            final URL ugly = new URL(getDonwloadURL(vidID));
            final HttpURLConnection connection = (HttpURLConnection) ugly.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                try (InputStream is = connection.getInputStream();
                     OutputStream out = Files.newOutputStream(target.toPath())) {
                    byte[] buffer = new byte[79096];
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                }
                return target;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static String getDonwloadURL(String videoID) {
        return extractUrls(getWebPabeSource(BASE_URL + videoID)).get(4);
    }

    private static List<String> extractUrls( String text) {
        List<String> containedUrls = new ArrayList<>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
        }
        return containedUrls;
    }

    private static String getWebPabeSource(String sURL) {
        final StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(sURL);
            URLConnection urlCon = url.openConnection();
            urlCon.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader in = null;
            if (urlCon.getHeaderField("Content-Encoding") != null && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream())));
            } else {
                in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            }
            String inputLine;
            while ((inputLine = in.readLine()) != null) sb.append(inputLine);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}