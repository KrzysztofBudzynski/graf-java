package kod;

public class Bfs extends Thread {
    private Labirynt l;
    private boolean[] odwiedzono;
    private Stack stack;
    private int s;
    private Punkt current;
    private boolean spojny;
    public Bfs(Labirynt l, int start)
    {  
        this.s = start;
        this.l = l;
        this.current = l.getPkt().get(s);
        odwiedzono = new boolean[l.getN()];
        for (int i = 0; i < l.getN(); i++)
        {
            odwiedzono[i] = false;
        }
        stack = new Stack(l.getN());

    }
    public void launch(){
        for (int i = 0; i < 4; i++)
        {
            if (current.getEdges().get(i).getTo() != null && odwiedzono[current.getEdges().get(i).getTo().getIndex()] == false)
            {
                stack.Push(current.getEdges().get(i).getTo().getIndex());
                odwiedzono[current.getEdges().get(i).getTo().getIndex()] = true;
                //System.out.println(i + current.getEdges().get(i).getTo().getIndex());
                //System.out.println(i);
            }
            //System.out.println(i + current.getEdges().get(i).getTo().getIndex());
        }  
        //stack.display();
        if (stack.getSize() >= 1)
        {
            this.current = l.getPkt().get(stack.Pop());
            launch();
        }
        else{
            spojny = true;
            for (int i = 0; i < l.getN(); i++)
            {
                //System.out.println(odwiedzono[i]);
                if (odwiedzono[i] == false)
                {
                    spojny = false;
                }
            }
            // if (spojny == 0)
            //     System.out.println("spojny");
            // else
            //     System.out.println("niespojnby");
            //System.out.println(l.getPkt().get(0).getEdges().get(1).getTo().getIndex());
        }
        

    }
    @Override
    public synchronized void start() {
        launch();
    }
    public boolean getSpojny()
        {
            return spojny;
        }
    

}
