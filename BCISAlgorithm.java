import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Scanner;

public class BCISAlgorithm {
    public static void main(String[] args) {
        long BCISMemoryStart = getUsedMemory();
        int[] array = readInputFromFile("dataset_sorted_500.txt");
        long BCISStartTime = System.nanoTime();
        bidirectionalConditionalInsertionSort(array);
        long BCISEndTime = System.nanoTime();
        long BCISExecutionTime = BCISEndTime - BCISStartTime;
        writeOutputToFile("BCIS.txt",array);
        long BCISMemoryEnd = getUsedMemory();
        long BCISMemory = BCISMemoryEnd - BCISMemoryStart;
        System.out.println("BCISMemoryUsed: " + BCISMemory + " bytes");
        System.out.println("BCISExecutionTime: " + BCISExecutionTime + " ns");
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

    public static void bidirectionalConditionalInsertionSort(int[] array) {
        int SL = 0;
        int SR = array.length - 1;
        while (SL < SR) {
            int mid = SL + (SR - SL) / 2;
            swap(array, SR, mid);
            if (array[SL] == array[SR]) {
                int result = isEqual(array, SL, SR);
                if (result == -1) {
                    return;
                }
            }
            if (array[SL] > array[SR]) {
                swap(array, SL, SR);
            }
            if ((SR - SL) >= 100) {
                for (int i = SL + 1; i <= Math.sqrt(SR - SL); i++) {
                    if (array[SR] < array[i]) {
                        swap(array, SR, i);
                    } else if (array[SL] > array[i]) {
                        swap(array, SL, i);
                    }
                }
            }
            int i = SL + 1;
            while (i < SR) {
                int currItem = array[i];
                if (currItem >= array[SR]) {
                    array[i] = array[SR - 1];
                    insertRight(array, currItem, SR, array.length - 1);
                    SR--;
                } else if (currItem <= array[SL]) {
                    array[i] = array[SL + 1];
                    insertLeft(array, currItem, SL, 0);
                    SL++;
                    i++;
                } else {
                    i++;
                }
            }
            SL++;
            SR--;
        }
    }


    public static int isEqual(int[] array, int SL, int SR) {
        for (int k = SL + 1; k <= SR - 1; k++) {
            if (array[k] != array[SL]) {
                swap(array, k, SL);
                return k;
            }
        }
        return -1;
    }

    public static void insertRight(int[] array, int currItem, int SR, int right) {
        int j = SR;
        while (j <= right && currItem > array[j]) {
            array[j - 1] = array[j];
            j++;
        }
        array[j - 1] = currItem;
    }

    public static void insertLeft(int[] array, int currItem, int SL, int left) {
        int j = SL;
        while (j >= left && currItem < array[j]) {
            array[j + 1] = array[j];
            j--;
        }
        array[j + 1] = currItem;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
