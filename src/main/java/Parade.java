import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Parade {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = getNumber(in);
        int[] m = new int[n];
        int[] a = new int[n];
        int[] b = new int[n];
        System.out.println("put in masses");
        fillMasses(in, n, m);
        System.out.println("put in a");
        fillArray(in, n, a);
        System.out.println("put in b");
        fillArray(in, n, b);
        resolveParade(n, m, a, b);
    }

    private static int getNumber(Scanner in) {
        boolean success = false;
        int n = 0;
        while (!success) {
            try {
                n = in.nextInt();
                if (n <= 1) {
                    throw new IllegalArgumentException("Number cannot be lower than 2!");
                }
                success = true;
            } catch (InputMismatchException e) {
                in.next();
                System.out.println("You have entered invalid data");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return n;
    }

    private static void fillArray(Scanner in, int n, int[] array) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            boolean success = false;
            int x;
            while (!success) {
                try {
                    x = in.nextInt();
                    if (set.contains(x)) {
                        throw new IllegalArgumentException("Elephant " + x + " is already in set!");
                    } else {
                        set.add(x);
                    }
                    if (x > n || x <= 0) {
                        throw new IllegalArgumentException("There are " + n + " elephants in parade! Put number from 1 to " + n);
                    } else {
                        array[i] = x;
                        success = true;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("You can put only an integer!");
                    in.next();
                }
            }
        }
    }

    private static void fillMasses(Scanner in, int n, int[] array) {
        for (int i = 0; i < n; i++) {
            boolean success = false;
            while (!success) {
                try {
                    int m = in.nextInt();
                    if (m <= 0) {
                        throw new IllegalArgumentException("Mass cannot be lower than 1!");
                    } else {
                        array[i] = m;
                        success = true;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("You can put only an integer!");
                    in.next();
                }
            }
        }
    }

    private static void resolveParade(int n, int[] m, int[] a, int[] b) {
        List<Cycle> cycles = getCycles(n, a, b);
        setParameters(cycles, m);
        int w = countResult(cycles, m);
        System.out.println(w);
    }

//    private static int[] convert(List<String> parade, int l) {
//        String[] weights = parade.get(l).split(" ");
//        int[] x = new int[weights.length];
//        for (int i = 0; i < weights.length; i++) {
//            x[i] = Integer.parseInt(weights[i]);
//        }
//        return x;
//    }

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
