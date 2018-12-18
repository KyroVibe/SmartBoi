package core;

public class Core {

    private Network n;

    public static void main(String[] args) {
        new Core(args);
    }

    public Core() { }

    public Core(String[] args) {
        n = new Network();
    }

}