package Test;

import kod.*;

public class TestGeneracji {
    public static void main(String[] args) {
        int n = 0;
        Labirynt l = new Labirynt(25, 25);
        Bfs b = new Bfs(l, 0);
        Dijkstra d = new Dijkstra(l, 0);
        for( int i = 0; i < 1000; i++ ) {
            l.genWagi();
            b.start();
            d.start();
            try {
                b.join();
                d.join();
            } catch (InterruptedException e) {
                e.getLocalizedMessage();
            }
            if( ! b.getSpojny() ) {
                System.err.println("Błędna generacja w n = " + n );
            }

            n++;
        }
    }
}
