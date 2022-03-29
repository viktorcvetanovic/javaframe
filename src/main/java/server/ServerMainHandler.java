package server;

import annotations.Wired;
import class_finder.ClassFinder;

import config.Config;
import registry.ClazzRegistry;
import server.thread.WelcomeThread;
import util.classutil.ClassUtil;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
    private void instanceClasses() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> clazz : classRegistry.getAllKeys()) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Wired.class)) {
                    if(field.getClass().isInterface()){
                        Class<?>[] clazzes=field.getClass().getClasses();
                        Constructor<?> constructor = new ClassUtil().getBestConstructor(clazzes[0]);
                        field.set(field.getClass(),constructor.newInstance());
                    }else{
                        Constructor<?> constructor = new ClassUtil().getBestConstructor(field.getClass());
                        field.set(field.getClass(), constructor.newInstance());
                    }
                }
            }

        }
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

