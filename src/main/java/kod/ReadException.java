package kod;

public class ReadException extends Exception {
    int ln;
    String type;

    public ReadException( int ln, String type ) {
        super("ln = " + ln + " typ: " + type);
        this.ln = ln;
        this.type = type;
    }
}
