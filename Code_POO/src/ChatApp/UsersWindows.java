package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersWindows extends Thread implements ActionListener   {
    JFrame frame;
    JListSimple jListSimple;
    Agent agent;
    JMenuBar barre_menu;
    JMenu m1;
    JMenu m2;
    JMenuItem  m11;
    JMenuItem m22;
    public UsersWindows(Agent agent){
        jListSimple=new JListSimple(this);
        this.agent=agent;
        this.start();
    }
    public void run(){
        frame = new JFrame("Connectés");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(jListSimple);
        barre_menu=new JMenuBar();
        m1= new JMenu("Pseudo");
        m2=new JMenu("Help ! J'ai besoin d'aide !");
        barre_menu.add(m1);
        barre_menu.add(m2);
        m11= new JMenuItem("Changer Pseudo");
        m22=new JMenuItem("Aide");
        m11.addActionListener(this);
        m1.add(m11);
        m2.add(m22);
        frame.getContentPane().add(BorderLayout.NORTH,barre_menu);
        frame.setSize(250, 200);
        frame.setLocationRelativeTo(null);
        /*while(true){
            jListSimple.Mise_a_jour(agent.getPseudoHandler().getConnectedUsers());
        }*/
    }

    public Agent getAgent(){return this.agent;}

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==m11){
            agent.getPseudoPage().getFrame().setVisible(true);
        }
    }
}
