package server;

import class_finder.ClassFinder;
import data.ConfigProperty;
import enums.config.PropertyValue;
import exception.server.InvalidServerConfigException;
import lombok.RequiredArgsConstructor;
import registry.ClazzRegistry;
import server.thread.WelcomeThread;
import util.classutil.ClassUtil;
import util.properties.Properties;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMainHandler {
    private final ClassFinder classFinder = new ClassFinder();
    private final ClazzRegistry classRegistry = ClazzRegistry.getInstance();
    private ServerSocket serverSocket;
    private List<Socket> connectedSocket;
    private Config config;


    private ServerMainHandler() {

    }

    private void config() {
        config = new Config();
        this.serverSocket = config.getServerSocket();
        connectedSocket = new ArrayList<>();
    }

    private void startServer() throws IOException {
        while (true) {
            var socket = this.serverSocket.accept();
            connectedSocket.add(socket);
            new Thread(new WelcomeThread(socket)).start();
        }
    }

    private void loadClasses() {
        classFinder.findAllLoadedClasses().forEach(e -> {
            try {
                classRegistry.set(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
        });
    }

    public static void run() {
        var serverMainHandler = new ServerMainHandler();
        try {
            serverMainHandler.loadClasses();
            serverMainHandler.config();
            serverMainHandler.startServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static final class Config {

        private final Properties properties = new Properties();

        public ServerSocket getServerSocket() {
            try {
                List<ConfigProperty> configPropertyList = properties.getProperties();
                var serverIp = properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_IP, configPropertyList).getPropertyValue();
                var serverPort = properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_PORT, configPropertyList).getPropertyValue();
                var serverBackLog = properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_BACKLOG, configPropertyList).getPropertyValue();
                if (serverIp == null) {
                    serverIp = "localhost";
                }
                if (serverPort == null) {
                    serverPort = "8080";
                }
                if (serverBackLog == null) {
                    serverBackLog = "80";
                }
                return new ServerSocket(Integer.parseInt(serverPort), Integer.parseInt(serverBackLog), InetAddress.getByName(serverIp));
            } catch (IOException e) {
                throw new InvalidServerConfigException("Check your server configuration");
            }
        }
    }

}

