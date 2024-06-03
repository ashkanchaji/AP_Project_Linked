package org.server.Http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.server.Controller.Controllers.ErrorController;
import org.server.Router.Router;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Server {
    private static Server httpServer = null;

    private HttpServer server;

    private Server(int port) throws IOException {
        createServer(port);
    }

    private void createServer(int port) throws IOException {
        InetAddress localAddress = InetAddress.getByName("127.0.0.1");
        this.server = HttpServer.create(new InetSocketAddress(localAddress, port), 0);

        Router.route(this);

        server.start();
    }

    public static synchronized Server createHttpServer(int port) throws IOException{
        if (httpServer == null){
            httpServer = new Server(port);
        }

        return httpServer;
    }

    public void handleValidRequests(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("GET") ||
                    exchange.getRequestMethod().equals("DELETE") ||
                    exchange.getRequestMethod().equals("POST") ||
                    exchange.getRequestMethod().equals("PUT")) {
                handler.handle(exchange);
            } else {
                ErrorController.methodNotSupported(exchange);
            }
        });
    }

}
