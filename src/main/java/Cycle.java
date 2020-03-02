import java.util.ArrayList;
import java.util.List;

public class Cycle {

    List<Integer> elephants;
    private int totalWeight;
    private int minWeight;

    Cycle() {
        this.elephants = new ArrayList<>();
    }

    List<Integer> getElephants() {
        return elephants;
    }

    public void setElephants(List<Integer> elephants) {
        this.elephants = elephants;
    }

    int getTotalWeight() {
        return totalWeight;
    }

    void setTotalWeight(int[] weights) {
        this.totalWeight = elephants.stream().mapToInt(e -> weights[e - 1]).sum();
    }

    int getMinWeight() {
        return minWeight;
    }

    void setMinWeight(int[] weights) {
        Integer min = Integer.MAX_VALUE;
        for (Integer elephant : elephants) {
            if (weights[elephant - 1] < min) {
                min = weights[elephant - 1];
            }
        }
        this.minWeight = min;
    }
}
