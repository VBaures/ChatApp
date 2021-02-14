/*
This class display the window that contain the list of the connected users

@author Alicia Calmet
@date 2021-02-13
*/
package ChatApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UsersWindows extends Thread implements ActionListener {

    private JFrame frame;
    private JListSimple jListSimple;
    private Agent agent;
    private JMenuBar barre_menu;
    private JMenu m1;
    private JMenu m2;
    private JMenuItem  m11;
    private JMenuItem m12;
    private JMenuItem m22;

/*==========CONSTRUCTOR==========*/
    public UsersWindows(Agent agent){
        jListSimple=new JListSimple(this);
        this.agent=agent;
        this.start();
    }

/*==========CONSTRUCTOR==========*/
    public void run(){

        //Frame Creation
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

        //Handling and creation of components
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

        //Add panels to the frame
        frame.getContentPane().add(BorderLayout.NORTH,barre_menu);

        //Frame handling
        frame.setSize(250, 200);
        frame.setLocationRelativeTo(null);
    }

/*================Buttons events===========*/
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==m11){
            agent.getPseudoPage().getFrame().setVisible(true);
        }
        else if (e.getSource()==m22){
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        URI uri = new URI("https://github.com/VBaures/Projet-COO/raw/master/guide_utilisation_poo.pdf");
                        Desktop dt = Desktop.getDesktop();
                        dt.browse(uri);
                    } catch (IOException | URISyntaxException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        else {
            try {
                agent.Disconnect();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.dispose();
            System.exit(0);
        }
    }

    public Agent getAgent(){return this.agent;}
    public JFrame getFrame() {
        return frame;
    }
    public JListSimple getjListSimple() { return jListSimple; }
}
