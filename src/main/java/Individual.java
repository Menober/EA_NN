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

    Individual(Individual clone){
        this.weightsMiddle= (ArrayList<Double>) clone.weightsMiddle.clone();
        this.weightsOutput= (ArrayList<Double>) clone.weightsOutput.clone();
    }

    public void addMiddleWeight(Double weight) {
        weightsMiddle.add(new Double(weight));
    }

    public void addOutputWeight(Double weight) {
        weightsOutput.add(new Double(weight));
    }

    public void mutationMiddle(int chance) {
        for (int i = 0; i < weightsMiddle.size(); i++) {
            if (Utils.randomInt(0, 100000) < chance) {
                mutateWeight(i, weightsMiddle);
            }
        }
    }

    private void mutateWeight(int i, ArrayList<Double> weightsMiddle) {
        weightsMiddle.set(i, Utils.randomDouble(-1.0, 1.0));
    }

    public void mutationOutput(int chance) {
        for (int i = 0; i < weightsOutput.size(); i++) {
            if (Utils.randomInt(0, 100000) < chance) {
                mutateWeight(i, weightsMiddle);
            }
        }
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
        System.out.println("\nOutput weights:");
        for (Double weight : weightsOutput) {
            System.out.print(weight + ";");
        }
    }

    public void parseToMiddleWeights(String middleWeights) {
        String[] weights = middleWeights.split(";");
        for (String weight : weights) {
            addMiddleWeight(Double.valueOf(weight));
        }
    }

    public void parseToOutputWeights(String outputWeights) {
        String[] weights = outputWeights.split(";");
        for (String weight : weights) {
            addOutputWeight(Double.valueOf(weight));
        }
    }
}
