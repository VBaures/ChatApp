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
    ServerHandler serverHandler;
    Agent agent;
    public NetworkHandler(Agent agent){
        this.agent=agent;
    }

    public void StartServer() throws IOException {
        this.serverHandler= new ServerHandler(this,agent.getPseudoHandler().getMain_User().getServerPort());
        serverHandler.start();
    }

    public Agent getAgent(){
        return agent;
    }

    public void StartChat(ChatHandler chatHandler) throws IOException {
        ClientHandler client = new ClientHandler(this, chatHandler );
        client.start();
    }
}
