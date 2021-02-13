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
            System.out.println("Connection au servlet ok "+connection);
            connection.setRequestMethod("POST");
            System.out.println("Connection au servlet ok1");
            connection.setUseCaches (false);
            System.out.println("Connection au servlet ok2");
            connection.setDoOutput(true);
            System.out.println("Connection au servlet ok3");
            connection.setRequestProperty("cmd","connected");
            System.out.println("Connection au servlet ok4");
            connection.setRequestProperty("ID",Integer.toString(networkHandler.getAgent().getPseudoHandler().getMain_User().getID()));
            System.out.println("Connection au servlet ok5");
            connection.setRequestProperty("pseudo", networkHandler.getAgent().getPseudoHandler().getMain_User().getPseudo().trim());
            connection.setRequestProperty("place", networkHandler.getAgent().getPseudoHandler().getMain_User().getPlace());
            connection.getResponseCode();
            System.out.println("Reponse ok");
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
             connection.setRequestProperty("place", networkHandler.getAgent().getPseudoHandler().getMain_User().getPlace());
             connection.disconnect();
         }catch (IOException e){}
     }

     public void getInformation(){
        try {
            System.out.println("Recupération info");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("Recup 0");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("place", networkHandler.getAgent().getPseudoHandler().getMain_User().getPlace());
            System.out.println("Recup 1.1.1");
            InputStream reader = connection.getInputStream();
            System.out.println("Recup 1.1.1.1 " + reader);
            InputStreamReader r = new InputStreamReader(reader);
            System.out.println("Recup 1.2");
            BufferedReader in = new BufferedReader(r);
            System.out.println("Recup 2");
            String line;
            while ((line = in.readLine()) != null) {
                String[] user = line.split(":");
                System.out.println("Line reçu: " + line);
                System.out.println("ID reçu: " + user[0]);
                if (user[3].trim().equals("connected")) {
                    networkHandler.getAgent().UpdateUsers(user[1], InetAddress.getByName(user[2]), Integer.parseInt(user[0]));
                } else {
                    networkHandler.getAgent().RemoveUser(Integer.parseInt(user[0]));
                }
            }
        }catch (IOException e){}
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
