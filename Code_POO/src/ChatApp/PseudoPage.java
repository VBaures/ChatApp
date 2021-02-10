/*
    Classe gérant le pseudonyme d'un utilisateur et l'unicité des pseudonymes.
 */

package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class PseudoPage implements ActionListener {

    //déclaration descomposant et des objets
    Agent agent;
    JTextField ppseudo;
    JFrame frame;
    JButton bouton1;

    //déclaration du constructeur de la classe
    public PseudoPage (Agent agent){
        this.agent=agent;

        //gestion fenêtre
        frame= new JFrame("Choose pseudo");
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
        frame.setSize(new Dimension(1000,1000));

        //gestion et création des composants
        JPanel panel1= new JPanel(new BorderLayout());
        JPanel panel2= new JPanel(new BorderLayout());
        JLabel title= new JLabel("Veuillez rentrer un pseudo");
        JLabel pseudo = new JLabel("Pseudo ", SwingConstants.LEFT);
        ppseudo= new JTextField(SwingConstants.RIGHT);
        bouton1= new JButton("Valider");
        bouton1.addActionListener(this );

        //ajout composant panels
        panel1.add(title,BorderLayout.PAGE_START);
        panel1.add(pseudo,BorderLayout.LINE_START);
        panel1.add(ppseudo,BorderLayout.CENTER);
        panel2.add(bouton1, BorderLayout.PAGE_START);

        //ajout panels dans la fenetre
        frame.getContentPane().add(panel1, BorderLayout.PAGE_START);
        frame.getContentPane().add(panel2, BorderLayout.PAGE_END);

        //gestion fenêtre
        frame.pack();
        frame.setLocationRelativeTo(null); }

    /* gestion du bouton: on vérifie l'unicité du pseudonyme choisi par l'utilisateur et on en informe
    les autres utilisateurs de l'application */
    public void actionPerformed (ActionEvent e){
        String getvalue= ppseudo.getText();
        System.out.println("Message :"+getvalue);
        System.out.println(agent.getPseudoHandler().VerifyPseudo(getvalue));
        if (agent.getPseudoHandler().VerifyPseudo(getvalue)){
            agent.getPseudoHandler().getMain_User().setPseudo(getvalue);
            try {
                System.out.println("Id envoyé " + agent.getPseudoHandler().getMain_User().getID());
                if (agent.getPseudoHandler().getMain_User().getPlace().equals("indoor")){
                    System.out.println("Notif indoor connect");
                    agent.getNetworkHandler().getServerHandler().getUdp().broadcastUDP("NewPseudo", agent.getPseudoHandler().getMain_User());
                    System.out.println("Notif indoor connect broadcast ok");
                    agent.getNetworkHandler().getRemoteHandler().notifyServer();
                    System.out.println("Notif indoor connect servlet ok");
                } else if (agent.getPseudoHandler().getMain_User().getPlace().equals("remote")){
                    agent.getNetworkHandler().getRemoteHandler().notifyServer();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.setVisible(false);
            agent.getUsersWindows().getFrame().setVisible(true);
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}

