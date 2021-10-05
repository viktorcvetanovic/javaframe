package server;

import class_finder.ClassFinder;

import config.Config;
import registry.ClazzRegistry;
import server.thread.WelcomeThread;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
        config = Config.getInstance();
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

    private void printInfo() {
        System.out.println("JAVAFRAME");
        System.out.println("Server is started on: " + config.getServerURL());
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

    //TODO: implement autowire
    private void instanceClasses() {

    }

    public static void run() {
        var serverMainHandler = new ServerMainHandler();
        try {
            serverMainHandler.loadClasses();
            serverMainHandler.instanceClasses();
            serverMainHandler.config();
            serverMainHandler.printInfo();
            serverMainHandler.startServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

