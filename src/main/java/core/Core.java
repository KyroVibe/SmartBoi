package core;

public class Core {

    private Trainer T;

    public static void main(String[] args) {
        new Core(args);
    }

    public Core() { }

    public Core(String[] args) {

        T = new Trainer(1000, 3, 50, 50, 6);

        /*
        n = new Network(3, 5, 5, 1);
        double[] d = n.process(new double[] { 1, 1, 1 });
        for (int i = 0; i < d.length; i++) {
            Print(d[i]);
        }*/
    }

    public static void Print(Object a) {
        System.out.println(a.toString());
    }

}