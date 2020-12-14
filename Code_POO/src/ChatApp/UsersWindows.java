package ChatApp;

import javax.swing.*;

public class UsersWindows extends Thread{
    JFrame frame;
    JListSimple jListSimple;
    Agent agent;
    public UsersWindows(Agent agent){
        jListSimple=new JListSimple(this);
        this.agent=agent;
    }
    public void run(){
        frame = new JFrame("Connectés");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(jListSimple);
        frame.setSize(250, 200);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        /*while(true){
            jListSimple.Mise_a_jour(agent.getPseudoHandler().getConnectedUsers());
        }*/
    }

    public Agent getAgent(){return this.agent;}

}
