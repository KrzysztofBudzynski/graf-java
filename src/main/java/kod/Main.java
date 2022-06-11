package kod;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Labirynt l = new Labirynt(10, 10);

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
        System.out.println("stan spojnosci grafu:" + bfs.getSpojny());
    }
}
