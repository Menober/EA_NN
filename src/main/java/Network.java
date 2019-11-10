import java.util.ArrayList;

public class Network {

    ArrayList<Neuron> input;
    ArrayList<Neuron> middle;
    ArrayList<Neuron> output;

    public void iniNetwork(int inputCount, int middleCount, int outputCount) {
        this.input = new ArrayList<>();
        for (int i = 0; i < inputCount; i++) {
            this.input.add(new Neuron());
        }
        this.middle = new ArrayList<>();
        for (int i = 0; i < middleCount; i++) {
            this.middle.add(new Neuron());
        }
        this.output = new ArrayList<>();
        for (int i = 0; i < outputCount; i++) {
            this.output.add(new Neuron());
        }
    }

    public void setConnections() {
        for (Neuron o : output) {
            o.previousNeurons.addAll(middle);
        }
        for (Neuron m : middle) {
            m.previousNeurons.addAll(input);
        }

        for (Neuron o : output) {
            o.iniWEights();
        }
        for (Neuron m : middle) {
            m.iniWEights();
        }
    }

    public void setInputValues(Double[] values) {
        for (int i = 0; i < values.length; i++) {
            input.get(i).value = values[i];
        }
    }

    public void calcMiddleLayer() {
        for (Neuron n : middle) {
            n.calculateValue();
        }
    }

    public void calcOutputLayer() {
        for (Neuron n : output) {
            n.calculateValue();
        }
    }

    public void printOutput() {
        System.out.println("\nOUTPUT:");
        for (Neuron o : output) {
            System.out.println(o.value);
        }
    }


    public void randomWeights() {
        for (Neuron n : middle) {
            n.iniRandomWeights();
        }
        for (Neuron o : output) {
            o.iniRandomWeights();
        }
    }

    public void setWeights(Individual individual) {
        int i = 0;
        for (int n = 0; n < middle.size(); n++) {
            Neuron neuron = middle.get(n);
            for (int k = 0; k < neuron.weights.size(); k++) { //TODO podejrzabe przypisanie
                neuron.weights.set(k, individual.weightsMiddle.get(i++));
            }
        }
        i = 0;
        for (int n = 0; n < output.size(); n++) {
            Neuron neuron = output.get(n);
            for (int k = 0; k < neuron.weights.size(); k++) { //TODO podejrzane przypisanie
                    neuron.weights.set(k, individual.weightsOutput.get(i++));
            }
        }
    }
}
