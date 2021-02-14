package com.example.ChatApp_Server;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


class User{
    private String pseudo;
    private String ID;
    private InetAddress IPAddress;
    private boolean status;
    private String place;

    public User (String pseudo, String ID, InetAddress IPAddress, String place){
        this.pseudo=pseudo;
        this.ID=ID;
        this.IPAddress=IPAddress;
        this.status=true;
        this.place=place;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus (boolean status) {
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(InetAddress IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

@WebServlet(name = "Servlet", value = "/servlet")
public class Servlet extends HttpServlet {
    private ArrayList<User> connectedUsers;
    private String essaie;

    public Servlet() {
        this.connectedUsers = new ArrayList<>();
    }

    public void init() {
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        String place = request.getHeader("place");
        PrintWriter pw =response.getWriter();
            for (int i = 0; i < connectedUsers.size(); i++) {
                User user = connectedUsers.get(i);
                if (place.equals("remote")) {
                    if (user.isStatus()) {
                        pw.println(user.getID() + ":" + user.getPseudo() + ":" + user.getIPAddress().getHostAddress() + ":connected");
                    } else {
                        pw.println(user.getID() + ":" + user.getPseudo() + ":" + user.getIPAddress().getHostAddress() + ":disconnected");
                    }
                } else {
                    if (user.getPlace().equals("remote")){
                        if (user.isStatus()) {
                            pw.println(user.getID() + ":" + user.getPseudo() + ":" + user.getIPAddress().getHostAddress() + ":connected");
                        } else {
                            pw.println(user.getID() + ":" + user.getPseudo() + ":" + user.getIPAddress().getHostAddress() + ":disconnected");
                        }
                    }
                }
            }
        pw.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String connected = request.getHeader("cmd");
        String ID = request.getHeader("ID");
        String pseudo = request.getHeader("pseudo");
        String IP = request.getHeader("IP");
        String place = request.getHeader("place");
        InetAddress IPAddress = InetAddress.getByName(getIpAddress(request));
        essaie = request.getRemoteHost();
        User user = getUser(ID);
        if (user!=null) {
            if (connected.equals("connected")) {
                user.setStatus(true);
                user.setID(ID);
                user.setPseudo(pseudo);
                user.setIPAddress(IPAddress);
                user.setPlace(place);
                return;
            } else {
                user.setStatus(false);
                return;
            }
        } else{
            connectedUsers.add(new User(pseudo,ID , IPAddress, place));
        }

    }

    public void destroy() {
    }

    private User getUser(String i) {
        int j;
        for (j = 0; j < connectedUsers.size(); j++) {
            essaie=connectedUsers.get(j).getID()+" "+i;
            if (connectedUsers.get(j).getID().equals(i)) {
                essaie="ok";
                return connectedUsers.get(j);
            } else {
                continue;
            }
        }
        return null;
    }

    protected String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");

        if(ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip ;
    }

}