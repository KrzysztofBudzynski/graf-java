package kod;

import java.util.Arrays;

public class Dijkstra extends Thread {
    private Labirynt l;
    private int[] odw;
    private int[] przez;
    private double[] min;
    private double max;
    private final int sIndex;        //indeks punktu startowego
    private Punkt current;     // miejsce na przechowanie punktu


    public Dijkstra( Labirynt l, int start ) {
        this.sIndex = start;
        this.l = l;
        this.current = l.getPkt().get(start);
        odw = new int[l.getN()];
        Arrays.fill(odw, 0);
        przez = new int[l.getN()];
        przez[start] = start;
        min = new double[l.getN()];
        Arrays.fill(min, Integer.MAX_VALUE);
        min[start] = 0;
    }

    public void launch() {              // iteracja algorytmu
        if( current == null || odw[current.getIndex()] != 0 ) return;
        odw[current.getIndex()] = 1;
        for( Edge e : current.getEdges() ) {
            if( e.getTo() == null ) continue;
            if( min[current.getIndex()] + e.getWaga() < min[e.getTo().getIndex()] ) {
                min[e.getTo().getIndex()] = min[current.getIndex()] + e.getWaga();
                przez[e.getTo().getIndex()] = current.getIndex();
            }
        }
        int obok = gdzie( min, odw );
        if( obok == Integer.MAX_VALUE ) return;
        current = l.getPkt().get(obok);
        launch();
    }

    private int gdzie( double[] min, int[] odw ) {  // szuka najbliższego punktu
        int tam = Integer.MAX_VALUE;
        double tmp = Integer.MAX_VALUE;
        for( int i = 0; i < min.length; i++ ) {
            if( min[i] < tmp && odw[i] == 0 ) {
                tmp = min[i];
                tam = i;
            }
        }
        return tam;
    }

    @Override
    public synchronized void start() {
        launch();
        setMax();
    }

    public int[] getOdw() {
        return odw;
    }

    public double[] getMin() {
        return min;
    }

    public int[] getPrzez() {
        return przez;
    }

    public void setMax() {
        double m = min[0];
        for( double d : min ) {
            if( m < d ) m = d;
        }
        this.max = m;
    }

    public double getMax() {
        return max;
    }

    public boolean testOdw() {      // czy wszystkie odwiedzone
        for( int j : odw ) {
            if( j != 1 ) return false;
        }
        return true;
    }
}
