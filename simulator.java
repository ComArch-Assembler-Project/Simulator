import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class simulator {
    private static final int NUMMEMORY = 65536; // maximum number of words in memory
    private static final int NUMREGS = 8; // number of machine registers
    private static final int MAXLINELENGTH = 1000;

    public static class State {
        int pc=0;
        int[] mem = new int[NUMMEMORY];
        int[] reg = new int[NUMREGS];
        int numMemory;
    }
    /*
         (address 0): 8454151 (hex 0x810007)
         (address 1): 9043971 (hex 0x8a0003)
         (address 2): 655361 (hex 0xa0001)
         (address 3): 16842754 (hex 0x1010002)
         (address 4): 16842749 (hex 0x100fffd)
         (address 5): 29360128 (hex 0x1c00000)
         (address 6): 25165824 (hex 0x1800000)
         (address 7): 5 (hex 0x5)
         (address 8): -1 (hex 0xffffffff)
         (address 9): 2 (hex 0x2)
     */


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

        //printState(state);
        //ตาต้าทำเอง
        String binaryString = Integer.toBinaryString(state.mem[0]);
        int originalNumber = Integer.parseInt(binaryString, 2);
        int extractedBits = (originalNumber >> 21) & 0b111;
        System.out.println("Bits 22, 23, 24: " + Integer.toBinaryString(extractedBits));
    }
}
