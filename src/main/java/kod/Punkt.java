package kod;

import java.util.ArrayList;
import java.util.List;

public class Punkt implements Comparable<Object> {
    private final int kolumna;
    private final int wiersz;

    private final int index;

    private final List<Edge> edges;

    public List<Edge> getEdges() {
        return edges;
    }

    public int getWiersz() {
        return wiersz;
    }

    public int getKolumna() {
        return kolumna;
    }

    public int getIndex() {
        return index;
    }

    public Punkt(int i, int j, int w){
        this.kolumna = j;
        this.wiersz = i;
        this.index = i * w + j;
        edges = new ArrayList<>();
    }

    public void nullify(int k ) {      // k kierunkiem, usuwa połączenie
        if( this.getEdges().get(k).getTo() == null ) return;

        getEdges().get(k).getTo().getEdges().get((k+2) % 4).setWaga(-1);
        getEdges().get(k).setWaga(-1);
    }

    @Override
    public String toString() {
        List<Integer> to = edges.stream()
                .filter(edge -> edge.getTo() != null)
                .map(edge -> edge.getTo().getIndex()).toList();
        return "wiersz = " + wiersz + " kolumna: " + kolumna + " k: " + edges.size()
                + " i = " + index + " do: { " + to + " }";
    }

    @Override
    public int compareTo( Object o ) {
        if( ! (o instanceof Punkt) ) {
            return -1;
        }
        if( ((Punkt)o).getIndex() < this.index ) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals( Object o ) {
        if( ! (o instanceof Punkt) ) {
            return false;
        }
        return ((Punkt)o).index == ((Punkt)o).index;
    }

    @Override
    public int hashCode() {
        return this.index * 31 + this.kolumna * this.wiersz;
    }
}
