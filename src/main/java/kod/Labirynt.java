package kod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Labirynt implements Iterable<Punkt>{
    private final int h;
    private final int w;
    private final int n;
    private int cz; //ile części

    private List<Punkt> pkt;

    public Labirynt( int h, int w ) {
        this.h = h;
        this.w = w;
        this.n = w * h;
        pkt = new ArrayList<>();
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                pkt.add(new Punkt(i, j, w));
            }
        }
        for( int i = 0; i < n; i++ ) {
            if(i-w < n && i-w >= 0) {
                pkt.get(i).getEdges().add(new Edge(pkt.get(i), pkt.get(i-w)));
            } else pkt.get(i).getEdges().add(new Edge());
            if((i + 1) % w != 0) {
                pkt.get(i).getEdges().add(new Edge(pkt.get(i), pkt.get(i+1)));
            } else pkt.get(i).getEdges().add(new Edge());
            if(i+w < n && i+w >= 0) {
                pkt.get(i).getEdges().add(new Edge(pkt.get(i), pkt.get(i+w)));
            } else pkt.get(i).getEdges().add(new Edge());
            if(i % w != 0) {
                pkt.get(i).getEdges().add(new Edge(pkt.get(i), pkt.get(i-1)));
            } else pkt.get(i).getEdges().add(new Edge());
        }
        this.cz = 1;
    }

    public void genWagi() {
        Random r = new Random();
        for( Punkt p : pkt ) {
            for( int i = 0; i < 2; i++ ) {
                if( p.getEdges().get(i).getTo() != null ) {
                    p.getEdges().get(i).setWaga(r.nextDouble());
                    p.getEdges().get(i).getTo().getEdges().get(i+2)
                            .setWaga(p.getEdges().get(i).getWaga());
                }
            }
        }
    }

    public boolean testWag() {      // raczej działa
        for( int i = 0; i < this.n; i++ ) {
            for( int k = 0; k < 2; k++ ) {
                if( pkt.get(i).getEdges().get(k).getTo() != null ) {
                    if( pkt.get(i).getEdges().get(k).getWaga() !=
                        pkt.get(i).getEdges().get(k).getTo().getEdges().get(k+2).getWaga())
                        return false;
                }
            }
        }
        return true;
    }

    public void dziel( int cz ) {
        this.cz = cz;
        dzielThread.start();
    }

     private Thread dzielThread = new Thread(new Runnable() {
        @Override
        public synchronized void run() {
            for( int i = 1; i < cz; i++ )
                funkcjaDzielenia(cz);
        }
    });

    private void funkcjaDzielenia( int n ) {
        System.out.println("dzielenie");
        Random r = new Random();
        int a = Math.abs(r.nextInt() % (int)(h * 0.33)) + (int)(h * 0.33);    // oś y
        int b = Math.abs(r.nextInt() % (int)(w * 0.33)) + (int)(w * 0.33);    // oś x
       // System.out.println(a + " " + b);
       // System.out.println("Wywołanie");
        rekDzielenie( getPkt().get( a * w + b ) );
        //System.out.println("Wywołanie");
        rekDzielenie( getPkt().get( a * w + b ) );
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

        p.nullify(1);
        p.nullify(2);

        if( a == 0 || a == h-1 || b == 0 || b == w-1 ) return;
        rekDzielenie( next );
    }

    public List<Punkt> getPkt() {
        return pkt;
    }

    public int getN() {
        return n;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for( Punkt p : this.getPkt() ) {
            result.append(p.toString());
            result.append('\n');
        }
        return result.toString();
    }

    @Override
    public Iterator<Punkt> iterator() {
        return new Iterator<Punkt>() {
            int curr = 0;
            int lgt = n;

            @Override
            public boolean hasNext() {
                return curr < lgt;
            }

            @Override
            public Punkt next() {
                return pkt.get(curr++);
            }
        };
    }
}
