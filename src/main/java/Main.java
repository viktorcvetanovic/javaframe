import config.Config;
import config.ConfigInterface;
import server.ServerMainHandler;


public class Main {
    public static void main(String[] args) {
        ConfigInterface configInterface = new Config();
        configInterface.setBackLog(0);
        configInterface.setInetAdress("0.0.0.0");
        configInterface.setServerPort(7070);
        ServerMainHandler.run();
    }
}
