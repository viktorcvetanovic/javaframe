package config;

import data.ConfigProperty;
import enums.config.PropertyValue;
import exception.server.InvalidServerConfigException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import registry.ClazzRegistry;
import util.null_checker.NullUtil;
import util.properties.Properties;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {
    private static String serverIp;
    private static String serverPort;
    private static String serverBackLog;
    private static String staticContent;
    private static final Config instance = new Config();

    public static Config getInstance() {
        Properties properties = new Properties();
        List<ConfigProperty> configPropertyList = properties.getProperties();
        serverIp = NullUtil.orElseGet(properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_IP, configPropertyList).getPropertyValue(), "localhost");
        serverPort = NullUtil.orElseGet(properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_PORT, configPropertyList).getPropertyValue(), "8080");
        serverBackLog = NullUtil.orElseGet(properties.filterPropertyByPropertyEnum(PropertyValue.SERVER_BACKLOG, configPropertyList).getPropertyValue(), "80");
        staticContent = NullUtil.orElseGet(properties.filterPropertyByPropertyEnum(PropertyValue.STATIC_CONTENT, configPropertyList).getPropertyValue(), "static");
        return instance;
    }


    public ServerSocket getServerSocket() {
        try {
            return new ServerSocket(Integer.parseInt(serverPort), Integer.parseInt(serverBackLog), InetAddress.getByName(serverIp));
        } catch (IOException e) {
            throw new InvalidServerConfigException("Check your server configuration");
        }
    }

    public String getServerStaticContent() {
        return staticContent;
    }

    public String getServerURL() {
        return "http://" + serverIp + ":" + serverPort;
    }
}
