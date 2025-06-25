package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public abstract class BaseController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestHeaders().getFirst("Token") == null) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
            httpExchange.close();
            return;
        }
        try {
            switch (httpExchange.getRequestMethod()) {
                case "GET" -> handleGet(httpExchange);
                case "POST" -> handlePost(httpExchange);
                case "PUT" -> handlePut(httpExchange);
                case "DELETE" -> handleDelete(httpExchange);
                default -> handleDefault(httpExchange);
            }
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(500, 0);
            httpExchange.close();
        }
        httpExchange.close();
    }

    protected abstract void handleGet(HttpExchange httpExchange);
    protected abstract void handlePost(HttpExchange httpExchange);
    protected abstract void handlePut(HttpExchange httpExchange);
    protected abstract void handleDelete(HttpExchange httpExchange);

    private void handleDefault(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
    }
}
