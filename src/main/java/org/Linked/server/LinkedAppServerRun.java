package org.Linked.server;

import org.Linked.server.Http.Server;

public class LinkedAppServerRun {
    public static void main(String[] args) throws Exception {
        Server httpServer = Server.createHttpServer(8080);
    }
}
