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
        ClientHandler client = new ClientHandler();
        client.StartChat();

    }
}