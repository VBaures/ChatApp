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
    ArrayList <String> label;
    JList list;
    public ArrayList<String> getlabel (){return this.label;}

    public void Mise_a_jour (String nom_personne){
        label.add(nom_personne);
        list=new JList((ListModel) label);
        remove(pane);
        pane = new JScrollPane(list);
        add(pane, BorderLayout.CENTER);
    }
    public JListSimple() {
        label=new ArrayList<String>();
        this.setLayout(new BorderLayout( ));
        list=new JList();
        // Ajouter la JList dans le JScrolPane
        pane = new JScrollPane(list);
        JButton btnPrint = new JButton("Chatter");
        btnPrint.addActionListener(new SessionChat( ));

        add(pane, BorderLayout.CENTER);
        add(btnPrint, BorderLayout.SOUTH);
    }

    public void start() {
        JFrame frame = new JFrame("Connectés");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JListSimple( ));
        frame.setSize(250, 200);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);
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