package com.app.coin_dispenser;

import com.app.coin_dispenser.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start(8080);
    }
}
