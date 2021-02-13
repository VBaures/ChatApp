/*
This class implement the windows used to create an account

@author Vincent Baures Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BDDpage implements ActionListener {
    private JFrame fram;
    private Agent agent;
    private JButton bouton;
    private JTextField pmdp;
    private JTextField plogin;

/*=======Constructor============*/
    public BDDpage (Agent agent){
        this.agent=agent;

        //Frame Creation
        fram=new JFrame("Inscription Utilisateur ");
        fram.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fram.getContentPane().setPreferredSize(new Dimension(200,100));

        //Handling and creation of components
        JPanel panel1= new JPanel(new GridLayout(2,2));
        JPanel panel2= new JPanel();
        JLabel login= new JLabel("Login :");
        JLabel mdp=new JLabel("Mot de passe :");
        plogin=new JTextField();
        pmdp =new JTextField();
        login.setForeground(Color.DARK_GRAY);
        mdp.setForeground(Color.DARK_GRAY);

        //Creation of buttons
        bouton=new JButton("Envoyer");
        bouton.addActionListener(this);
        bouton.setForeground(Color.GRAY);

        //Add components to panels
        panel1.add(login,BorderLayout.LINE_START);
        panel1.add(plogin,BorderLayout.CENTER);
        panel1.add(mdp,BorderLayout.LINE_START);
        panel1.add(pmdp,BorderLayout.CENTER);
        panel2.add(bouton,BorderLayout.CENTER);

        //Add panels to the frame
        fram.getContentPane().add(panel1, BorderLayout.PAGE_START);
        fram.getContentPane().add(panel2, BorderLayout.CENTER);

        //Frame handling
        fram.pack();
        fram.setLocationRelativeTo(null);
    }

/*================Buttons events===========*/
    public void actionPerformed(ActionEvent e) {
        String username= plogin.getText();
        String password=pmdp.getText();
        if (e.getSource()==bouton){
            if (agent.CreateAccount(username,password)){
                    fram.dispose();
            }
        }
    }

/*========GETTERS AND SETTERS========*/
    public JFrame getFrame() { return fram; }
}
