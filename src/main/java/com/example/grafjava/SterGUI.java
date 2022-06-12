package com.example.grafjava;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;

import kod.Bfs;
import kod.Dijkstra;
import kod.Labirynt;
import kod.Utils;
import com.example.grafjava.HelloApplication;

public class SterGUI {
    public TextField szerokosc;
    public TextField wysokosc;
    public String nazwa;
    private Labirynt l = null;
    private Dijkstra d;
    private Bfs b;

    @FXML
    private Label welcomeText;

    @FXML
    private void startClicked() {
        if( l == null ) {
            System.err.println("Nie wczytano żadnego grafu");
            return;
        }
        b = new Bfs(l, 0);
        b.start();
        try {
            b.join();
        } catch (InterruptedException ignored) {}
        if( ! b.getSpojny() ) {
            System.err.println("Graf niespójny, bez wymuszenia, szukanie ścieżki się nie odbywa");
            return;
        }
        d = new Dijkstra(l, 0);
        d.start();
        try {
            d.join();
        } catch (InterruptedException ignored) {}
        System.out.println("Graf przeszukany");
    }

    @FXML
    private void genClicked() {
        int width;
        int height;
        String slowoWys;
        String slowoSzer;
        int maxWymiar = 10000;
        //String [] words = wymiary.getText().split("\\s+");

        // if( words.length != 2 ) {
        //     System.err.println("Niepoprawne dane w okno nazwa");
        //     return;
        // }

        // height = Integer.parseInt(words[0]);
        // width = Integer.parseInt(words[1]);

        slowoWys = wysokosc.getText();
        height = Integer.parseInt(slowoWys);
        if (height > maxWymiar){
            System.err.println("Przekroczono maksymalny wymiar dla wysokosci.");
            return;
        }
        else if (height <= 0){
            System.err.println("Podano wymiar wysokosci nizszy niz 0.");
            return;
        }
        slowoSzer = szerokosc.getText();
        width = Integer.parseInt(slowoSzer);
        if (width > maxWymiar){
            System.err.println("Przekroczono maksymalny wymiar szerokosci.");
            return;
        }
        else if (width <= 0){
            System.err.println("Podano wymiar szerokosci nizszy niz 0.");
            return;
        }

        // if( width <= 0 || height <= 0 ) {
        //     System.err.println("Podano rozmiary mniejsze od 1");
        //     return;
        // }
        l = new Labirynt(height, width);
        l.genWagi();
        System.out.println("Wygenerowano, wysokosc " + height + " szerokosc " + width + "\n");
    }
    @FXML
    private void fileSelect() {
        System.out.println("fileSelect\n");
    }
    //     FileChooser filechooser = new FileChooser();
    //     Stage stage = (Stage) fileSelect.getScene(),getWindow();
    //     File file = filechooser.showOpenDialog();
    //     if (file != null)
    //     {
    //         nazwa = file.getAbsolutePath();
    //     }
    //     System.out.println(nazwa);
    // }
    

    @FXML
    private void inpClicked() {
        try {
           l = Utils.readLabirynt(nazwa);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if( l != null ) {
            System.out.println("Wczytano z: " + nazwa);
        }
    }
}