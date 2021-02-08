/*
    Classe gérant la liste des pseudonymes des utilisateurs de l'application.
 */

package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

class JListSimple extends JPanel
{
    //déclaration des composant et des objets
    JScrollPane pane;
    JList list;
    UsersWindows usersWindows;

    //déclaration de la méthode Mise_a_jour de la liste
    public void Mise_a_jour (ArrayList<User> connectUsers){
        ArrayList<String> connected = new ArrayList<String>();
        for(int i=0; i<connectUsers.size(); i++){
            connected.add(connectUsers.get(i).getPseudo());
        }
        list.setListData(connected.toArray());
    }

    //déclaration du constructeur de la classe
    public JListSimple(UsersWindows usersWindows) {
        this.usersWindows=usersWindows;
        this.setLayout(new BorderLayout( ));
        list=new JList();

        // Ajout JList dans le panel
        pane = new JScrollPane(list);
        JButton btnPrint = new JButton("Chatter");
        btnPrint.addActionListener(new SessionChat( ));

        add(pane, BorderLayout.CENTER);
        add(btnPrint, BorderLayout.SOUTH);
    }
    
    // Afficher le éléments sélectionnés de la JList
    class SessionChat implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selected[] = list.getSelectedIndices( );
            System.out.println("Début de chat avec :  ");

            for (int i=0; i < selected.length; i++) {
                String element =
                        (String)list.getModel( ).getElementAt(selected[i]);
                System.out.println("  " + element);
                try {
                    usersWindows.getAgent().StartChat(element);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}