package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AuthentificationPage implements ActionListener {
    Agent agent;
    JFrame frame;
    JButton bouton;
    JButton bouton_BDD;
    public AuthentificationPage (Agent agent){
        this.agent=agent;
        //gestion fenetre
        frame= new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1000,1000));

        //gestion et création des composants
        JPanel panel1= new JPanel(new GridLayout(3,2));
        JPanel panel2= new JPanel(new GridLayout(1,3));
        JPanel panel4= new JPanel(new GridLayout(1,3));
        JLabel label_vide=new JLabel();
        JLabel title= new JLabel("Veuillez vous connectez");
        JLabel puser = new JLabel("Username ", SwingConstants.LEFT);
        JTextField pusername= new JTextField(SwingConstants.RIGHT);
        JTextField ppassword= new JTextField(2);
        JLabel ppass = new JLabel("Password", SwingConstants.LEFT);

        //les boutons
        bouton= new JButton("S'AUTHENTIFIER");
        bouton.addActionListener(this );

        bouton_BDD=new JButton("Je me connecte pour la première fois");
        bouton_BDD.addActionListener(this);
        bouton_BDD.setForeground(Color.GRAY);

        //ajout composant panels
        panel1.add(title,BorderLayout.LINE_START);
        panel1.add(label_vide,BorderLayout.CENTER);
        panel1.add(puser,BorderLayout.LINE_START);
        panel1.add(pusername,BorderLayout.CENTER);
        panel1.add(ppass,BorderLayout.LINE_START);
        panel1.add(ppassword,BorderLayout.CENTER);

        panel2.add(bouton, BorderLayout.CENTER);
        panel4.add(bouton_BDD,BorderLayout.LINE_END);

        //ajout panels dans la fenetre
        frame.getContentPane().add(panel2,BorderLayout.CENTER);
        frame.getContentPane().add(panel1,BorderLayout.PAGE_START);
        frame.getContentPane().add(panel4,BorderLayout.PAGE_END);

        //gestion fenetre
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        }

        //TODO; a completer quand BDD prete
        //gestion des actions sur les boutons
        public void actionPerformed (ActionEvent e){
        if (e.getSource()==bouton){
            frame.dispose();
            agent.getPseudoPage().getFrame().setVisible(true);}
        else{
            agent.getBddpage().getFrame().setVisible(true);
        }
    }
}


