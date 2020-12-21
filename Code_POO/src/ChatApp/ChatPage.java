package ChatApp;

import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
public class ChatPage extends Thread implements ActionListener {
        ChatHandler chatHandler;
        Agent agent;
        ArrayList<JPanel> listMessage;
        JTextField zone_texte;
        JPanel liste;
        JScrollPane pane;
        JScrollBar bare;
        JFrame fram;
        JButton bouton;
        JButton bouton2;
        public ChatPage(Agent agent, ChatHandler chatHandler){
            this.agent=agent;
            this.chatHandler=chatHandler;
            this.listMessage = new ArrayList<>();
            fram= new JFrame("Chat avec " + chatHandler.getRecipient().username);
        }

        public void run(){

            fram.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fram.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    agent.StopChat(chatHandler);
                    fram.dispose();
                }
            });
            fram.getContentPane().setPreferredSize(new Dimension(300,300));
            JPanel panel1= new JPanel(new GridLayout(1,4));
            JPanel panel2= new JPanel(new GridLayout(1,4));

            //JLabel label1=new JLabel ("Bonjour");
            //label1.setForeground(Color.BLUE);
            bouton=new JButton("Envoyer");
            bouton2=new JButton("Envoie Fichier");
            bouton.addActionListener(this);
            bouton.setForeground(Color.GRAY);
            zone_texte=new JTextField();
            panel2.add(zone_texte,BorderLayout.LINE_START);
            panel2.add(bouton,BorderLayout.CENTER);
            panel2.add(bouton2,BorderLayout.LINE_END);
            //panel1.add(label1, BorderLayout.PAGE_START);
            GridLayout layout = new GridLayout(0,1);
            liste=new JPanel();
            liste.setLayout(layout);
            bare = new JScrollBar(JScrollBar.HORIZONTAL,0,1000,0,1000);
            pane = new JScrollPane(liste);

            pane.setHorizontalScrollBar(bare);

            //fram.getContentPane().add(panel1, BorderLayout.PAGE_START);
            fram.getContentPane().add(panel2, BorderLayout.PAGE_END);
            fram.getContentPane().add(pane,BorderLayout.CENTER);
            fram.pack();
            fram.setVisible(true);
            fram.setLocationRelativeTo(null);}


        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==bouton){
            String getvalue= zone_texte.getText();
            if (getvalue.equals("")){}
            else {System.out.println("Message :"+getvalue);
            zone_texte.setText("");
            chatHandler.Send(getvalue);
            System.out.println("Message envoyé");}}
            else {
                if (e.getSource()==bouton2){

                }
            }
        }
        public JPanel creation (String message, String pseudo, String date, Color couleur ){
            JPanel pane= new JPanel();
            JLabel label2=new JLabel(pseudo + " : ");
            JLabel label3=new JLabel(date);
            JLabel label=new JLabel(message);
            label.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,15));
            label3.setFont(new java.awt.Font(Font.SERIF,Font.ITALIC,10));
            label2.setForeground(couleur);
            pane.setLayout(new FlowLayout(FlowLayout.LEFT));
            pane.add(label2);
            pane.add(label3);
            pane.add(label);
            return pane;
        }
        public void debut (){
            for (int i = 0; i < chatHandler.getMessageHistory().size(); i++) {
                if (chatHandler.getMessageHistory().get(i).getRecipient().getPseudo().equals
                        (agent.getPseudoHandler().getMain_User().getPseudo())) {
                    if (chatHandler.getMessageHistory().get(i) instanceof StringMessage) {
                        StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(i);
                        liste.add(creation(message.getContentString()
                                , chatHandler.getMessageHistory().get(i).getSender().getPseudo(),
                                chatHandler.getMessageHistory().get(i).getFormatTime(),
                                Color.RED), BorderLayout.SOUTH);
                    }
                }
                else {
                    if (chatHandler.getMessageHistory().get(i) instanceof StringMessage) {
                        StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(i);
                        liste.add(creation(message.getContentString()
                                , chatHandler.getMessageHistory().get(i).getSender().getPseudo(),
                                chatHandler.getMessageHistory().get(i).getFormatTime(),
                                Color.BLUE), BorderLayout.SOUTH);
                    }
                }
            }
            liste.updateUI();
        }

        public void Mise_a_jour () {
            int index = chatHandler.getMessageHistory().size() - 1;
            if (chatHandler.getMessageHistory().get(index).getRecipient().getPseudo().equals
                    (agent.getPseudoHandler().getMain_User().getPseudo())) {
                if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                    StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                    liste.add(creation(message.getContentString()
                            , chatHandler.getMessageHistory().get(index).getSender().getPseudo(),
                            chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED), BorderLayout.SOUTH);
                }
            } else {
                if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                    StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                    liste.add(creation(message.getContentString()
                            , chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE), BorderLayout.SOUTH);

                }
            }
            liste.updateUI();
        }

        public JFrame getFram(){
            return this.fram;
        }
    }


