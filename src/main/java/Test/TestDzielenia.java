package Test;

import kod.Dijkstra;
import kod.Dzielnik;
import kod.Labirynt;

public class TestDzielenia {
    public static void main(String[] args) {
        Labirynt l;
        Dzielnik d;
        for( int i = 0; i < 1000; i++ ) {
            l = new Labirynt(30, 30);
            l.genWagi();
            d = new Dzielnik(l, 2);
            d.start();
            try {
                d.join();
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }
}
