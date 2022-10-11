package thread.throughput;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CountWordHandler implements HttpHandler {
    private String text;
    public CountWordHandler(String text) {
        this.text = text;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] keyValue = query.split("=");

        String action = keyValue[0];
        if( ! action.equals("word")) {
            exchange.sendResponseHeaders(400, 0);
        }
        String value = keyValue[1];
        long count = wordCount(value);
        byte[] response = Long.toString(count).getBytes();
        exchange.sendResponseHeaders(200, response.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response);
        outputStream.close();
    }

    private long wordCount(String word) {
        int index = text.indexOf(word);
        long count = 0;
        while(index >= 0) {
            ++count;
            ++index;
            index = text.indexOf(word, index);
        }
        return count;
    }
}
