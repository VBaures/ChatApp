package ChatApp;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkHandler extends Thread {
    ServerHandler serverHandler;
    RemoteHandler remoteHandler;
    ArrayList<ClientHandler> listClientHandler;
    Agent agent;

    public NetworkHandler(Agent agent){
        this.agent=agent;
        this.listClientHandler=new ArrayList<>();
        try {
            StartServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartServer() throws IOException {
        this.serverHandler= new ServerHandler(this);
        serverHandler.start();
        this.remoteHandler = new RemoteHandler(this);
        remoteHandler.start();
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
    public RemoteHandler getRemoteHandler() { return this.remoteHandler; }
}
