package org.server;

import org.server.Http.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Server httpServer = Server.createHttpServer(8888);
    }
}
