package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
public class ChatPage extends Thread implements ActionListener {
        ChatHandler chatHandler;
        Agent agent;
        ArrayList<JPanel> listMessage;
        JTextField zone_texte;
        JPanel liste;
        JScrollPane pane;

        public ChatPage(Agent agent, ChatHandler chatHandler){
            this.agent=agent;
            this.chatHandler=chatHandler;
            this.listMessage = new ArrayList<>();
        }

        public void run(){

            JFrame fram= new JFrame("Application");
            fram.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fram.setSize(new Dimension(1000,1000));
            JPanel panel1= new JPanel(new GridLayout(1,4));
            JPanel panel2= new JPanel(new GridLayout(1,4));

            //JLabel label1=new JLabel ("Bonjour");
            //label1.setForeground(Color.BLUE);
            JButton bouton=new JButton("Envoyer");
            bouton.addActionListener(this);
            bouton.setForeground(Color.GRAY);
            zone_texte=new JTextField();
            panel2.add(zone_texte);
            panel2.add(bouton);
            //panel1.add(label1, BorderLayout.PAGE_START);
            GridLayout layout = new GridLayout(0,1);
            liste=new JPanel();
            liste.setLayout(layout);
            pane = new JScrollPane(liste);
            pane.setSize(300,300);
            //fram.getContentPane().add(panel1, BorderLayout.PAGE_START);
            fram.getContentPane().add(panel2, BorderLayout.PAGE_END);
            fram.getContentPane().add(pane,BorderLayout.PAGE_START);
            fram.pack();
            fram.setVisible(true);
            fram.setLocationRelativeTo(null);}


        public void actionPerformed(ActionEvent e) {
            String getvalue= zone_texte.getText();
            System.out.println("Message :"+getvalue);
            chatHandler.Send(getvalue);
            System.out.println("Message envoyé");
        }
        public JPanel creation (String message, String pseudo /*Date date*/, Color couleur ){
            JPanel pane= new JPanel();
            JLabel label2=new JLabel(pseudo + " : ");
            //JLabel label3=new JLabel(date.toString());
            JLabel label=new JLabel(message);
            label.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,15));
            label2.setForeground(couleur);
            pane.setLayout(new FlowLayout(FlowLayout.LEFT));
            pane.add(label2);
            pane.add(label);
            //pane.add(label3,BorderLayout.PAGE_START);
            return pane;
        }
        public void debut (){
            for (int i = 0; i < chatHandler.getMessageHistory().size(); i++) {
                if (chatHandler.getMessageHistory().get(i).getRecipient().getPseudo().equals
                        (agent.getPseudoHandler().getMain_User().getPseudo())) {

                    liste.add(creation(chatHandler.getMessageHistory().get(i).getContent()
                            ,chatHandler.getMessageHistory().get(i).getSender().getPseudo(),
                            /*chatHandler.getMessageHistory().get(i).getTime(),*/
                            Color.RED),BorderLayout.SOUTH);
                }
                else {liste.add(creation(chatHandler.getMessageHistory().get(i).getContent()
                        ,chatHandler.getMessageHistory().get(i).getSender().getPseudo(),
                        /*chatHandler.getMessageHistory().get(i).getTime(),*/
                        Color.BLUE),BorderLayout.SOUTH);}
            }
            liste.updateUI();
        }

        public void Mise_a_jour () {
            int index = chatHandler.getMessageHistory().size() - 1;
            if (chatHandler.getMessageHistory().get(index).getRecipient().getPseudo().equals
                    (agent.getPseudoHandler().getMain_User().getPseudo())) {

                liste.add(creation(chatHandler.getMessageHistory().get(index).getContent()
                        , chatHandler.getMessageHistory().get(index).getSender().getPseudo(),
                        Color.RED),BorderLayout.SOUTH);
            } else {
                liste.add(creation(chatHandler.getMessageHistory().get(index).getContent()
                        , chatHandler.getMessageHistory().get(index).getSender().getPseudo(), Color.BLUE),BorderLayout.SOUTH);
            }
            liste.updateUI();
        }
    }


