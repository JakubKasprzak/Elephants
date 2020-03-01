import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        int n = 6;
        int[] m = {2400, 2000, 1200, 2400, 1600, 4000};
        int[] a = {1, 4, 5, 3, 6, 2};
        int[] b = {5, 3, 2, 4, 6, 1};

        for (int i = 0; i < n; i++) {
            int[] copy = Arrays.copyOf(a, a.length);
            setElephants(i, n, copy, b);
            if (Arrays.equals(copy, b)) {
                System.out.println(i + " SORTED!");
            }
        }
    }

    private static void setElephants(int startPosition, int n, int[] a, int[] b) {
        boolean[] isProperPosition = new boolean[n];
        for (int i = 0; i < n; i++) {
            isProperPosition[i] = a[i] == b[i];
        }
        int c = 0;
        for (int i = startPosition; i < n; i++) {
            if (!isProperPosition[i]) {
                c++;
                int x = i;
                while (!isProperPosition[x]) {
                    isProperPosition[x] = true;
                    changePlaces(a, x, b[x]);
                    x = b[x];
                    System.out.println("Cykle " + c + " " + Arrays.toString(a));
                    System.out.println(Arrays.toString(isProperPosition));
                }
            }
        }
    }

    private static int[] changePlaces(int[] array, int i, int y) {
        int x = array[i];
        int j = IntStream.range(0, array.length)
            .filter(a -> y == array[a])
            .findFirst()
            .orElse(-1);
        array[j] = x;
        array[i] = y;
        return array;
    }
}
