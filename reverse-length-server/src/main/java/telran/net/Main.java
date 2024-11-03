package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.*;  

public class Main {
       private static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String line = "";
            while((line = reader.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                if(json.has("1")) {
                    writer.println(new StringBuffer(json.getString("1")).reverse().toString());
                }
                else if(json.has("2")) {
                    writer.println(json.getString("2").length());
                }
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }
}