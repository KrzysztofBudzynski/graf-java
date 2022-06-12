package kod;

public class Main {
    public static void main(String[] args) {
        Labirynt l = new Labirynt(5, 5);

        /*if( args.length == 1 ) */l.gen();

/*        if (args.length == 2) {
            try {
                l = Utils.readLabirynt(args[1]);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }
        }*/
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
        Dzielnik dz = new Dzielnik(l, 2);
        dz.start();
        try {
            dz.join();
        } catch (InterruptedException ignored) {}
        Bfs bfs = new Bfs(l, 0);
        bfs.start();
        System.out.println("stan spojnosci grafu:" + bfs.getSpojny());
        System.out.println(l);
    }
}
