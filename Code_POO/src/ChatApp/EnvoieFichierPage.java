package ChatApp;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;


public class EnvoieFichierPage{
    // Boîte de sélection de fichier à partir du répertoire courant
    public static void main(String[] args) {
        File repertoireCourant = null;
        try {
        // obtention d'un objet File qui désigne le répertoire courant. Le
        // "getCanonicalFile" n'est pas absolument nécessaire mais permet
        // d'éviter les /Truc/./Chose/ ...
        repertoireCourant = new File(".").getCanonicalFile();
        System.out.println("Répertoire courant : " + repertoireCourant);
        } catch(IOException e) {}

        // création de la boîte de dialogue dans ce répertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
        // cas repertoireCourant vaut null)
        JFileChooser dialogue = new JFileChooser(repertoireCourant);

        // affichage
        dialogue.showOpenDialog(null);

        // récupération du fichier sélectionné
        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());

        }
        }
