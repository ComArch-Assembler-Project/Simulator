import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class simulator {
    private static final int NUMMEMORY = 65536; // maximum number of words in memory
    private static final int NUMREGS = 8; // number of machine registers
    private static final int MAXLINELENGTH = 1000;

    public static class State {
        int pc;
        int[] mem = new int[NUMMEMORY];
        int[] reg = new int[NUMREGS];
        int numMemory;
    }

    public static void printState(State state) {
        System.out.println("\n@@@\nstate:");
        System.out.println("\tpc " + state.pc);
        System.out.println("\tmemory:");
        for (int i = 0; i < state.numMemory; i++) {
            System.out.println("\t\tmem[ " + i + " ] " + state.mem[i]);
        }
        System.out.println("\tregisters:");
        for (int i = 0; i < NUMREGS; i++) {
            System.out.println("\t\treg[ " + i + " ] " + state.reg[i]);
        }
        System.out.println("end state");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("error: usage: java Simulator <machine-code file>");
            System.exit(1);
        }

        String fileName = args[0];
        State state = new State();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                state.mem[state.numMemory] = Integer.parseInt(line);
                System.out.println("memory[" + state.numMemory + "]=" + state.mem[state.numMemory]);
                state.numMemory++;
            }
        } catch (IOException e) {
            System.err.println("error: can't open file " + fileName);
            e.printStackTrace();
            System.exit(1);
        }

        // Simulation code can be added here.

        // Printing the initial state.
        printState(state);
    }
}
