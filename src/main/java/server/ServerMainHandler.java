package server;

import data.ConfigProperty;
import enums.config.PropertyValue;
import exception.server.InvalidServerConfigException;
import server.thread.WelcomeThread;
import util.properties.Properties;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMainHandler {
    private ServerSocket serverSocket;
    private List<Socket> connectedSocket;
    private Config config;


    private ServerMainHandler() {
        config = new Config();
        this.serverSocket = config.getServerSocket();
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


    public static final class Config {

        private final Properties properties = new Properties();

        public ServerSocket getServerSocket() {
            try {
                //TODO: FIX THIS
                List<ConfigProperty> configPropertyList = properties.getProperties();
                var serverIp = properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_IP, configPropertyList).getPropertyValue();
                var serverPort = properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_PORT, configPropertyList).getPropertyValue();
                var serverBackLog = properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_BACKLOG, configPropertyList).getPropertyValue();
                return new ServerSocket(Integer.parseInt(serverPort));
            } catch (IOException e) {
                throw new InvalidServerConfigException("Check your server configuration");
            }
        }
    }

}

