import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileProcessor {

    List<String> readLinesFromFile(String filePath) throws FileNotFoundException {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        }
        return list;
    }
}
