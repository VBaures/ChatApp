package ChatApp;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    NetworkHandler networkHandler;
    ChatHandler chatHandler;
    Socket link;

    public ClientHandler(NetworkHandler networkHandler, ChatHandler chatHandler) throws IOException {
        this.networkHandler = networkHandler;
        this.chatHandler = chatHandler;
    }

    public void run() {
            try {
                //Scanner keyboard = new Scanner(System.in);
                //System.out.println("Enter client port");
                this.link = new Socket(chatHandler.getRecipient().getAddr_Ip(),1040);
                link.setKeepAlive(false);
                System.out.println("client link créé");
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());

                ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                networkHandler.getAgent().findChatHandler(chatHandler.getRecipient().getID()).setOutput(out);
                networkHandler.getAgent().findChatHandler(chatHandler.getRecipient().getID()).setSocket(link);
                chatHandler.setOutput(out);
                chatHandler.setSocket(link);
                User sender = networkHandler.getAgent().getPseudoHandler().FindUserByPortServer(link.getPort());
                while(true){
                    try {
                        Object ObjectReceive=in.readObject();
                        System.out.println(ObjectReceive);
                        if (ObjectReceive instanceof StringMessage) {
                            StringMessage receive = (StringMessage) ObjectReceive;
                            System.out.println("Message reçu :"+ receive.getContentString());
                            chatHandler.getMessageHistory().add(receive);
                            chatHandler.getChatPage().Mise_a_jour();
                        } else if (ObjectReceive instanceof FileMessage) {
                            FileMessage receive = (FileMessage) ObjectReceive;
                            System.out.println("Message avec file reçu");
                            chatHandler.getMessageHistory().add(receive);
                            chatHandler.getChatPage().Mise_a_jour();
                        } else if (ObjectReceive instanceof String) {
                            String receive = (String) ObjectReceive;
                            System.out.println(receive);
                            if (receive.equals("StopChat")){
                                networkHandler.getAgent().getCurrentChat().remove(chatHandler);
                                chatHandler.getChatPage().getFram().dispose();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                out.close();
                in.close();
                link.close();

            } catch(IOException e){
                    System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
            }
    }

    public ChatHandler getChatHandler(){
        return this.chatHandler;
    }
}
