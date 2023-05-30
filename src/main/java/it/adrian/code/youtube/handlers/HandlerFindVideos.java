package it.adrian.code.youtube.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import it.adrian.code.youtube.manage.Manager;
import it.adrian.code.youtube.utils.Querys;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HandlerFindVideos implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        String query = t.getRequestURI().getQuery();
        Map<String, String> queryParams = Querys.parseQuery(query);
        String criteria = queryParams.get("criteria");
        String responseJson = Manager.getJsonByTitleSearching(criteria);
        Headers headers = t.getResponseHeaders();
        headers.set("Content-Type", "application/json");
        t.sendResponseHeaders(200, responseJson.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(responseJson.getBytes());
        os.close();
    }
}