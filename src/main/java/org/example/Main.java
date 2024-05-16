package org.example;

import org.example.Http.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Server httpServer = Server.createHttpServer(8888);
    }
}
