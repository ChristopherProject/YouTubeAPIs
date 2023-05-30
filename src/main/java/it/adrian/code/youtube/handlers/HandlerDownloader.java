package it.adrian.code.youtube.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import it.adrian.code.youtube.manage.Manager;
import it.adrian.code.youtube.utils.Querys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HandlerDownloader implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        String query = t.getRequestURI().getQuery();
        Map<String, String> queryParams = Querys.parseQuery(query);
        if (query.contains("index") || query.contains("VideoID")) {
            if (query.contains("index")) {
                String index = queryParams.get("index");
                t.getResponseHeaders().set("Content-Type", "audio/mpeg");
                File file = Manager.downloadByIndex(Integer.parseInt(index));
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] mp3Data = new byte[(int) file.length()];
                    fileInputStream.read(mp3Data);
                    t.sendResponseHeaders(200, mp3Data.length);
                    OutputStream outputStream = t.getResponseBody();
                    outputStream.write(mp3Data);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (query.contains("VideoID")) {
                String VideoID = queryParams.get("VideoID");
                t.getResponseHeaders().set("Content-Type", "audio/mpeg");
                File file = Manager.downloadByVidId(VideoID);
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] mp3Data = new byte[(int) file.length()];
                    fileInputStream.read(mp3Data);
                    t.sendResponseHeaders(200, mp3Data.length);
                    OutputStream outputStream = t.getResponseBody();
                    outputStream.write(mp3Data);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}