
import server.ServerMainHandler;


public class Main {
    public static void main(String[] args) {
        ServerMainHandler.Config.setBackLog(0);
        ServerMainHandler.Config.setInetAdress("0.0.0.0");
        ServerMainHandler.Config.setServerPort(7070);
        ServerMainHandler.run();
    }
}
