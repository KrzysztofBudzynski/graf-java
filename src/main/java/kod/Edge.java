package kod;

public class Edge {
    private Punkt from;
    private Punkt to;
    private double waga;

    public Edge(Punkt a, Punkt b) {
        this.from = a;
        this.to = b;
    }

    public Edge() {
        this.from = null;
        this.to = null;
    }

    public void setNull() {
        this.to = null;
        this.from = null;
    }

    public void setWaga(double waga) {
        this.waga = waga;
    }

    public Punkt getTo() {
        return to;
    }

    public double getWaga() {
        return waga;
    }

    public Punkt getFrom() {
        return from;
    }
}
