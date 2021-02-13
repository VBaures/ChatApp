/*
This class handle the list of pseudos displayed in the connected users windows

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

class JListSimple extends JPanel implements ActionListener {
    private JScrollPane pane;
    private JList list;
    private UsersWindows usersWindows;


/*==========CONSTRUCTORS==========*/
    public JListSimple(UsersWindows usersWindows) {
        this.usersWindows=usersWindows;
        this.setLayout(new BorderLayout( ));
        list=new JList();

        pane = new JScrollPane(list);
        JButton btnPrint = new JButton("Chatter");
        btnPrint.addActionListener(this);

        add(pane, BorderLayout.CENTER);
        add(btnPrint, BorderLayout.SOUTH);
    }

/*================Buttons events===========*/
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

/*This function  add a new pseudo to the list*/
    public void Mise_a_jour (ArrayList<User> connectUsers){
        ArrayList<String> connected = new ArrayList<String>();
        for(int i=0; i<connectUsers.size(); i++){
            if (connectUsers.get(i).getPseudo().equals("notdefine")==false) {
                connected.add(connectUsers.get(i).getPseudo());
            }
        }
        list.setListData(connected.toArray());
    }
}
