package core;

import java.util.Random;

public class Network {

    // double[Node]
    // double[Layer][Node]
    // double[Layer][Node][Weight]
    private double[][] nodes;
    private double[][][] weights;

    public boolean Purge = false;
    public double Error;

    // -[Contructors]-

    public Network() { }

    // OffSpring Network
    public Network(int inputN, int hiddenN, int hiddenL, int outputN, double[][][] newWeights) {
        nodes = new double[2 + hiddenL][];
        for (int i = 0; i < nodes.length; i++) {
            if (i == 0) {
                nodes[0] = new double[inputN];
            } else if (i == nodes.length - 1) {
                nodes[i] = new double[outputN];
            } else {
                nodes[i] = new double[hiddenN];
            }
        }

        weights = newWeights;
    }

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
        nodes = new double[2 + hiddenL][];
        for (int i = 0; i < nodes.length; i++) {
            if (i == 0) {
                nodes[0] = new double[inputN];
            } else if (i == nodes.length - 1) {
                nodes[i] = new double[outputN];
            } else {
                nodes[i] = new double[hiddenN];
            }
        }

        weights = GenWeights();

        //weights = new double[hiddenL + 1][][];

        /*for (int i = 0; i < hiddenL + 1; i++) {
            if (i == 0) {
                weights[i] = new double[inputN][];
            } else {
                weights[i] = new double[hiddenN][];
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
        }*/
    }

    public double[][][] GenWeights() {

        Random rand = new Random();

        double[][][] wt;

        wt = new double[nodes.length - 1][][];

        for (int x = 0; x < (nodes.length) - 1; x++) {
            wt[x] = new double[nodes[x].length][];
            for (int y = 0; y < nodes[x].length; y++) {
                if ((x < (nodes.length - 1) - 2) && (x >= 0))
                    wt[x][y] = new double[nodes[x + 1].length - 1];
                else
                    wt[x][y] = new double[nodes[x + 1].length];
                for (int z = 0; z < wt[x][y].length; z++) {
                    //wt[x][y][z] = ((double)rand.nextDouble() - 0.5);
                    wt[x][y][z] = (rand.nextDouble() * 2);
                    //System.out.println(wt[x][y][z]);
                }
            }
        }

        return wt;
    }

    public double[] process(double[] input) {
        nodes[0] = input;

        //for (int y = 0; y < nodes[0].length; y++) {
            //System.out.println(nodes[0][y]);
        //}

        for (int i = 0; i < (nodes.length) - 1; i++) {
            for (int a = 0; a < nodes[i].length; a++) {
                for (int g = 0; g < nodes[i + 1].length; g++) {

                    for (int b = 0; b < weights[i][a].length; b++) {
                        //System.out.println(weights[i][a][b]);
                        nodes[i + 1][g] += nodes[i][a] * weights[i][a][b];
                    }
                    //System.out.println(g);
                    nodes[i + 1][g] = nodes[i + 1][g] / weights[i][a].length;
                    //System.out.println(g);
                }
            }
        }

        /*for (int i = 0; i < weights.length - 1; i++) {
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
        }*/

        for (int h = 0; h < nodes.length; h++) {
            for (int y = 0; y < nodes[h].length; y++) {
                //System.out.println(nodes[h][y]);
            }
        }

        return nodes[nodes.length - 1];
    }

    public double[][][] getWeights() { return weights; }

}