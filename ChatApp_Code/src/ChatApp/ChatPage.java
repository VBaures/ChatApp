/*
This class display the chat session between two users
@author Alicia Calmet
@date 2021-02-13
*/

package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ChatPage extends Thread implements ActionListener{
    private ChatHandler chatHandler;
    private Agent agent;
    private ArrayList<JPanel> listMessage;
    private JTextField zone_texte;
    private JPanel liste = new JPanel();
    private JScrollPane pane;
    private JScrollBar bare;
    private JFrame fram;
    private JButton bouton;
    private JButton bouton2;
    private JMenuBar barre_menu;
    private JMenu m1;

/*=========CONSTRUCTOR========*/
    public ChatPage(Agent agent, ChatHandler chatHandler){
        this.agent=agent;
        this.chatHandler=chatHandler;
        this.listMessage = new ArrayList<>();
        fram= new JFrame("Chat avec " + chatHandler.getRecipient().getPseudo());
    }
/*=========RUN METHOD========*/
    public void run(){
        //Frame Creation
        fram.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fram.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                agent.StopChat(chatHandler);
                fram.dispose();
            }
        });
        fram.getContentPane().setPreferredSize(new Dimension(400,400));

        //Handling and creation of components
        JPanel panel1= new JPanel(new GridLayout(1,1));
        JPanel panel2= new JPanel(new GridLayout(1,4));
        zone_texte=new JTextField();
        barre_menu=new JMenuBar();
        m1= new JMenu("Stop Chat");
        m1.setForeground(Color.RED);
        barre_menu.add(m1);
        m1.addActionListener(this);

        //Creation of buttons
        bouton=new JButton("Envoyer");
        bouton2=new JButton("Envoyer Fichier");
        bouton.addActionListener(this);
        bouton2.addActionListener(this);
        bouton.setForeground(Color.GRAY);

        //Add components to panels
        panel2.add(zone_texte,BorderLayout.LINE_START);
        panel2.add(bouton,BorderLayout.CENTER);
        panel2.add(bouton2,BorderLayout.LINE_END);

        //Handle scrolling display
        GridLayout layout = new GridLayout(0,1);
        liste=new JPanel();
        liste.setLayout(layout);
        bare = new JScrollBar(JScrollBar.HORIZONTAL,0,1000,0,1000);
        debut();
        pane = new JScrollPane(liste);
        pane.setHorizontalScrollBar(bare);

        //Add panels to the frame
        fram.getContentPane().add(panel2, BorderLayout.PAGE_END);
        fram.getContentPane().add(pane,BorderLayout.CENTER);

        //Frame handling
        fram.getContentPane().add(BorderLayout.NORTH,barre_menu);
        fram.pack();
        fram.setVisible(true);
        fram.setLocationRelativeTo(null);}

/*================Buttons events===========*/
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==bouton){
            String getvalue= zone_texte.getText();
            if (getvalue.equals("")){}
            else {
                zone_texte.setText("");
                chatHandler.Send(getvalue);
            }
        } else if (e.getSource()==bouton2){
            JFileChooser dialogue = new JFileChooser();
            dialogue.showOpenDialog(null);
            chatHandler.Send(dialogue.getSelectedFile());
        }else{
            try {
                chatHandler.StopChat();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            fram.dispose();
            System.exit(0);
        }
    }

/*Function creating a JPanel displaying a message */
    private JPanel creation (String message, String pseudo, String date, Color couleur ){
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
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
        label.setFont(new Font(Font.SERIF,Font.BOLD,15));
        label3.setFont(new Font(Font.SERIF,Font.ITALIC,10));
        label2.setForeground(couleur);
        pane.setLayout(new FlowLayout(FlowLayout.LEFT));
        pane.add(label2);
        pane.add(label3);
        pane.add(label);
        listMessage.add(pane);
        return pane;
    }

/* Function adding all the message of the history */
    public void debut (){
        for (int index = 0; index < chatHandler.getMessageHistory().size(); index++) {
            if (chatHandler.getMessageHistory().get(index).getRecipient().getPseudo().equals(agent.getPseudoHandler().getMain_User().getPseudo())) {
                if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                    StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                    JPanel panel = creation(message.getContentString(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED);
                    this.liste.add(panel, BorderLayout.SOUTH);
                }
                else if (chatHandler.getMessageHistory().get(index) instanceof FileMessage) {
                    FileMessage message = (FileMessage) chatHandler.getMessageHistory().get(index);
                    JPanel panel = creation("Fichier: " + message.getFileName(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED);
                    this.liste.add(panel, BorderLayout.SOUTH);
                }
            } else {
                if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                    StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                    JPanel panel = creation(message.getContentString(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE);this.liste.add(panel, BorderLayout.SOUTH);
                }
                else if (chatHandler.getMessageHistory().get(index) instanceof FileMessage) {
                    FileMessage message = (FileMessage) chatHandler.getMessageHistory().get(index);
                    JPanel panel = creation("Fichier: " + message.getFileName(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE);
                    this.liste.add(panel, BorderLayout.SOUTH);
                }
            }
        }
    }

 /* Function adding a new message to the display */
    public void Mise_a_jour () {
        int index = chatHandler.getMessageHistory().size() - 1;
        if (chatHandler.getMessageHistory().get(index).getRecipient().getPseudo().equals(agent.getPseudoHandler().getMain_User().getPseudo())) {
            if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                JPanel panel = creation(message.getContentString(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED);
                this.liste.add(panel, BorderLayout.SOUTH);
            } else if (chatHandler.getMessageHistory().get(index) instanceof FileMessage) {
                FileMessage message = (FileMessage) chatHandler.getMessageHistory().get(index);
                JPanel panel = creation("Fichier: " + message.getFileName(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.RED);
                this.liste.add(panel, BorderLayout.SOUTH);
            }
        } else {
            if (chatHandler.getMessageHistory().get(index) instanceof StringMessage) {
                StringMessage message = (StringMessage) chatHandler.getMessageHistory().get(index);
                this.liste.add(creation(message.getContentString(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE), BorderLayout.SOUTH);
            }else if (chatHandler.getMessageHistory().get(index) instanceof FileMessage) {
                FileMessage message = (FileMessage) chatHandler.getMessageHistory().get(index);
                JPanel panel = creation("Fichier: " + message.getFileName(), chatHandler.getMessageHistory().get(index).getSender().getPseudo(), chatHandler.getMessageHistory().get(index).getFormatTime(), Color.BLUE);
                this.liste.add(panel, BorderLayout.SOUTH);
            }
        }
        this.liste.updateUI();
    }

    /* Function opening a receive file */
    public void openFile(MouseEvent e) {
        Message message = chatHandler.getMessageHistory().get((listMessage.indexOf(e.getSource())+listMessage.size())%listMessage.size());
        if (message instanceof FileMessage){
            FileMessage fileMessage = (FileMessage) message;
            JFileChooser dialogue = new JFileChooser();
            dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            dialogue.showOpenDialog(e.getComponent());
            dialogue.getCurrentDirectory();
            try {
                FileOutputStream stream = new FileOutputStream(dialogue.getSelectedFile().toString()+"\\"+fileMessage.getFileName());
                stream.write(fileMessage.getContentFile());
                stream.close();
            } catch (FileNotFoundException f){

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
/*=========GETTERS AND SETTERS==========*/
    public JFrame getFram(){
        return this.fram;
    }
}


