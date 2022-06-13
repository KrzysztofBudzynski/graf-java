package kod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dzielnik extends Thread {
    Labirynt l;
    double [] kopia1;
    double [] kopia2;
    int h;
    int w;
    int cz;

    public Dzielnik( Labirynt l, int cz ) {
        this.l = l;
        kopia1 = new double[l.getN()];
        kopia2 = new double[l.getN()];
        this.cz = cz;
        this.h = l.getH();
        this.w = l.getW();
    }

    @Override
    public void run() {
        for( int i = 1; i < cz; i++ ) {
            System.out.println("dzielenie na " + cz);
            Random r = new Random();
            int a = Math.abs(r.nextInt() % (int) (h * 0.33)) + (int) (h * 0.33);    // oś y
            int b = Math.abs(r.nextInt() % (int) (w * 0.33)) + (int) (w * 0.33);    // oś x
            rekDzielenie(l.getPkt().get(a * w + b));
            rekDzielenie(l.getPkt().get(a * w + b));
        }
        fill();
        for( Punkt p : l ) {
            if( p.getEdges().get(1).getWaga() < 0 ) {
                p.remove(1);
            }
            if( p.getEdges().get(2).getWaga() < 0 ) {
                p.remove(2);
            }
        }
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
            next = p.getEdges().get(k).getTo();
        } while( p.getEdges().get(k).getTo() == null );
        if( p.getEdges().get(1).getWaga() > 0 && p.getEdges().get(2).getWaga() > 0 ) {
            kopia1[p.getIndex()] = p.getEdges().get(1).getWaga();
            kopia2[p.getIndex()] = p.getEdges().get(2).getWaga();
        }
        p.mark(1);
        p.mark(2);

        if( a == 0 || a == h-1 || b == 0 || b == w-1 ) return;
        rekDzielenie( next );
    }
    private void rekDzieleniePrawo( Punkt p ) {
        //System.out.println("Iteracja " + p);
        int a = p.getWiersz();
        int b = p.getKolumna();
        int k;
        Random r = new Random();
        Punkt next;

        do {
            k = Math.abs(r.nextInt() % 3);
            next = p.getEdges().get(k).getTo();
        } while( p.getEdges().get(k).getTo() == null );
        if( p.getEdges().get(1).getWaga() > 0 && p.getEdges().get(2).getWaga() > 0 ) {
            kopia1[p.getIndex()] = p.getEdges().get(1).getWaga();
            kopia2[p.getIndex()] = p.getEdges().get(2).getWaga();
        }
        p.mark(1);
        p.mark(2);

        if( a == 0 || a == h-1 || b == 0 || b == w-1 ) return;
        rekDzielenie( next );
    }
    private void rekDzielenieLewo( Punkt p ) {
        //System.out.println("Iteracja " + p);
        int a = p.getWiersz();
        int b = p.getKolumna();
        int k;
        Random r = new Random();
        Punkt next;

        do {
            k = Math.abs(r.nextInt() % 3);
            if( k == 1 ) k = 3;
            next = p.getEdges().get(k).getTo();
        } while( p.getEdges().get(k).getTo() == null );
        if( p.getEdges().get(1).getWaga() > 0 && p.getEdges().get(2).getWaga() > 0 ) {
            kopia1[p.getIndex()] = p.getEdges().get(1).getWaga();
            kopia2[p.getIndex()] = p.getEdges().get(2).getWaga();
        }
        p.mark(1);
        p.mark(2);

        if( a == 0 || a == h-1 || b == 0 || b == w-1 ) return;
        rekDzielenie( next );
    }

    private void fill() {       // (int i = l.getN() - 1; i >= 0; i--) || (int i = 0; i < l.getN(); i++ )
        for (int i = l.getN() - 1; i >= 0; i--) {
            if( l.getPkt().get(i).czySam() ) {
                l.getPkt().get(i).getEdges().get(1).setWaga(kopia1[l.getPkt().get(i).getIndex()]);
                l.getPkt().get(i).getEdges().get(1).getTo().getEdges().get(3).setWaga(kopia1[l.getPkt().get(i).getIndex()]);
                /*l.getPkt().get(i).getEdges().get(3).setWaga(kopia1[l.getPkt().get(i).getIndex()]);
                l.getPkt().get(i).getEdges().get(3).getTo().getEdges().get(1).setWaga(kopia1[l.getPkt().get(i).getIndex()]);*/
/*                l.getPkt().get(i).getEdges().get(2).setWaga(kopia2[l.getPkt().get(i).getIndex()]);
                l.getPkt().get(i).getEdges().get(2).getTo().getEdges().get(0).setWaga(kopia2[l.getPkt().get(i).getIndex()]);*/
                System.out.println("poprawiono nr " + l.getPkt().get(i).getIndex() );
            }
        }   //  && p.getEdges().get(1).getTo() != null && p.getEdges().get(2).getTo() != null
    }
}
