/*
    Classe gérant une session de chat entre deux utilisateurs,
     de l'ouverture à la fermeture de la  page de chat
 */

package ChatApp;

import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends Thread implements ActionListener{

        //déclaration descomposant et des objets
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
        JMenuBar barre_menu;
        JMenu m1;

        //déclaration du constructeur de la classe
        public ChatPage(Agent agent, ChatHandler chatHandler){
            this.agent=agent;
            this.chatHandler=chatHandler;
            this.listMessage = new ArrayList<>();
            fram= new JFrame("Chat avec " + chatHandler.getRecipient().getPseudo());
        }

        //implémentation de la méthode run
        public void run(){
            fram.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fram.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    agent.StopChat(chatHandler);
                    fram.dispose();
                }
            });

            //gestion fenêtre
            fram.getContentPane().setPreferredSize(new Dimension(400,400));


            //gestion et création des composants
            JPanel panel1= new JPanel(new GridLayout(1,1));
            JPanel panel2= new JPanel(new GridLayout(1,4));
            bouton=new JButton("Envoyer");
            bouton2=new JButton("Envoyer Fichier");
            bouton.addActionListener(this);
            bouton2.addActionListener(this);
            bouton.setForeground(Color.GRAY);

            zone_texte=new JTextField();

            barre_menu=new JMenuBar();
            m1= new JMenu("Stop Chat");
            m1.setForeground(Color.RED);
            barre_menu.add(m1);
            m1.addActionListener(this);
            //ajout composant panels
            panel2.add(zone_texte,BorderLayout.LINE_START);
            panel2.add(bouton,BorderLayout.CENTER);
            panel2.add(bouton2,BorderLayout.LINE_END);


            //gestion de l'affichage des message avec un affichage en mode déroulant vertical
            GridLayout layout = new GridLayout(0,1);
            liste=new JPanel();
            liste.setLayout(layout);
            bare = new JScrollBar(JScrollBar.HORIZONTAL,0,1000,0,1000);
            pane = new JScrollPane(liste);
            pane.setHorizontalScrollBar(bare);

            //ajout panels dans la fenetre
            fram.getContentPane().add(panel2, BorderLayout.PAGE_END);
            fram.getContentPane().add(pane,BorderLayout.CENTER);

            //gestion fenetre
            fram.getContentPane().add(BorderLayout.NORTH,barre_menu);
            fram.pack();
            fram.setVisible(true);
            fram.setLocationRelativeTo(null);}

        //gestion du bouton: on peut envoyer soit des messages soit des fichiers
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==bouton){
                String getvalue= zone_texte.getText();
                if (getvalue.equals("")){}
                else {
                    System.out.println("Message :"+getvalue);
                    zone_texte.setText("");
                    chatHandler.Send(getvalue);
                    System.out.println("Message envoyé et enregisté");}
                }
            else if (e.getSource()==bouton2){
                JFileChooser dialogue = new JFileChooser();
                dialogue.showOpenDialog(null);
                chatHandler.Send(dialogue.getSelectedFile());
            }
            else{
                    try {
                        chatHandler.StopChat();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    fram.dispose();
                    System.exit(0);
            }
        }

        /*déclaration de la méthode creation qui permet de crée le message entier envoyé avec le
        nom de l'envoyeur, la date et le contenu du message */
        public JPanel creation (String message, String pseudo, String date, Color couleur ){
            JPanel pane= new JPanel();
            JLabel label2=new JLabel(pseudo + " : ");
            JLabel label3=new JLabel(date);
            JLabel label=new JLabel(message);
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openFile(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            label.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,15));
            label3.setFont(new java.awt.Font(Font.SERIF,Font.ITALIC,10));
            label2.setForeground(couleur);
            pane.setLayout(new FlowLayout(FlowLayout.LEFT));
            pane.add(label2);
            pane.add(label3);
            pane.add(label);
            listMessage.add(pane);
            return pane;
        }

        /*délaration de la méthode debut qui permet d'aller chercher l'éventuel historique
        des messages envoyés entre les deux utilisteurs */
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
            if (liste!=null) {
                liste.updateUI();
            }
        }

        //declaration de la méthode Mise_a_jour qui rajoute le message envoyé à la liste des messages envoyées
        public void Mise_a_jour () {
            int index = chatHandler.getMessageHistory().size() - 1;
            if (chatHandler.getMessageHistory().get(index).getRecipient().getPseudo().equals
                    (agent.getPseudoHandler().getMain_User().getPseudo())) {
                if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                    StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                   JPanel panel = creation(message.getContentString()
                           , chatHandler.getMessageHistory().get(index).getSender().getPseudo(),
                    chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED);
                    liste.add(panel, BorderLayout.SOUTH);
                }
                else if (chatHandler.getMessageHistory().get(index) instanceof FileMessage) {
                    FileMessage message = (FileMessage) chatHandler.getMessageHistory().get(index);
                    JPanel panel = creation("Fichier: " + message.getFileName()
                            , chatHandler.getMessageHistory().get(index).getSender().getPseudo(),
                            chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED);
                    liste.add(panel, BorderLayout.SOUTH);
                }
            } else {
                if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                    StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                    liste.add(creation(message.getContentString()
                            , chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE), BorderLayout.SOUTH);

                }
                else if (chatHandler.getMessageHistory().get(index) instanceof FileMessage) {
                    FileMessage message = (FileMessage) chatHandler.getMessageHistory().get(index);
                    JPanel panel = creation("Fichier: " + message.getFileName()
                            , chatHandler.getMessageHistory().get(index).getSender().getPseudo(),
                            chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE);
                    liste.add(panel, BorderLayout.SOUTH);
                }
            }
            liste.updateUI();
        }

        public JFrame getFram(){
            return this.fram;
        }

    public void openFile(MouseEvent e) {
        Message message = chatHandler.getMessageHistory().get(listMessage.indexOf(e.getSource())%listMessage.size());
        if (message instanceof FileMessage){
            FileMessage fileMessage = (FileMessage) message;
            JFileChooser dialogue = new JFileChooser();
            dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            dialogue.showOpenDialog(e.getComponent());
            dialogue.getCurrentDirectory();
            System.out.println("Enregistrement fichier sous: " + dialogue.getSelectedFile().toString()+"\\"+fileMessage.getFileName());
            try {
                FileOutputStream stream = new FileOutputStream(dialogue.getSelectedFile().toString()+"\\"+fileMessage.getFileName());
                stream.write(fileMessage.getContentFile());
                stream.close();
                System.out.println("Fin enregistrement");
            } catch (FileNotFoundException f){

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}


