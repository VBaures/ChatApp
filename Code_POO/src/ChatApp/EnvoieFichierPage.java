package ChatApp;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;


public class EnvoieFichierPage implements ActionListener {
    // Boîte de sélection de fichier à partir du répertoire courant
    JFrame fenetre;
    JFileChooser fc;
    public EnvoieFichierPage() {
        fenetre = new JFrame();
        fenetre.setSize(200,200);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
        fenetre.setLocationRelativeTo(null);

        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Ficher");
        JMenuItem Ouvrir = new JMenuItem("Ouvrir");
        jm.add(Ouvrir);
        jmb.add(jm);

        fenetre.add(jmb,BorderLayout.NORTH);

        fc = new JFileChooser();

        Ouvrir.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int val_retour = fc.showOpenDialog(fenetre);

        if (val_retour == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //afficher le nom du fichier
            System.out.println("Nom du fichier : "+file.getName()+"\n");
            //afficher le chemin absolu du fichier
            System.out.println("Chemin absolu : "+file.getAbsolutePath()+"\n");
        } else {
            System.out.println("L'ouverture est annulée\n");
        }
    }
}