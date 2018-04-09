import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class KnapsackKnapp1 {
    private Writer writer;
    private int setSize;
    private int sackSize;
    private int[] weightArray;
    private int[] valueArray;
    private int[][] knappsackArray;

    private KnapsackKnapp1(File dataFile){
        knapsackSolutionSet(dataFile);
    }

    private void createKnapsackArray(File dataFile){
        try {
            Scanner in = new Scanner(dataFile);
            while (in.findInLine("c ") != null) {
                in.nextLine();
            }
            setSize = in.nextInt();
            write(String.format("Set Size: %d%n", setSize));
            sackSize = in.nextInt();
            write(String.format("Sack Size: %d%n", sackSize));
            weightArray = new int[setSize + 1];
            write("Weights:");
            for (int i = 1; i <= setSize; i++) {
                weightArray[i] = in.nextInt();
                write(String.format(" %d", weightArray[i]));
            }
            write("\n");
            valueArray = new int[setSize + 1];
            write("Values:");
            for (int i = 1; i <= setSize; i++) {
                valueArray[i] = in.nextInt();
                write(String.format(" %d", valueArray[i]));
            }
            write("\n");
            knappsackArray = new int[setSize + 1][sackSize + 1];
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void knapsackSolutionSet(File dataFile){
        createOutputFile();
        createKnapsackArray(dataFile);
        for (int k = 0; k <= sackSize; k++) {
            knappsackArray[0][k] = 0;
        }
        for (int i = 1; i <= setSize; i++) {
            for (int j = 0; j <= sackSize; j++) {
                if (j - weightArray[i] >= 0) {
                    int firstValue = knappsackArray[i - 1][j];
                    int secondValue = knappsackArray[i - 1][j - weightArray[i]] + valueArray[i];
                    if (firstValue >= secondValue) {
                        knappsackArray[i][j] = firstValue;
                    } else {
                        knappsackArray[i][j] = secondValue;
                    }
                } else {
                    knappsackArray[i][j] = knappsackArray[i - 1][j];
                }
            }
        }
        write(String.format("Max Value: %d%n", knappsackArray[setSize][sackSize]));
        write("Optimal Solution:\n");
        int w = sackSize;
        for (int i = setSize; i > 0; i--){
            if (knappsackArray[i][w] != knappsackArray[i - 1][w]){
                write(String.format("Item: %d Value: %d%n", i, valueArray[i]));
                w = w - weightArray[i];
            }
        }
        System.out.println("Debug");
    }

    private void createOutputFile(){
        try {
            writer = new PrintWriter("knappsackOutput.txt");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void write(String s){
        try {
            writer.write(s);
            writer.flush();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        if (args.length != 1){
            System.out.println("Usage: <fullFilePath>");
            System.exit(1);
        }
        else{
            new KnapsackKnapp1(new File(args[0]));
        }
    }
}
