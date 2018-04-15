import java.io.*;
import java.util.Scanner;

public class WarshallFloydKnapp1 {
    private Writer writer;
    private int nodes;
    private int[][] WDAMatrix;
    private int[][] predecessorMatrix;

    WarshallFloydKnapp1(File dataFile) {
        shortestPath(dataFile);
    }

    void constructWDAMatrix(File dataFile) {
        try {
            Scanner in = new Scanner(dataFile);
            while (in.findInLine("c ") != null) {
                in.nextLine();
            }
            nodes = in.nextInt();
            WDAMatrix = new int[nodes + 1][nodes + 1];
            predecessorMatrix = new int[nodes + 1][nodes + 1];
            for (int i = 1; i <= nodes; i++) {
                for (int j = 1; j <= nodes; j++) {
                    if (i == j) {
                        WDAMatrix[i][j] = 0;
                        predecessorMatrix[i][j] = 0;
                    }
                    else {
                        WDAMatrix[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
            while (in.hasNextLine()){
                int i = in.nextInt();
                int j = in.nextInt();
                int k = in.nextInt();
                WDAMatrix[i][j] = k;
                predecessorMatrix[i][j] = i;
            }
            write(String.format("Weighted Matrix %d:%n", 0));
            matrixToString(WDAMatrix);
            write(String.format("Predecessor Matrix %d:%n", 0));
            matrixToString(predecessorMatrix);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void shortestPath(File dataFile) {
        createOutputFile();
        constructWDAMatrix(dataFile);
        for (int k = 1; k <= nodes; k++) {
            for (int i = 1; i <= nodes; i++) {
                for (int j = 1; j <= nodes; j++) {
                    if (WDAMatrix[i][k] == Integer.MAX_VALUE || WDAMatrix[k][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (WDAMatrix[i][j] > WDAMatrix[i][k] + WDAMatrix[k][j]) {
                        WDAMatrix[i][j] = WDAMatrix[i][k] + WDAMatrix[k][j];
                        predecessorMatrix[i][j] = predecessorMatrix[k][j];
                    }
                }
            }
            write(String.format("Weighted Matrix %d:%n", k));
            matrixToString(WDAMatrix);
            write(String.format("Predecessor Matrix %d:%n", k));
            matrixToString(predecessorMatrix);
        }
        write(String.format("%nPath from %d to %d:%n%s", 1, 4, path(1, 4)));
    }

    String path(int i, int j) {
        if (i == j){
            return String.format("%d", i);
        }
        else if (predecessorMatrix[i][j] == 0){
            return String.format("No path from %d to %d", i, j);
        }
        else {
            return String.format("%d - %s", j, path(i, predecessorMatrix[i][j]));
        }
    }

    private void createOutputFile() {
        try {
            writer = new PrintWriter("floydOutput.txt");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void write(String s) {
        try {
            writer.write(s);
            writer.flush();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void matrixToString(int[][] matrix){
        int counter = 0;
        for(int[] row : matrix) {
            if (counter != 0){
                printRow(row);
            }
            counter++;
        }
    }

    private void printRow(int[] row) {
        int counter = 0;
        for (int i : row) {
            if (counter != 0) {
                if (i == Integer.MAX_VALUE) {
                    write("∞" + "\t");
                } else {
                    write(i + "\t");
                }
            }
            counter++;
        }
        write("\n");
    }

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: <fullFilePath>");
            System.exit(1);
        }
        else{
            new WarshallFloydKnapp1(new File(args[0]));
        }
    }
}
