package com.example.grafjava;

import kod.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        

    }

    public static void main(String[] args) {
        launch();
        Labirynt l = new Labirynt(10, 10);

        if( args.length == 1 ) l.genWagi();

        if (args.length == 2) {
            try {
                l = Utils.readLabirynt(args[1]);
            } catch (IOException e) {
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
        System.out.println("stan spojnosci grafu:" + bfs.getSpojny());
    }
}