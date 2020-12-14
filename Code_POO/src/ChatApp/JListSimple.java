package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

class JListSimple extends JPanel
{
    JScrollPane pane;
    // Le contenu de la JList
    JList list;
    UsersWindows usersWindows;
    JList liste_message;
    JScrollPane pane2;


    public void Mise_a_jour (ArrayList<User> connectUsers){
        ArrayList<String> connected = new ArrayList<String>();
        for(int i=0; i<connectUsers.size(); i++){
            connected.add(connectUsers.get(i).getPseudo());
        }
        list.setListData(connected.toArray());
    }
    public JListSimple(UsersWindows usersWindows) {
        this.usersWindows=usersWindows;
        this.setLayout(new BorderLayout( ));
        list=new JList();
        // Ajouter la JList dans le JScrolPane
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
                //JOptionPane.showMessageDialog(null, element);
                //ChatPage newpage = new ChatPage();
                //newpage.affichage();
                try {
                    usersWindows.getAgent().StartChat(element);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}