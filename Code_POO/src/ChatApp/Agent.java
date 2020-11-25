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
        Scanner keyboard = new Scanner(System.in);
        NetworkHandler t1 = new NetworkHandler();
        t1.start();
        t1.Connect(keyboard.nextInt());
        t1.StartChat(keyboard.nextInt());

    }
    }