package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkHandler extends Thread {
    ArrayList<ChatHandler> currentChats = new ArrayList<>();

    public ArrayList<ChatHandler> getCurrentChats() {
        return currentChats;
    }
}
