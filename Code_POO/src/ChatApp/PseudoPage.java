package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PseudoPage implements ActionListener {
    Agent agent;
    JTextField ppseudo;
    public void AuthentificationPage (Agent agent){
        this.agent=agent;
        JFrame frame= new JFrame("Choose pseudo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1000,1000));
        JPanel panel1= new JPanel(new GridLayout(2,2));
        JPanel panel2= new JPanel(new GridLayout(2,2));


        JLabel title= new JLabel("Veuillez rentrer un pseudo");
        JLabel pseudo = new JLabel("Pseudo ", SwingConstants.LEFT);
        ppseudo= new JTextField(SwingConstants.RIGHT);
        JButton bouton= new JButton("S'AUTHENTIFIER");
        panel2.add(title,BorderLayout.PAGE_START);
        panel1.add(pseudo);
        panel1.add(ppseudo);
        panel2.add(bouton, BorderLayout.CENTER);

        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.getContentPane().add(panel2, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        bouton.addActionListener(this );}

    public void actionPerformed (ActionEvent e){
        String getvalue= ppseudo.getText();
        System.out.println("Message :"+getvalue);
        if (agent.getPseudoHandler().VerifyPseudo(getvalue)){
        }

    }
}

