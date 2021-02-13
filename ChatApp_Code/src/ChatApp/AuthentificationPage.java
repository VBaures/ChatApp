/*
This class implement the windows where the users will connect to the application

@author Vincent Baures and Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class AuthentificationPage implements ActionListener {
    private Agent agent;
    private JFrame frame;
    private JButton bouton1;
    private JButton bouton2;
    private JButton bouton_BDD;
    private JTextField pusername;
    private JTextField ppassword;

/*=============CONSTRUCTOR=============*/
    public AuthentificationPage (Agent agent){
        this.agent=agent;

        //Frame Creation
        frame= new JFrame("Application");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        frame.setSize(new Dimension(1000,1000));

        //Handling and creation of components
        JPanel panel1= new JPanel(new GridLayout(3,2));
        JPanel panel2= new JPanel(new GridLayout(1,3));
        JPanel panel4= new JPanel(new GridLayout(1,3));
        JLabel label_vide=new JLabel();
        JLabel title= new JLabel("Veuillez vous connectez");
        JLabel puser = new JLabel("Username ", SwingConstants.LEFT);
        pusername= new JTextField(SwingConstants.RIGHT);
        ppassword= new JTextField(2);
        JLabel ppass = new JLabel("Password", SwingConstants.LEFT);

        //Creation of buttons
        bouton1= new JButton("S'AUTHENTIFIER INDOOR USER");
        bouton1.addActionListener(this );
        bouton2= new JButton("S'AUTHENTIFIER OUTDOOR USER");
        bouton2.addActionListener(this );
        bouton_BDD=new JButton("Je me connecte pour la première fois");
        bouton_BDD.addActionListener(this);
        bouton_BDD.setForeground(Color.GRAY);

        //Add components to panels
        panel1.add(title,BorderLayout.LINE_START);
        panel1.add(label_vide,BorderLayout.CENTER);
        panel1.add(puser,BorderLayout.LINE_START);
        panel1.add(pusername,BorderLayout.CENTER);
        panel1.add(ppass,BorderLayout.LINE_START);
        panel1.add(ppassword,BorderLayout.CENTER);
        panel2.add(bouton1, BorderLayout.PAGE_END);
        panel2.add(bouton2, BorderLayout.PAGE_START);
        panel4.add(bouton_BDD,BorderLayout.LINE_END);

        //Add panels to the frame
        frame.getContentPane().add(panel2,BorderLayout.CENTER);
        frame.getContentPane().add(panel1,BorderLayout.PAGE_START);
        frame.getContentPane().add(panel4,BorderLayout.PAGE_END);

        //Frame handling
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        }


/*================Buttons events===========*/
        public void actionPerformed (ActionEvent e) {
            String username = pusername.getText();
            String password = ppassword.getText();
            if (e.getSource() == bouton1) {
                if (agent.LogIn(username, password)) {
                    agent.getPseudoHandler().getMain_User().setPlace("indoor");
                    frame.dispose();
                    agent.getPseudoPage().getFrame().setVisible(true);
                    agent.StartServers();
                }
            } else if (e.getSource() == bouton2) {
                if (agent.LogIn(username, password)) {
                    agent.getPseudoHandler().getMain_User().setPlace("remote");
                    frame.dispose();
                    agent.getPseudoPage().getFrame().setVisible(true);
                    agent.StartServers();
                }
            } else{
            agent.getBddpage().getFrame().setVisible(true);
        }

    }
}


