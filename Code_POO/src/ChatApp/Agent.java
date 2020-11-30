package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Agent {

    public static void main(String[] args) {
        ServerHandler t1 = new ServerHandler();
        ClientHandler t2 = new ClientHandler();
        t1.start();
        t2.start();

    }
}