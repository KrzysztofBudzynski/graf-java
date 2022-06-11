package com.example.grafjava;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kod.Bfs;
import kod.Dijkstra;
import kod.Labirynt;
import kod.Utils;

public class SterGUI {
    public TextField wymiary;
    public TextField nazwa;
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
        String [] words = wymiary.getText().split("\\s+");

        if( words.length != 2 ) {
            System.err.println("Niepoprawne dane w okno nazwa");
            return;
        }

        height = Integer.parseInt(words[0]);
        width = Integer.parseInt(words[1]);

        if( width <= 0 || height <= 0 ) {
            System.err.println("Podano rozmiary mniejsze od 1");
            return;
        }
        l = new Labirynt(height, width);
        l.genWagi();
        System.out.println("Wygenerowano");
    }

    @FXML
    private void inpClicked() {
        try {
            l = Utils.readLabirynt(nazwa.getText());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if( l != null ) {
            System.out.println("Wczytano z: " + nazwa.getText());
        }
    }
}