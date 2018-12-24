package core;

import java.util.ArrayList;
import java.util.Random;

public class Trainer {

    private Random uniRand = new Random();

    public int inputNodes, hiddenNodes, hiddenLayers, outputNodes;

    public ArrayList<Network> goodNetworks = new ArrayList<Network>();

    public Network[] networks;
    public Network best;

    double bestError = 1;
    double worstError = 0;

    public double[][] inputData = new double[][] {{ 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }};
    public double[][] outputData = new double[][] {{ 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0, 0 }};

    public Trainer(int sampleSizes, int inputNodes, int hiddenNodes, int hiddenLayers, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.hiddenLayers = hiddenLayers;
        this.outputNodes = outputNodes;

        BeginTraining(sampleSizes);
    }

    public void BeginTraining(int sampleSizes) {
        System.out.println("\tBeginning Training...");

        networks = new Network[sampleSizes];
        for (int i = 0; i < networks.length; i++) {
            networks[i] = new Network(inputNodes, hiddenNodes, hiddenLayers, outputNodes);
        }

        for (int cycleTimes = 0; cycleTimes < 500; cycleTimes++) {
            if (cycleTimes != 0) { Purge(); }

            double bestError = 1;
            double worstError = 0;

            for (Network a : networks) {

                int dat = uniRand.nextInt(inputData.length - 1);
                //System.out.println(dat);

                double[] out = a.process(inputData[dat]);
                double[] errors = new double[out.length];

                double sum = 0;
                for (int i = 0; i < errors.length; i++) {
                    errors[i] = Math.abs(out[i] - outputData[dat][i]);
                    sum += errors[i];
                }

                a.Error = sum / errors.length;

                if (a.Error > worstError) {
                    worstError = a.Error;
                } else if (a.Error < bestError) {
                    bestError = a.Error;
                }
            }

            System.out.println(bestError);
        }

        double b = 1;

        for (Network n : networks) {
            if (n.Error < b) {
                b = n.Error;
                best = n;
            }
        }

        System.out.println("\n\n\tTraining Complete!");
        System.out.println("Error: " + best.Error);
    }

    // Util

    public void Purge() {

        double passingError = (bestError + worstError) / 2;
        Random rand = new Random(uniRand.nextInt(6534334));

        int purged = 0;

        goodNetworks = new ArrayList<Network>();

        for (Network a : networks) {
            if (a.Error <= passingError) {
                if (rand.nextDouble() * 100 > 85) {
                    a.Purge = true;
                    purged++;
                } else {
                    goodNetworks.add(a);
                }
            } else {
                a.Purge = true;
                purged++;
            }
        }

        System.out.println("Purged: " + purged);
        System.out.println("NotPurged: " + goodNetworks.size());

        for (int i = 0; i < networks.length; i++) {
            if (networks[i].Purge) {
                if (goodNetworks.size() > 5) {
                    int a = uniRand.nextInt(goodNetworks.size() - 1);
                    int b = uniRand.nextInt(goodNetworks.size() - 1);
                    networks[i] = Mate(goodNetworks.get(a), goodNetworks.get(b));
                } else {
                    networks[i] = new Network(inputNodes, hiddenNodes, hiddenLayers, outputNodes);
                }
            }
        }
    }

    public Network Mate(Network a, Network b) {

        Random rand = new Random(uniRand.nextInt(6534334));

        double[][][] weightsA = a.getWeights();
        double[][][] weightsB = b.getWeights();

        //System.out.println("A: " + weightsA.length + " " + weightsA[0].length + " " + weightsA[0][0].length);
        //System.out.println("B: " + weightsB.length + " " + weightsB[0].length + " " + weightsB[0][0].length);

        double[][][] newWeights = new double[weightsA.length][][];

        for (int x = 0; x < weightsA.length; x++) {
            newWeights[x] = new double[weightsA[x].length][];
            for (int y = 0; y < weightsA[x].length; y++) {
                newWeights[x][y] = new double[weightsA[x][y].length];
            }
        }

        for (int x = 0; x < newWeights.length; x++) {
            for (int y = 0; y < newWeights[x].length; y++) {
                for (int z = 0; z < newWeights[x][y].length; z++) {
                    double j = rand.nextDouble();
                    if (j < 0.42) {
                        newWeights[x][y][z] = weightsA[x][y][z];
                    } else if (j > 0.68) {
                        newWeights[x][y][z] = weightsB[x][y][z];
                    } else {
                        newWeights[x][y][z] = (rand.nextDouble() * 2) - 1;
                    }
                }
            }
        }

        return new Network(inputNodes, hiddenNodes, hiddenLayers, outputNodes, newWeights);
    }

}