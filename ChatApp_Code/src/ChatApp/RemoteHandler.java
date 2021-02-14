/*
This class handle the connection to the remote server via HTTP

@author Vincent Baures
@date 2021-02-13
*/
package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class RemoteHandler extends Thread{
    private NetworkHandler networkHandler;
    private URL url;

/*==========CONSTRUCTORS==========*/
    public RemoteHandler(NetworkHandler networkHandler) {
        this.networkHandler=networkHandler;
        try{
            url = new URL("https://srv-gei-tomcat.insa-toulouse.fr/ChatApp_Server/servlet");
        } catch (MalformedURLException e){
        }
    }

/* This function notify the remote server with information concerning the main user */
    public void notifyServer(){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches (false);
            connection.setDoOutput(true);
            connection.setRequestProperty("cmd","connected");
            connection.setRequestProperty("ID",Integer.toString(networkHandler.getAgent().getPseudoHandler().getMain_User().getID()));
            connection.setRequestProperty("pseudo", networkHandler.getAgent().getPseudoHandler().getMain_User().getPseudo().trim());
            connection.setRequestProperty("place", networkHandler.getAgent().getPseudoHandler().getMain_User().getPlace());
            connection.getResponseCode();
            connection.disconnect();
        }catch (IOException e){}
    }

/* This function inform the remote server of the disconnection of the main user */
     public void NotifyDisconnection(){
         try {
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("POST");
             connection.setUseCaches (false);
             connection.setDoOutput(true);
             connection.setRequestProperty("cmd","disconnected");
             connection.setRequestProperty("ID",Integer.toString(networkHandler.getAgent().getPseudoHandler().getMain_User().getID()));
             connection.setRequestProperty("pseudo", networkHandler.getAgent().getPseudoHandler().getMain_User().getPseudo().trim());
             connection.setRequestProperty("place", networkHandler.getAgent().getPseudoHandler().getMain_User().getPlace());
             connection.disconnect();
         }catch (IOException e){}
     }

/* This function ask who is connected to the remote server */
    private void getInformation(){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("place", networkHandler.getAgent().getPseudoHandler().getMain_User().getPlace());
            InputStream reader = connection.getInputStream();
            InputStreamReader r = new InputStreamReader(reader);
            BufferedReader in = new BufferedReader(r);
            String line;
            while ((line = in.readLine()) != null) {
                String[] user = line.split(":");
                if (user[3].trim().equals("connected")) {
                    networkHandler.getAgent().UpdateUsers(user[1], InetAddress.getByName(user[2]), Integer.parseInt(user[0]));
                } else {
                    networkHandler.getAgent().RemoveUser(Integer.parseInt(user[0]));
                }
            }
        }catch (IOException e){}
    }

/*========== RUN METHOD ==========*/
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

