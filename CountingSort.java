import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Scanner;

public class CountingSort {
    public static void main(String[] args) {
        long countingSortMemoryStart = getUsedMemory();
        int[] array = readInputFromFile("dataset_reversed_50000.txt");
        long countingSortStartTime = System.nanoTime();
        int[] sortedArrayCountingSort = countingSort(array);
        long countingSortEndTime = System.nanoTime();
        long countingSortExecutionTime = countingSortEndTime - countingSortStartTime;
        writeOutputToFile("CSsort.txt", sortedArrayCountingSort);
        long countingSortMemoryEnd = getUsedMemory();
        long countingSortMemory = countingSortMemoryEnd - countingSortMemoryStart;
        System.out.println("CSMemoryUsed: " + countingSortMemory + " bytes");
        System.out.println("CSExecutionTime: " + countingSortExecutionTime + " ms");
    }

    public static void writeOutputToFile(String fileName, int[] array) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (int num : array) {
                writer.write(num + "\n");
            }
            writer.close();
            System.out.println("Output written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static long getUsedMemory() {
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        return heapMemoryUsage.getUsed();
    }

    public static int[] readInputFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int size = scanner.nextInt();
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = scanner.nextInt();
            }
            scanner.close();
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] countingSort(int[] array) {
        int max = findMaxValue(array);

        int[] count = new int[max + 1];

        for (int num : array) {
            count[num]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            sortedArray[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }

        return sortedArray;
    }

    public static int findMaxValue(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
}
