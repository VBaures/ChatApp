/*
This class handle all the network aspect of the network, the TCP and UDP servers, the connection to the remote sever and the creation of TCP clients

@author Vincent Baures
@date 2021-02-13
*/

package ChatApp;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkHandler extends Thread {
    private ServerHandler serverHandler;
    private RemoteHandler remoteHandler;
    private ArrayList<ClientHandler> listClientHandler;
    private Agent agent;

/*=======CONSTRUCTOR==========*/
    public NetworkHandler(Agent agent){
        this.agent=agent;
        this.listClientHandler=new ArrayList<>();
        try {
            StartServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*This function create and start the classes that will handle the TCP and UDP server as well as the HTTP connection to the remote server*/
    private void StartServer() throws IOException {
        this.serverHandler= new ServerHandler(this);
        serverHandler.start();
        this.remoteHandler = new RemoteHandler(this);
        remoteHandler.start();
    }

/* This function create a new TCP client */
    public void StartChat(ChatHandler chatHandler) throws IOException {
        ClientHandler client = new ClientHandler(this, chatHandler );
        listClientHandler.add(client);
        client.start();
    }

/* This function destroy a  TCP client */
    public void StopChat(ChatHandler chatHandler){
        ClientHandler clientHandler = findClientHandler(chatHandler.getRecipient().getID());
        if (clientHandler!=null){
            listClientHandler.remove(clientHandler);
        }
    }

/* This function retrieve a  TCP client via the recipient ID */
    private ClientHandler findClientHandler(int ID){
        for (int i=0; i<listClientHandler.size(); i++){
            if (listClientHandler.get(i).getChatHandler().getRecipient().getID()==ID){
                return listClientHandler.get(i);
            }
        }
        return null;
    }

/*==========GETTERS AND SETTERS==========*/
    public Agent getAgent(){ return agent; }
    public ServerHandler getServerHandler(){ return this.serverHandler; }
    public RemoteHandler getRemoteHandler() { return this.remoteHandler; }
}
