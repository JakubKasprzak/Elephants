import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Parade {
    private static FileProcessor fp = new FileProcessor();
    private static final String filepath = "src/main/resources/slo1.in";

    public static void main(String[] args) throws FileNotFoundException {
        resolveParade(fp.readLinesFromFile(filepath));
    }

    private static void resolveParade(List<String> parade) {
        int n = Integer.parseInt(parade.get(0));
        int[] m = convert(parade, 1);
        int[] a = convert(parade, 2);
        int[] b = convert(parade, 3);
        List<Cycle> cycles = getCycles(n, a, b);
        setParameters(cycles, m);
        int w = countResult(cycles, m);
        System.out.println(w);
    }

    private static int[] convert(List<String> parade, int l) {
        String[] weights = parade.get(l).split(" ");
        int[] x = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            x[i] = Integer.parseInt(weights[i]);
        }
        return x;
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
                    x = switchPlaces(a, x, b[x]);
                }
            }
        }
        return cycles;
    }

    private static int switchPlaces(int[] array, int i, int y) {
        int x = array[i];
        int j = IntStream.range(0, array.length)
            .filter(a -> y == array[a])
            .findFirst()
            .orElse(-1);
        array[j] = x;
        array[i] = y;
        return j;
    }

    private static void setParameters(List<Cycle> cycles, int[] m) {
        cycles.forEach(c -> {
            c.setTotalWeight(m);
            c.setMinWeight(m);
        });
    }

    private static int countResult(List<Cycle> cycles, int[] m) {
        int w = 0;
        List<Integer> intList = new ArrayList<>(m.length);
        for (int i : m) {
            intList.add(i);
        }
        for (Cycle c : cycles) {
            int rm1 = c.getTotalWeight() + (c.getElephants().size() - 2) * c.getMinWeight();
            int rm2 = c.getTotalWeight() + c.getMinWeight() + (c.getElephants().size() + 1) * (Collections.min(intList));
            if (rm1 >= rm2) {
                w += rm2;
            } else {
                w += rm1;
            }
        }
        return w;
    }
}
