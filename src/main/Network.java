package main;

public class Network {

    // double[Node]
    // double[Layer][Node]
    // double[Layer][Node][Weight]
    private double[] inputNodes;
    private double[][] hiddenNodes;
    private double[] outputNodes;
    private double[][][] weights;

    // -[Contructors]-

    public Network() { }

    // New Network
    public Network(String path) {
        // Load network from file
    }

    // Existing Network
    public Network(int inputNodes, int hiddenNodes, int hiddenLayers, int outputNodes) {
        // Init new network
        init(inputNodes, hiddenNodes, hiddenLayers, outputNodes);
    }

    // -[Operators]-

    private void init(int inputN, int hiddenN, int hiddenL, int outputN) {
        inputNodes = new double[inputN];
        hiddenNodes = new double[hiddenL][hiddenN];
        outputNodes = new double[outputN];
        weights = new double[hiddenL + 1];

        for (int i = 0; i < hiddenL + 1; i++) {
            if (i == 0) {
                weights[i] = new double[inputN];
            } else {
                weights[i] = new double[hiddenN];
            }

            for (int j = 0; j < weights[i].length; i++) {
                if (j == weights[i].length - 1) {
                    weights[i][j] = new double[outputN];
                } else {
                    weights[i][j] = new double[hiddenN];
                }

                for (int w = 0; w < weights[i][j].length; w++) {
                    weights[i][j][w] = 0.5;
                }
            }
        }
    }

    public double[] process(double[] input) {
        inputNodes = input;

        for (int i = 0; i < weights.length - 1; i++) {
            for (int j = 0; j < weights[i + 1].length; j++) {
                for (int w = 0; w < weights[i - 1][j].length; w++) {
                    if (i == 0) {

                        double myNode = 0;

                        for (int h = 0; h < weights[i].length; h++) {
                            myNode += inputNodes[h] * weights[i][h][w];
                        }

                        myNode /= weights[i].length;

                        hiddenNodes[i + 1][j] = myNode;
                    } else if (i == weights.length - 2) {
                        double myNode = 0;

                        for (int h = 0; h < weights[i].length; h++) {
                            myNode += hiddenNodes[i + 1][h] * weights[i][h][w];
                        }

                        myNode /= weights[i].length;

                        outputNodes[j] = myNode;
                    } else {
                        double myNode = 0;

                        for (int h = 0; h < weights[i].length; h++) {
                            myNode += hiddenNodes[i + 1][h] * weights[i][h][w];
                        }

                        myNode /= weights[i].length;

                        hiddenNodes[i + 1][j] = myNode;
                    }
                }
            }
        }

        return outputNodes;
    }

}