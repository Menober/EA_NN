import java.util.ArrayList;

public class Neuron {
    ArrayList<Neuron> previousNeurons = new ArrayList<>();
    ArrayList<Double> weights = new ArrayList<>();
    Double value;

    public void calculateValue() {
        Double value = 0.0;
        for (int i = 0; i < previousNeurons.size(); i++) {
            value += previousNeurons.get(i).value * weights.get(i);
        }
        this.value = 1.0 / (1.0 + Math.pow(Math.E, -value));
    }

    public void iniWEights() {
        weights = new ArrayList<>();
        for (Neuron n : previousNeurons) {
            weights.add(0.0);
        }
    }

    public void iniRandomWeights() {
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, Utils.randomDouble(Double.MIN_VALUE, Double.MAX_VALUE));
        }
    }
}
