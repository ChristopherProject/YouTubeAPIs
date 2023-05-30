package it.adrian.code.youtube;

import com.sun.net.httpserver.HttpServer;
import it.adrian.code.youtube.handlers.HandlerDownloader;
import it.adrian.code.youtube.handlers.HandlerFindVideos;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String... args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/v1/find", new HandlerFindVideos());//http://localhost:8080/api/v1/find
        server.createContext("/api/v1/download", new HandlerDownloader());//http://localhost:8080/api/v1/download
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
        System.runFinalization();
        System.gc();
    }
}