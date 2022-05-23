package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static int NumeroPorta = 8000;
    static Socket clientSocket = null;

    public static void main(String[] args) {

        ServerSocket serverSocket = openToServer();
        System.out.println("Server socket avviato al numero di porta: " + serverSocket.getLocalPort());

        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("connessione richiesta dal client fallita" + e);
            }

            System.out.println(clientSocket.getLocalAddress());

        }

    }

    static private ServerSocket openToServer() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(NumeroPorta);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return serverSocket;
    }
}
