import java.util.ArrayList;

public class Individual {
    ArrayList<Double> weightsMiddle;
    ArrayList<Double> weightsOutput;
    Double fitness;

    Individual() {
        weightsOutput = new ArrayList<>();
        weightsMiddle = new ArrayList<>();
        fitness = 0.0;
    }

    Individual(ArrayList<Double> weightsMiddle, ArrayList<Double> weightsOutput) {
        this.weightsOutput = weightsOutput;
        this.weightsMiddle = weightsMiddle;
    }

    public void addMiddleWeight(Double weight) {
        weightsMiddle.add(new Double(weight));
    }

    public void addOutputWeight(Double weight) {
        weightsOutput.add(new Double(weight));
    }

    public void mutationMiddle(int chance) {
        for (int i = 0; i < weightsMiddle.size(); i++) {
            char[] weight = Utils.convertDoubleToString(weightsMiddle.get(i)).toCharArray();
            for (int j = 0; j < weight.length; j++) {
                if (Utils.randomInt(0, 100000) < chance) {
                    weight[j] = swapByte(weight[j]);
                }
            }
            weightsMiddle.set(i, Utils.convertStringToDouble(String.valueOf(weight)));
        }
    }

    public void mutationOutput(int chance) {
        for (int i = 0; i < weightsOutput.size(); i++) {
            char[] weight = Utils.convertDoubleToString(weightsOutput.get(i)).toCharArray();
            for (int j = 0; j < weight.length; j++) {
                if (Utils.randomInt(0, 100000) < chance) {
                    weight[j] = swapByte(weight[j]);
                }
            }
            weightsOutput.set(i, Utils.convertStringToDouble(String.valueOf(weight)));
        }
    }

    private char swapByte(char c) {
        return c == '1' ? '0' : '1';
    }

    public void setFitness(Double ranDistance) {
        fitness = ranDistance;
    }

    public void setWeightsMiddle(ArrayList<Double> weightsMiddle) {
        this.weightsMiddle = weightsMiddle;
    }

    public void setWeightsOutput(ArrayList<Double> weightsOutput) {
        this.weightsOutput = weightsOutput;
    }

    public Individual copy() {
        Individual newIndividual = new Individual();
        newIndividual.setFitness(fitness); //TODO do sprawdzenia
        newIndividual.setWeightsMiddle((ArrayList<Double>) weightsMiddle.clone());
        newIndividual.setWeightsOutput((ArrayList<Double>) weightsOutput.clone());
        return newIndividual;
    }

    public Double getFitness() {
        return fitness;
    }

    public void printData() {
        System.out.println("Fitness: " + fitness);
        System.out.println("Middle weights:");
        for (Double weight : weightsMiddle) {
            System.out.print(weight + ";");
        }
        System.out.println("Output weights:");
        for (Double weight : weightsOutput) {
            System.out.print(weight + ";");
        }
    }
}
