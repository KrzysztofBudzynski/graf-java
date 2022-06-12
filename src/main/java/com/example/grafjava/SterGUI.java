package com.example.grafjava;

import java.io.File;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import kod.*;
import com.example.grafjava.HelloApplication;
import com.example.grafjava.*;

public class SterGUI {
    public TextField szerokosc;
    public TextField wysokosc;
    public String nazwa;
    public String nazwaladna;
    public Canvas canvas;
    public TextField poczatkowy;
    public TextField koncowy;
    public ToggleButton force;
    public Label nazwapliku;
    public Button dziel;
    public TextField czesci;
    private Labirynt l = null;
    private Dijkstra d;
    private Bfs b;
    private Dzielnik dz;
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
        if( ! b.getSpojny()  && !czyForce ) {
            System.err.println("Graf niespójny, bez wymuszenia, szukanie ścieżki się nie odbywa");
            return;
        }
        d = new Dijkstra(l, start);
        d.start();
        try {
            d.join();
        } catch (InterruptedException ignored) {}
        System.out.println("Graf przeszukany, start = " + start + " koniec = " + end + " force = " + czyForce);
        grc.setFill(Color.BLACK);
        double dx = (canvas.getWidth() - 50) / l.getW();
        double dy = (canvas.getHeight() - 50)/ l.getH();
        double r = 3;
        //grc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight() );
        rysuj();
        rysujSciezke();
        
        if( end != Integer.MAX_VALUE ) {
            int i = end;
            while( i != start ) {
                System.out.println(i + " " + d.getPrzez()[i]);
                i = d.getPrzez()[i];
            }
        }

        

        // for(Punkt p : l ) {
        //     x = canX + dx * p.getKolumna() + r;
        //     y = canY + dy * p.getWiersz() + r;
        //     p.setPX(x);
        //     p.setPY(y);
        //     grc.fillOval(x - r, y - r, 2 * r, 2 * r);
        //     if( p.getEdges().get(1).getTo() != null ) {
        //         grc.fillRect(x, y, dx, 1);
        //     }
        //     if( p.getEdges().get(2).getTo() != null ) {
        //         grc.fillRect(x, y, 1, dy);
        //     }
        // }

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
        l.gen();
        System.out.println("Wygenerowano, wysokosc " + height + " szerokosc " + width + "\n");
        rysuj();
    }
    @FXML
    private void fileSelect() {
        final Stage openFile = new Stage();
        FileChooser fileChooser = new FileChooser();
        File plik = fileChooser.showOpenDialog(openFile);
        //System.out.println(plik);
        if (plik != null){
        nazwa = plik.getAbsolutePath();
        System.out.println(nazwa);
        nazwaladna = plik.getName();
        System.out.println(nazwaladna);

        nazwapliku.setText(nazwaladna);
        }
        else
        {
            System.err.println("Nie wybrano pliku.\n");
            return;
        }


        //System.out.println(nazwa);
    }
    

    @FXML
    private void inpClicked() {
        System.out.println(nazwa);
        try {
           l = Utils.readLabirynt(nazwa);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if( l != null ) {
            System.out.println("Wczytano z: " + nazwa + " h = " + l.getH() + " w = " + l.getW());
        }
        rysuj();
    }

    private void rysuj() {
        if( l == null ) {
            System.err.println("Nie jest wczytany graf do rysowania");
            return;
        }
        double canX = canvas.getLayoutX();
        double canY = canvas.getLayoutY();
        double dx = (canvas.getWidth() - 50) / l.getW();
        double dy = (canvas.getHeight() - 50)/ l.getH();
        double x, y;
        double r = 3;
        System.out.println(canX + " " + canY + " " + dx + " " + dy);
        grc = canvas.getGraphicsContext2D();
        grc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grc.setFill(Color.BLACK);
        for(Punkt p : l ) {
            x = canX + dx * p.getKolumna() + r;
            y = canY + dy * p.getWiersz() + r;
            p.setPX(x);
            p.setPY(y);
            grc.fillOval(x - r, y - r, 2 * r, 2 * r);
            if( p.getEdges().get(1).getTo() != null ) {
                grc.fillRect(x, y, dx, 1);
            }
            if( p.getEdges().get(2).getTo() != null ) {
                grc.fillRect(x, y, 1, dy);
            }
        }
    }
    @FXML
    private void rysujSciezke() {
        double canX = canvas.getLayoutX();
        double canY = canvas.getLayoutY();
        double dx = (canvas.getWidth() - 50) / l.getW();
        double dy = (canvas.getHeight() - 50)/ l.getH();
        double x, y, x2, y2;
        double r = 3;
        int start = 0;
        int end = Integer.MAX_VALUE;
        Punkt p, p2;
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
        grc.setFill(Color.RED);
        if( end != Integer.MAX_VALUE ) {
            int i = end;
            while( i != start ) {
                p = l.getPkt().get(i);
                p2 = l.getPkt().get(d.getPrzez()[i]);
                x = canX + dx * p.getKolumna() + r;
                y = canY + dy * p.getWiersz() + r;
                p.setPX(x);
                p.setPY(y);
                p2.setPX(x);
                p2.setPY(y);
                x2 = canX + dx * p2.getKolumna() + r;
                y2 = canY + dy * p2.getWiersz() + r;

                grc.fillOval(x - r, y - r, 2 * r, 2 * r);
                grc.fillOval(x2 - r, y2 - r, 2 * r, 2 * r);
                if( p.getEdges().get(1).getTo() != null) {
                    if (p.getEdges().get(1).getTo().getIndex() == d.getPrzez()[i])
                    {
                        grc.fillRect(x, y, dx, 1);
                    }
                }
                if( p.getEdges().get(2).getTo() != null) {
                    if (p.getEdges().get(2).getTo().getIndex() == d.getPrzez()[i])
                    {
                        grc.fillRect(x, y, 1, dy);
                    }
                }
                if (p2.getEdges().get(1).getTo() != null) {
                    if (p2.getEdges().get(1).getTo().getIndex() == i)
                    {
                        grc.fillRect(x2, y2, dx, 1);
                    }
                }
                if (p2.getEdges().get(2).getTo() != null) {
                    if (p2.getEdges().get(2).getTo().getIndex() == i)
                    {
                        grc.fillRect(x2, y2, 1, dy);
                    }
                }
                i = d.getPrzez()[i];

            }
        }
    }

    @FXML
    private void dzielClicked() {
        if( l == null ) {
            System.err.println("Nie ma nic do dzielenia");
            return;
        }
        int n = Integer.parseInt(czesci.getText());
        if( n <= 1 ) {
            System.err.println("Nie można dzielić na mniej niż 2 części");
            return;
        }
        dz = new Dzielnik(l, n);
        dz.start();
        try {
            dz.join();
        } catch (InterruptedException e) {
            System.err.println(e.getLocalizedMessage());
        }
        rysuj();
    }
}