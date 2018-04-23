import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class TopoSortKnapp1 {
    private Writer writer;
    private int nodes;
    private LinkedList<Integer> adjacencyList[];
    private int[] inDegreeArray;

    TopoSortKnapp1(File dataFile) {
        sort(dataFile);
    }

    void createDiGraph(File dataFile) {
        try {
            Scanner in = new Scanner(dataFile);
            while (in.findInLine("c ") != null) {
                in.nextLine();
            }
            nodes = in.nextInt();
            inDegreeArray = new int[nodes + 1];
            adjacencyList = new LinkedList[nodes + 1];
            for (int i = 1; i <= nodes; i++) {
                adjacencyList[i] = new LinkedList<>();
            }
            while (in.hasNextLine()) {
                int u = in.nextInt();
                int v = in.nextInt();
                if (!adjacencyList[u].contains(v)) {
                    adjacencyList[u].add(v);
                    inDegreeArray[v]++;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void sort(File dataFile) {
        createOutputFile();
        createDiGraph(dataFile);
        LinkedList<Integer> S = new LinkedList<>();
        for (int u = 1; u <= nodes; u++) {
            if (inDegreeArray[u] == 0) {
                S.push(u);
            }
        }
        int i = 1;
        String graphOutput = "";
        while (!S.isEmpty()) {
            int u = S.pop();
            graphOutput = String.format("%s%d", graphOutput, u);
            i++;
            for (int v : adjacencyList[u]) {
                inDegreeArray[v]--;
                if (inDegreeArray[v] == 0) {
                    S.push(v);
                }
            }
        }
        if (i > nodes) {
            write(graphOutput);
        }
        else {
            write("The graph contains a cycle.");
        }
    }

    private void createOutputFile() {
        try {
            writer = new PrintWriter("topoOutput.txt");
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

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: <fullFilePath>");
            System.exit(1);
        }
        else{
            new TopoSortKnapp1(new File(args[0]));
        }
    }
}
