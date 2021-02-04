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
    ArrayList<ClientHandler> listClientHandler;
    Agent agent;

    public NetworkHandler(Agent agent){
        this.agent=agent;
        this.listClientHandler=new ArrayList<>();
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
        listClientHandler.add(client);
        client.start();
    }

    public void StopChat(ChatHandler chatHandler){
        ClientHandler clientHandler = findClientHandler(chatHandler.getRecipient().getID());
        if (clientHandler!=null){
            listClientHandler.remove(clientHandler);
        }
    }

    public ClientHandler findClientHandler(int ID){
        for (int i=0; i<listClientHandler.size(); i++){
            if (listClientHandler.get(i).getChatHandler().getRecipient().getID()==ID){
                return listClientHandler.get(i);
            }
        }
        return null;
    }

    public ServerHandler getServerHandler(){
        return this.serverHandler;
    }
}
