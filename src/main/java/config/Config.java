package config;

import exception.server.InvalidServerConfigException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Config implements ConfigInterface {
    private static int serverPort;
    private static InetAddress serverAdress;
    private static int serverBacklog;

    @Override
    public void setServerPort(int port) {
        serverPort = port;
    }

    @Override
    public void setBackLog(int backlog) {
        serverBacklog = backlog;
    }

    @Override
    public void setInetAdress(String inetAdress) {
        try {
            serverAdress = InetAddress.getByName(inetAdress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setInetAdress(InetAddress inetAdress) {
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
