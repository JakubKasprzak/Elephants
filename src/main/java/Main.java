import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        int n = 6;
        int[] m = {2400, 2000, 1200, 2400, 1600, 4000};
        int[] a = {6, 2, 1, 4, 5, 3};
        int[] b = {6, 1, 5, 3, 2, 4};

        int[] copy = Arrays.copyOf(a, a.length);
        List<Cycle> cycles = getCycles(n, copy, b);
        setParameters(cycles, m);
        int w = countResult(cycles, m);
        System.out.println(w);
    }

    private static int countResult(List<Cycle> cycles, int[] m) {
        int w = 0;
        List<Integer> intList = new ArrayList<>(m.length);
        for (int i : m) {
            intList.add(i);
        }
        for (Cycle c : cycles) {
            int rm1 = c.getTotalWeight() + (c.getElephants().size() - 2) * c.getMinWeight();
            int rm2 = c.getTotalWeight() + c.getMinWeight() + (c.getMinWeight() + 1) * (Collections.min(intList));
            if (rm1 >= rm2) {
                w += rm2;
            } else {
                w += rm1;
            }
        }
        return w;
    }

    private static void setParameters(List<Cycle> cycles, int[] m) {
        cycles.forEach(c -> {
            c.setTotalWeight(m);
            c.setMinWeight(m);
        });
    }

    private static List<Cycle> getCycles(int n, int[] a, int[] b) {
        boolean[] isProperPosition = new boolean[n];
        List<Cycle> cycles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            isProperPosition[i] = a[i] == b[i];
        }
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (!isProperPosition[i]) {
                int x = i;
                c++;
                cycles.add(new Cycle());
                while (!isProperPosition[x]) {
                    isProperPosition[x] = true;
                    cycles.get(c - 1).elephants.add(b[x]);
                    x = changePlaces(a, x, b[x]);
                }
            }
        }
        return cycles;
    }

    private static int changePlaces(int[] array, int i, int y) {
        int x = array[i];
        int j = IntStream.range(0, array.length)
            .filter(a -> y == array[a])
            .findFirst()
            .orElse(-1);
        array[j] = x;
        array[i] = y;
        return j;
    }
}
