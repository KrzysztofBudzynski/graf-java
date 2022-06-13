package kod;

public class Main {
    public static void main(String[] args) {
        Labirynt l = new Labirynt(5, 5);

        System.out.println("wczytano " + l.getN());
        System.out.println(l.testWag());

        Dijkstra d = new Dijkstra(l, 0);
        d.start();
        try {
            d.join();
        } catch (InterruptedException e) {
            System.err.println(e.getLocalizedMessage());
        }
        for( int i : d.getPrzez() ) System.out.println(i);
        Bfs bfs = new Bfs(l, 0);
        bfs.start();
        System.out.println("stan spojnosci grafu:" + bfs.getSpojny());
        System.out.println(l);
        System.out.println(d.getMax());
    }
}
