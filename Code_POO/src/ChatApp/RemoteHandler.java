package ChatApp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class RemoteHandler extends Thread{
    private NetworkHandler networkHandler;
    private URL url;
    public RemoteHandler(NetworkHandler networkHandler) {
        this.networkHandler=networkHandler;
        try{
            url = new URL("https://srv-gei-tomcat.insa-toulouse.fr/ChatApp_Server/servlet");
        } catch (MalformedURLException e){
        }
    }

    public void notifyServer(){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches (false);
            connection.setDoOutput(true);
            connection.setRequestProperty("cmd","connected");
            connection.setRequestProperty("ID",Integer.toString(networkHandler.getAgent().getPseudoHandler().getMain_User().getID()));
            connection.setRequestProperty("pseudo", networkHandler.getAgent().getPseudoHandler().getMain_User().getPseudo().trim());
            connection.setRequestProperty("IP",networkHandler.getAgent().getPseudoHandler().getMain_User().getAddr_Ip().getHostAddress());
            System.out.println("Envoie ok " + connection.getResponseCode());
            connection.disconnect();
        }catch (IOException e){}
    }

     public void NotifyDisconnection(){
         try {
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("POST");
             connection.setUseCaches (false);
             connection.setDoOutput(true);
             connection.setRequestProperty("cmd","disconnected");
             connection.setRequestProperty("ID",Integer.toString(networkHandler.getAgent().getPseudoHandler().getMain_User().getID()));
             connection.setRequestProperty("pseudo", networkHandler.getAgent().getPseudoHandler().getMain_User().getPseudo().trim());
             connection.setRequestProperty("IP",networkHandler.getAgent().getPseudoHandler().getMain_User().getAddr_Ip().getHostAddress());
             System.out.println("Envoie deconnection ok " + connection.getResponseCode());
             connection.disconnect();
         }catch (IOException e){}
     }

     public void getInformation(){
        try{
        System.out.println("Recupération info");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println("Recup 0");
        connection.setRequestMethod("GET");
            System.out.println("Recup 1.1.1");
                InputStream reader = connection.getInputStream();
                InputStreamReader r = new InputStreamReader(reader);
                System.out.println("Recup 1.2");
                BufferedReader in = new BufferedReader(r);
                System.out.println("Recup 2");
                String line;
                System.out.println("Line reçu: " + in.readLine());
                while ((line = in.readLine()) != null) {
                    String[] user = line.split(":");
                    System.out.println("Line reçu: " + line);
                    System.out.println("ID reçu: " + user[0]);
                    User find = networkHandler.getAgent().getPseudoHandler().FindUser(Integer.parseInt(user[0]));
                        System.out.println("User trouvé" + find);
                        if (find != null) {
                            if (user[3].trim().equals("connected") && find.getPlace().trim().equals("remote")) {
                                find.setPseudo(user[1]);
                                find.setAddr_IP(InetAddress.getByName(user[2].substring(0)));
                                find.setID(Integer.parseInt(user[0]));
                            } else {
                                networkHandler.getAgent().getPseudoHandler().getConnectedUsers().remove(find);
                            }
                        } else {
                            System.out.println("Is me = " + (networkHandler.getAgent().getPseudoHandler().getMain_User().getID()));
                            if (networkHandler.getAgent().getPseudoHandler().getMain_User().getID() != Integer.parseInt(user[0])&&user[3].trim().equals("connected")) {
                                System.out.println("Ajout via remote");
                                User newUser = new User(user[1], InetAddress.getByName(user[2].substring(0)), Integer.parseInt(user[0]));
                                newUser.setPlace("remote");
                                networkHandler.getAgent().getPseudoHandler().getConnectedUsers().add(newUser);
                                System.out.println(networkHandler.getAgent().getPseudoHandler().getConnectedUsers());
                            }

                        }
                    }

         }catch (IOException e){}
         networkHandler.getAgent().getUsersWindows().jListSimple.Mise_a_jour(networkHandler.getAgent().getPseudoHandler().getConnectedUsers());

    }

     public void run(){
        Timer timer = new Timer();
         TimerTask timerTask = new TimerTask() {
             @Override
             public void run() {
                 getInformation();
             }
         };
         timer.scheduleAtFixedRate(timerTask,0,2000 );
     }
}

