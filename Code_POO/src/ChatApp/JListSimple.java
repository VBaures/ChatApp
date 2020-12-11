package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class JListSimple extends JPanel
{
    JScrollPane pane;
    // Le contenu de la JList
    JList list;
    UsersWindows usersWindows;


    public void Mise_a_jour (ArrayList<User> connectUsers){
        ArrayList<String> connected = new ArrayList<String>();
        for(int i=0; i<connectUsers.size(); i++){
            connected.add(connectUsers.get(i).getUserName());
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
                PageChat newpage = new PageChat();
                newpage.affichage();
            }
        }
    }
    public class PageChat{

        public void affichage(){

            JFrame fram= new JFrame("Application");
            fram.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fram.setSize(new Dimension(1000,1000));
            JPanel panel1= new JPanel(new GridLayout(1,4));
            JPanel panel2= new JPanel(new GridLayout(1,4));
            JLabel label1=new JLabel ("Bonjour");
            label1.setForeground(Color.BLUE);
            JButton bouton=new JButton("Envoyer");
            bouton.setForeground(Color.GRAY);
            JTextField zone_texte=new JTextField();
            panel2.add(zone_texte);
            panel2.add(bouton);
            panel1.add(label1, BorderLayout.PAGE_START);
            fram.getContentPane().add(panel1, BorderLayout.PAGE_START);
            fram.getContentPane().add(panel2, BorderLayout.PAGE_END);
            fram.pack();
            fram.setVisible(true);
            fram.setLocationRelativeTo(null);}
        }
}