package ChatApp;/*
    Classe gérant l'affichage de la liste des pseudonymes des utilisateurs de l'application.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class UsersWindows extends Thread implements ActionListener {

    //déclaration des composant et des objets
    JFrame frame;
    JListSimple jListSimple;
    Agent agent;
    JMenuBar barre_menu;
    JMenu m1;
    JMenu m2;
    JMenuItem  m11;
    JMenuItem m12;
    JMenuItem m22;

    //declaration du constructeur de la classe
    public UsersWindows(Agent agent){
        jListSimple=new JListSimple(this);
        this.agent=agent;
        this.start();
    }

    //implementation de la méthode run de la classe
    public void run(){

        //gestion fenetre
        frame = new JFrame("Connectés");
        frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        agent.Disconnect();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    frame.dispose();
                    System.exit(0);
                }
            });
        frame.setContentPane(jListSimple);

        //gestion et création des composants
        barre_menu=new JMenuBar();
        m1= new JMenu("Options");
        m2=new JMenu("J'ai besoin d'aide !");
        barre_menu.add(m1);
        barre_menu.add(m2);
        m12=new JMenuItem("Déconnexion");
        m11= new JMenuItem("Changer Pseudo");
        m22=new JMenuItem("Aide");
        m11.addActionListener(this);
        m12.addActionListener(this);
        m22.addActionListener(this);
        m1.add(m11);
        m1.add(m12);
        m2.add(m22);

        //ajout composant panels
        frame.getContentPane().add(BorderLayout.NORTH,barre_menu);

        //gestion fenetre
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

    public int Close() throws IOException {
        agent.getNetworkHandler().getRemoteHandler().NotifyDisconnection();
        agent.Disconnect();
        return 0;
    }

    //gestion du bouton
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==m11){
            agent.getPseudoPage().getFrame().setVisible(true);
        }
        else if (e.getSource()==m22){
            File fichier = new File("C:/Users/calme/Documents/include.pdf");
            // On vérifie que la classe Desktop soit bien supportée :
            if (Desktop.isDesktopSupported()) {
                // On récupère l'instance du desktop :
                Desktop desktop = Desktop.getDesktop();
                // On vérifie que la fonction open est bien supportée :
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Et on lance l'application associé au fichier pour l'ouvrir :
                    try {
                        desktop.open(fichier);
                    }
                    catch(IOException ex) {
                        // Gestion de l'erreur
                    }
                }
            }
        }
        else {
            try {
                agent.getNetworkHandler().getRemoteHandler().NotifyDisconnection();
                agent.Disconnect();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.dispose();
            System.exit(0);
        }
    }
}
