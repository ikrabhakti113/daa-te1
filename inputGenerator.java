import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class inputGenerator {

    public static void main(String[] args) {
        reverseFile("dataset_sorted_5000.txt");
    }

    public static void generate(int num) {
        Random random = new Random();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dataset_" + num + ".txt"));

            for (int i = 0; i < num; i++) {
                int val = random.nextInt(num + 1);
                writer.write(String.valueOf(val));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void reverseFile(String fileName) {
        try {
            ArrayList<Integer> numbers = new ArrayList<>();

            // Read numbers from the file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line));
            }
            reader.close();

            // Reverse the list
            Collections.reverse(numbers);

            // Write the reversed list back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName.replace("_sorted", "_reversed")));
            for (int val : numbers) {
                writer.write(String.valueOf(val));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
