package Test;

import kod.Dijkstra;
import kod.Labirynt;

public class TestDijkstra {
    public static void main(String[] args) {
        Labirynt l;
        Dijkstra d;
        for( int i = 0; i < 1000; i++ ) {
            l = new Labirynt(50, 50);
            d = new Dijkstra(l, 0);
            l.genWagi();
            d.start();
            try {
                d.join();
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }
}
