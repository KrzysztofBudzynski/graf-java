package kod;

import java.util.Random;

public class Dzielnik extends Thread {
    Labirynt l;
    int h;
    int w;
    int cz;

    public Dzielnik( Labirynt l, int cz ) {
        this.l = l;
        this.cz = cz;
        this.h = l.getH();
        this.w = l.getW();
    }

    @Override
    public void run() {
        for( int i = 1; i < cz; i++ ) {
            System.out.println("dzielenie");
            Random r = new Random();
            int a = Math.abs(r.nextInt() % (int) (h * 0.33)) + (int) (h * 0.33);    // oś y
            int b = Math.abs(r.nextInt() % (int) (w * 0.33)) + (int) (w * 0.33);    // oś x
            // System.out.println(a + " " + b);
            // System.out.println("Wywołanie");
            rekDzielenie(l.getPkt().get(a * w + b));
            //System.out.println("Wywołanie");
            rekDzielenie(l.getPkt().get(a * w + b));
        }
        for( Punkt p : l ) {
            if( p.getEdges().get(1).getWaga() < 0 ) {
                p.remove(1);
            }
            if( p.getEdges().get(2).getWaga() < 0 ) {
                p.remove(2);
            }
        }
        Bfs b = new Bfs( this.l, 0);
        b.start();

    }

    private void rekDzielenie( Punkt p ) {
        //System.out.println("Iteracja " + p);
        int a = p.getWiersz();
        int b = p.getKolumna();
        int k;
        Random r = new Random();
        Punkt next;

        do {
            k = Math.abs(r.nextInt() % 4);
            // System.out.println(k);
            next = p.getEdges().get(k).getTo();
        } while( p.getEdges().get(k).getTo() == null );

        p.mark(1);
        p.mark(2);

        if( a == 0 || a == h-1 || b == 0 || b == w-1 ) return;
        rekDzielenie( next );
    }
}
