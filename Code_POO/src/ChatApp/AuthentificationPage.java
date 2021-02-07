package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

class AuthentificationPage implements ActionListener {
    Agent agent;
    JFrame frame;
    JButton bouton;
    JButton bouton_BDD;
    JTextField pusername;
    JTextField ppassword;

    public AuthentificationPage (Agent agent){
        this.agent=agent;
        //gestion fenetre
        frame= new JFrame("Application");
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
        JPanel panel1= new JPanel(new GridLayout(3,2));
        JPanel panel2= new JPanel(new GridLayout(1,3));
        JPanel panel4= new JPanel(new GridLayout(1,3));
        JLabel label_vide=new JLabel();
        JLabel title= new JLabel("Veuillez vous connectez");
        JLabel puser = new JLabel("Username ", SwingConstants.LEFT);
        pusername= new JTextField(SwingConstants.RIGHT);
        ppassword= new JTextField(2);
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
            String username= pusername.getText();
            String password=ppassword.getText();
            if (agent.LogIn(username, password)) {
                frame.dispose();
                agent.getPseudoPage().getFrame().setVisible(true);
            }
        }else{
            agent.getBddpage().getFrame().setVisible(true);
        }
    }
}


