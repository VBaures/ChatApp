/*
This class handle the windows that allows the user to choose his pseudo

@author Alicia Calmet
@date 2021-02-13
*/
package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class PseudoPage implements ActionListener {
    private Agent agent;
    private JTextField ppseudo;
    private JFrame frame;
    private JButton bouton1;

/*=============CONSTRUCTOR=============*/
    public PseudoPage (Agent agent){
        this.agent=agent;

        //Frame Creation
        frame= new JFrame("Choose pseudo");
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

        //Handling and creation of components
        JPanel panel1= new JPanel(new BorderLayout());
        JPanel panel2= new JPanel(new BorderLayout());
        JLabel title= new JLabel("Veuillez rentrer un pseudo");
        JLabel pseudo = new JLabel("Pseudo ", SwingConstants.LEFT);
        ppseudo= new JTextField(SwingConstants.RIGHT);

        //Creation of buttons
        bouton1= new JButton("Valider");
        bouton1.addActionListener(this );

        //Add components to panels
        panel1.add(title,BorderLayout.PAGE_START);
        panel1.add(pseudo,BorderLayout.LINE_START);
        panel1.add(ppseudo,BorderLayout.CENTER);
        panel2.add(bouton1, BorderLayout.PAGE_START);

        //Add panels to the frame
        frame.getContentPane().add(panel1, BorderLayout.PAGE_START);
        frame.getContentPane().add(panel2, BorderLayout.PAGE_END);

        //Frame handling
        frame.pack();
        frame.setLocationRelativeTo(null); }

/*================Buttons events===========*/
    public void actionPerformed (ActionEvent e){
        String getvalue= ppseudo.getText();
        if (agent.getPseudoHandler().VerifyPseudo(getvalue)){
            agent.getPseudoHandler().getMain_User().setPseudo(getvalue);
            try {
                if (agent.getPseudoHandler().getMain_User().getPlace().equals("indoor")){
                    agent.getNetworkHandler().getServerHandler().getUdp().broadcastUDP("NewPseudo", agent.getPseudoHandler().getMain_User());
                    agent.getNetworkHandler().getRemoteHandler().notifyServer();
                } else if (agent.getPseudoHandler().getMain_User().getPlace().equals("remote")){
                    agent.getNetworkHandler().getRemoteHandler().notifyServer();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.setVisible(false);
            agent.getUsersWindows().getFrame().setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Pseudo déjà utilisé");
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}

