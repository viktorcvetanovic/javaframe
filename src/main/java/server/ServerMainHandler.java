package server;

import config.Config;
import server.thread.WelcomeThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMainHandler {
    private ServerSocket serverSocket;
    private List<Socket> connectedSocket;

    private ServerMainHandler() {
        this.serverSocket = Config.getServerSocket();
        connectedSocket = new ArrayList<>();
    }

    private void config() throws IOException {
        while (true) {
            var socket = this.serverSocket.accept();
            connectedSocket.add(socket);
            new Thread(new WelcomeThread(socket)).start();
        }
    }

    public static void run() {
        try {
            new ServerMainHandler().config();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

