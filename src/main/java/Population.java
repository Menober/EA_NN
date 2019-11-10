import java.util.ArrayList;

public class Population {
    ArrayList<Individual> population;

    Population() {
        population = new ArrayList<>();
    }

    public void addIndiv(Individual i) {
        population.add(i);
    }

    public void generateRandomPop(int popSize, Network network) {
        for (int i = 0; i++ < popSize; ) {
            network.randomWeights();
            Individual newIndiv = new Individual();
            for (Neuron n : network.middle) {
                n.weights.forEach(newIndiv::addMiddleWeight);
            }
            for (Neuron n : network.output) {
                n.weights.forEach(newIndiv::addOutputWeight);
            }
            population.add(newIndiv);
        }
    }

    public void crossover(int px, int amountOfChamps) {
        ArrayList<Individual> newPopulation = new ArrayList<>();
        while (newPopulation.size() != population.size()) {
            Individual parentA = tournamentSelection(amountOfChamps);
            Individual parentB = tournamentSelection(amountOfChamps);
            Individual childA = parentA.copy();
            Individual childB = parentB.copy();
            if (Utils.randomInt(0, 100000) <= px) {
                childA = crossoverParents(parentA, parentB);
                childB = crossoverParents(parentB, parentA);
            }
            newPopulation.add(childA);
            newPopulation.add(childB);
        }
        population = newPopulation;
    }

    private Individual crossoverParents(Individual parentA, Individual parentB) {
        Individual child = new Individual();
        for (int i = 0; i < parentA.weightsMiddle.size(); i++) {
            child.addMiddleWeight(Utils.randomInt(0, 100) > 50 ? parentA.weightsMiddle.get(i) : parentB.weightsMiddle.get(i));
        }
        for (int i = 0; i < parentA.weightsOutput.size(); i++) {
            child.addOutputWeight(Utils.randomInt(0, 100) > 50 ? parentA.weightsOutput.get(i) : parentB.weightsOutput.get(i));
        }

        return child;
    }

    public void mutate(int pm) {
        for (Individual individual : population) {
            individual.mutationMiddle(pm);
            individual.mutationOutput(pm);
        }
    }

    private Individual tournamentSelection(int amountOfChamps) {
        Individual best = population.get(Utils.randomInt(0, population.size() - 1));
        for (int i = 0; i < amountOfChamps; i++) {
            Individual randomRival = population.get(Utils.randomInt(0, population.size() - 1));
            if (randomRival.fitness > best.fitness) {
                best = randomRival;
            }
        }
        return best;
    }

    public Individual getBest() {
        Individual best = population.get(0);
        Double max = Double.MIN_VALUE;
        for (Individual i : population) {
            if (max < i.fitness) {
                max = i.fitness;
                best = i;
            }
        }
        return best;
    }

    public Individual getWorst() {
        Individual worst = population.get(0);
        Double min = Double.MAX_VALUE;
        for (Individual i : population) {
            if (min > i.fitness) {
                min = i.fitness;
                worst = i;
            }
        }
        return worst;
    }
}

