import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        int n = 6;
        int[] m = {2400, 2000, 1200, 2400, 1600, 4000};
        int[] a = {6, 2, 1, 4, 5, 3};
        int[] b = {6, 1, 5, 3, 2, 4};

        int[] copy = Arrays.copyOf(a, a.length);
        getCycles(n, copy, b);
        if (Arrays.equals(copy, b)) {
            System.out.println(" SORTED!");
        }
    }

    private static void getCycles(int n, int[] a, int[] b) {
        boolean[] isProperPosition = new boolean[n];
        List<Cycle> cycles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            isProperPosition[i] = a[i] == b[i];
        }
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (!isProperPosition[i]) {
                int x = i;
                List<Integer> cycle = new ArrayList<>();
                c++;
                cycles.add(new Cycle());
                while (!isProperPosition[x]) {
                    isProperPosition[x] = true;
                    cycle.add(b[x]);
                    cycles.get(c-1).elephants.add(b[x]);
                    x = changePlaces(a, x, b[x]);
                    System.out.println("Cycle " + c + " ");
                    System.out.println("Switched numbers so far: " + cycle.toString());
                    System.out.println("Result so far: " + Arrays.toString(a));
                    System.out.println(Arrays.toString(isProperPosition));
                }
            }
        }
        cycles.forEach(cyc -> System.out.println(cyc.elephants));
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
