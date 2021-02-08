package ChatApp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
public class ClientHandler extends Thread{
    private NetworkHandler networkHandler;
    private ChatHandler chatHandler;
    private Socket link;

    public ClientHandler(NetworkHandler networkHandler, ChatHandler chatHandler){
        this.networkHandler = networkHandler;
        this.chatHandler = chatHandler;
    }

    public void run() {
            try {
                System.out.println("Adress IP "+chatHandler.getRecipient().getAddr_Ip());
                this.link = new Socket(chatHandler.getRecipient().getAddr_Ip().getHostAddress(),1040, InetAddress.getByName("0.0.0.0"),0);
                link.setKeepAlive(false);
                System.out.println("client link créé");
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(link.getInputStream());
                User sender = networkHandler.getAgent().getPseudoHandler().FindUserByIP(link.getInetAddress().getHostAddress());
                chatHandler.setOutput(out);
                chatHandler.setSocket(link);
                while(true){
                    try {
                        Object ObjectReceive=in.readObject();
                        System.out.println(ObjectReceive);
                        if (ObjectReceive instanceof StringMessage) {
                            StringMessage receive = (StringMessage) ObjectReceive;
                            System.out.println("Message reçu :"+ receive.getContentString());
                            chatHandler.Receive(receive);
                        } else if (ObjectReceive instanceof FileMessage) {
                            FileMessage receive = (FileMessage) ObjectReceive;
                            System.out.println("Message avec file reçu");
                            chatHandler.Receive(receive);
                        } else if (ObjectReceive instanceof String) {
                            String receive = (String) ObjectReceive;
                            System.out.println(receive);
                            if (receive.equals("StopChat")){
                                networkHandler.getAgent().StopChat(sender.getID());
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
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
