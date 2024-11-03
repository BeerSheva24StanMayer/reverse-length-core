package telran.net;

import telran.view.*;
import org.json.*;

public class Main {
    static EchoClient echoClient;

    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::exit, true)
      };
      Menu menu = new Menu("Echo Application", items);
      menu.perform(new StandardInputOutput());
    }

    public static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname: ");
        Integer port = io.readNumberRange("Enter port: ", "Wrong port", 1, 65536).intValue();
        if(echoClient != null) {
            echoClient.close();
        }
        echoClient = new EchoClient(host, port);
        Item[] items = {
            Item.of("Get your string reversed", Main::getReverse),
            Item.of("Get length of your string", Main::getLength),
            Item.ofExit()
      };
        Menu menu = new Menu("Session is running", items);
        menu.perform(io);

    }

    static void getReverse(InputOutput io) {
        String response = outString(io, 1);
        io.writeLine(response);
    }

    static void getLength(InputOutput io) {
        String response = outString(io, 2);
        io.writeLine(response);
    }

    private static String outString(InputOutput io, Integer code) {
        String json = new JSONObject().put(code.toString(), io.readString("Enter any string: ")).toString();
        return echoClient.sendAndReceive(json);
    }

    static void exit(InputOutput io) {
        if(echoClient != null) {
            echoClient.close();
        }
    }
}