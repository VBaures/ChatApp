package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AuthentificationPage implements ActionListener {

    public void AuthentificationPage (){
    JFrame frame= new JFrame("Application");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(new Dimension(1000,1000));
    JPanel panel1= new JPanel(new GridLayout(2,2));
    JPanel panel2= new JPanel(new GridLayout(2,2));

    JPanel panel3= new JPanel(new GridLayout(1,2));
    //display the window

    JLabel title= new JLabel("Veuillez vous connectez");
    JLabel puser = new JLabel("Username ", SwingConstants.LEFT);
    JTextField pusername= new JTextField(SwingConstants.RIGHT);
    JTextField ppassword= new JTextField(2);
    JLabel ppass = new JLabel("Password", SwingConstants.LEFT);
    JButton bouton= new JButton("S'AUTHENTIFIER");
    panel2.add(title,BorderLayout.PAGE_START);
    panel3.add(title,BorderLayout.CENTER);
    panel1.add(pusername,BorderLayout.EAST);
    panel1.add(puser,BorderLayout.EAST);
        panel1.add(ppassword,BorderLayout.WEST);
        panel1.add(ppass,BorderLayout.WEST);
        panel2.add(bouton, BorderLayout.CENTER);

        frame.getContentPane().add(panel3, BorderLayout.PAGE_START);
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.getContentPane().add(panel2, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        bouton.addActionListener(this );}

        //TODO; a completer quand BDD prete
        public void actionPerformed (ActionEvent e){
            PseudoPage newpage = new PseudoPage();



    }
}


