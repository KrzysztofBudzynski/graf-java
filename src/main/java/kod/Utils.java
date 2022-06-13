package kod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Utils {
    public static Labirynt readLabirynt( String path ) throws Exception {
        if( ! path.endsWith( ".txt") ) {
            throw new IOException("Błędny format pliku wejściowego");
        }

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int ln = 0;
        String [] words;

        line = br.readLine();

        words = line.split("\\s+");

        if( words.length != 2 ) {
            throw new ReadException(ln, "Wymiary grafu");
        }

        int h = Integer.parseInt(words[0]);
        int w = Integer.parseInt(words[1]);
        if( h <= 0 || w <= 0 ) {
            throw new ReadException(ln, "Wymiary grafu");
        }

        Labirynt l = new Labirynt(h, w);
        int p;
        double waga;

        while( (line = br.readLine()) != null ) {
            words = line.split("\\s+");
            if( words.length % 2 != 0 ) {
                throw new ReadException(ln, "Wagi");
            }
            for( int i = 0; i < words.length/2; i++ ) {
                p = Integer.parseInt(words[2*i]);
                waga = Double.parseDouble(words[2*i+1].replace(":", ""));
                if( waga <= 0 ) continue;
                if( p < 0 ) throw new ReadException(ln, "Punkt o ujemnym indeksie");

                if( p == ln - l.getW() ) {
                    l.getPkt().get(ln).getEdges().get(0).setWaga(waga);
                    l.getPkt().get(p).getEdges().get(2).setWaga(waga);
                }
                else if( p == ln + 1 ) {
                    l.getPkt().get(ln).getEdges().get(1).setWaga(waga);
                    l.getPkt().get(p).getEdges().get(3).setWaga(waga);
                }
                else if( p == ln + w ) {
                    l.getPkt().get(ln).getEdges().get(2).setWaga(waga);
                    l.getPkt().get(p).getEdges().get(0).setWaga(waga);
                }
                else if( p == ln - 1 ) {
                    l.getPkt().get(ln).getEdges().get(3).setWaga(waga);
                    l.getPkt().get(p).getEdges().get(1).setWaga(waga);
                }
                else throw new ReadException(ln, "Punkty nie mogą mieć połączenia");
            }
            ln++;
        }
        return l;
    }
}
