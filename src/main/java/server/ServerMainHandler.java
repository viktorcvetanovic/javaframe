package server;

import data.ConfigProperty;
import enums.config.PropertyValue;
import exception.server.InvalidPropertiesFileException;
import exception.server.InvalidServerConfigException;
import lombok.Data;
import server.thread.WelcomeThread;
import util.PropertiesUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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


        public ServerSocket getServerSocket() {
            try {
                var propertiesUtil = new PropertiesUtil();
                List<ConfigProperty> configPropertyList = propertiesUtil.getProperties();
                var serverIp = propertiesUtil.filterPropertyByPropertyEnum(PropertyValue.SERVER_IP, configPropertyList).getPropertyValue();
                var serverPort = propertiesUtil.filterPropertyByPropertyEnum(PropertyValue.SERVER_PORT, configPropertyList).getPropertyValue();
                var serverBackLog = propertiesUtil.filterPropertyByPropertyEnum(PropertyValue.SERVER_BACKLOG, configPropertyList).getPropertyValue();
                return new ServerSocket(Integer.parseInt(serverPort), Integer.parseInt(serverBackLog));
            } catch (IOException e) {
                throw new InvalidServerConfigException("Check your server configuration");
            }
        }
    }

}

