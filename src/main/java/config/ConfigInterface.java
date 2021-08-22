package config;

import java.net.InetAddress;

public interface ConfigInterface {
    void setServerPort(int port);

    void  setBackLog(int backlog);

    void setInetAdress(String inetAdress);

    void setInetAdress(InetAddress inetAdress);
}
