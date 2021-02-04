package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1000,1000));

        //gestion et création des composants
        JPanel panel1= new JPanel(new GridLayout(3,2));
        JPanel panel2= new JPanel(new GridLayout(1,3));
        JPanel panel4= new JPanel(new GridLayout(1,3));
        JLabel label_vide=new JLabel();
        JLabel title= new JLabel("Veuillez vous connectez");
        JLabel puser = new JLabel("Login ", SwingConstants.LEFT);
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
            String getvalue_login= pusername.getText();
            String getValue_mdp=ppassword.getText();
            int id=-1;
            try {
                id = agent.getBddHandler().getIDUser(getvalue_login, getValue_mdp);
                System.out.println("ID="+id);
            if (id!=-1){
                System.out.println("Mise à jour ID = " + id);
                agent.getPseudoHandler().getMain_User().setID(id);
                System.out.println("Nouvel ID= "+agent.getPseudoHandler().getMain_User().getID());
                System.out.println(agent.getPseudoHandler().getMain_User());
                frame.dispose();
                agent.getPseudoPage().getFrame().setVisible(true);
                /*try {
                    agent.StartAgent();
                    agent.getNetworkHandler().getRemoteHandler().notifyServer();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }*/
            }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            agent.getBddpage().getFrame().setVisible(true);
        }
    }
}


