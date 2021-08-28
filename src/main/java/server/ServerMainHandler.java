package server;

import exception.server.InvalidServerConfigException;
import lombok.Data;
import server.thread.WelcomeThread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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

    @Data
    public static final class Config {
        private static int serverPort;
        private static InetAddress serverAdress;
        private static int serverBacklog;

        public static void setServerPort(int port) {
            serverPort = port;
        }


        public static void setBackLog(int backlog) {
            serverBacklog = backlog;
        }


        public static void setInetAdress(String inetAdress) {
            try {
                serverAdress = InetAddress.getByName(inetAdress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }


        public static void setInetAdress(InetAddress inetAdress) {
            serverAdress = inetAdress;
        }

        public static ServerSocket getServerSocket() {
            try {
                return new ServerSocket(serverPort, serverBacklog, serverAdress);
            } catch (IOException e) {
                throw new InvalidServerConfigException("Check your config file for server, some parameters are not valid");
            }
        }
    }

}

