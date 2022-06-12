package com.example.grafjava;

import java.io.File;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.stage.*;

import kod.*;
import com.example.grafjava.HelloApplication;

public class SterGUI {
    public TextField szerokosc;
    public TextField wysokosc;
    public String nazwa;
    public Canvas canvas;
    public TextField poczatkowy;
    public TextField koncowy;
    public ToggleButton force;
    private Labirynt l = null;
    private Dijkstra d;
    private Bfs b;
    private GraphicsContext grc;

    @FXML
    private Label welcomeText;

    @FXML
    private void startClicked() {
        boolean czyForce = force.isSelected();
        int start = 0;
        int end = Integer.MAX_VALUE;
        if( poczatkowy.getText() != "" ) {
            start = Integer.parseInt(poczatkowy.getText());
        }
        if( koncowy.getText() != "" ) {
            end = Integer.parseInt(koncowy.getText());
        }

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
        d = new Dijkstra(l, start);
        d.start();
        try {
            d.join();
        } catch (InterruptedException ignored) {}
        System.out.println("Graf przeszukany, start = " + start + " koniec = " + end + " force = " + czyForce);
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

        if(Objects.equals(wysokosc.getText(), "") || Objects.equals(szerokosc.getText(), "")) {
            System.err.println("Nie wpisano wartości");
            return;
        }

        slowoWys = wysokosc.getText();
        slowoSzer = szerokosc.getText();

        height = Integer.parseInt(slowoWys);
        if (height > maxWymiar){
            System.err.println("Przekroczono maksymalny wymiar dla wysokosci.");
            return;
        }
        else if (height <= 0){
            System.err.println("Podano wymiar wysokosci nizszy niz 0.");
            return;
        }
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

    private void rysuj() {
        if( l == null ) {
            System.err.println("Nie jest wczytany graf do rysowania");
            return;
        }
        double dx = canvas.getWidth() / l.getW();
        double dy = canvas.getHeight() / l.getH();
        grc = canvas.getGraphicsContext2D();
        for(Punkt p : l ) {

        }
    }
}