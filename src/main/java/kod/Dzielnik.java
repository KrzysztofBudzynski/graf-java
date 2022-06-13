package kod;

import java.util.Random;

public class Dzielnik extends Thread {
    private Labirynt l;
    private double [] kopia1;
    private double [] kopia2;
    private int cz;
    private Dijkstra d;

    public Dzielnik( Labirynt l, int cz ) {
        this.l = l;
        kopia1 = new double[l.getN()];
        kopia2 = new double[l.getN()];
        this.cz = cz;
        Random r = new Random();
        this.d = new Dijkstra(l, Math.abs(r.nextInt() % l.getH()) * l.getW());
        l.setCz(cz);
    }

    @Override
    public void run() {
        for (int i = 1; i < cz; i++) {
            Random r = new Random();
            int kon = (Math.abs(r.nextInt() % (l.getH() - 1)) + 1) * l.getW() - 1;
            d.start();
            try {
                d.join();
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
            tnij( l.getPkt().get(kon) );
        }
        fill();
        clear();
    }

    private void tnij( Punkt curr ) {
        int ind = curr.getIndex();
        curr.mark(1);
        curr.mark(2);

        if( curr.getIndex() == d.getsIndex() ) return;
        if( d.getPrzez()[ind] == Integer.MAX_VALUE) return;

        curr = l.getPkt().get(d.getPrzez()[ind]);
        tnij(curr);
    }

    private void fill() {       // (int i = l.getN() - 1; i >= 0; i--) || (int i = 0; i < l.getN(); i++ )
        for (int i = l.getN() - 1; i >= 0; i--) {
            Punkt p = l.getPkt().get(i);
            if( p.czySam() ) {
                if( p.getEdges().get(1).getTo() != null ) {
                    p.getEdges().get(1).setWaga(kopia1[p.getIndex()]);
                    p.getEdges().get(1).getTo().getEdges().get(3).setWaga(kopia1[p.getIndex()]);
                } else if( p.getEdges().get(2).getTo() != null ) {
                    p.getEdges().get(2).setWaga(kopia1[p.getIndex()]);
                    p.getEdges().get(2).getTo().getEdges().get(0).setWaga(kopia2[p.getIndex()]);
                }
            }
        }
    }

    private void clear() {
        for( Punkt p : l ) {
            if( p.getEdges().get(1).getWaga() < 0 ) {
                p.remove(1);
            }
            if( p.getEdges().get(2).getWaga() < 0 ) {
                p.remove(2);
            }
        }
    }
}
