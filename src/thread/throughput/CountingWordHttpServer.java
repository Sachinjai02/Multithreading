package thread.throughput;

import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountingWordHttpServer {
    private static final String INPUT_FILE = "resources/throughput/war_and_peace.txt";
    private static final Integer NUM_THREADS = 4;
    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    private static void startServer(String text) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/search", new CountWordHandler(text));
        Executor executor = Executors.newFixedThreadPool(NUM_THREADS);
        server.setExecutor(executor);
        server.start();
    }
}
