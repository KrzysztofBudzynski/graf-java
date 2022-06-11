package com.example.grafjava;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import kod.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    private int width = 800;
    private int height = 1000;

    private Button runButton;
    private Labirynt l;
    private Dijkstra d;
    private Bfs b;
    private Canvas canvas;
    private GraphicsContext grc;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Graf");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
/*        Labirynt l = new Labirynt(10, 10);

        if( args.length == 1 ) l.genWagi();

        if (args.length == 2) {
            try {
                l = Utils.readLabirynt(args[1]);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        System.out.println("wczytano " + l.getN());
        System.out.println(l.testWag());

        Dijkstra d = new Dijkstra(l, 0);
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            System.err.println(e.getLocalizedMessage());
        }
        l.dziel(5);
        Bfs bfs = new Bfs(l, 0);
        bfs.start();
        System.out.println("stan spojnosci grafu:" + bfs.getSpojny());*/
    }
}