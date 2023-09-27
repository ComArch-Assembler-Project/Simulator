import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class simulator {
    private static final int NUMMEMORY = 65536; // maximum number of words in memory
    private static final int NUMREGS = 8; // number of machine registers
    private static final int MAXLINELENGTH = 1000;

    public static class State {
        int pc = 0;
        int[] mem = new int[NUMMEMORY];
        int[] reg = new int[NUMREGS];
        int numMemory;
    }
    /*
         (address 0): 8454151 (hex 0x810007) lw 0 1 five :load reg1 with 5 (uses symbolic address)
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
        //int
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

        //ตาต้าทำเอง
        String binaryString = Integer.toBinaryString(state.mem[0]);
        int originalNumber = Integer.parseInt(binaryString, 2);
        int extractedBits = (originalNumber >> 21) & 0b111;
        String X = Integer.toBinaryString(extractedBits);
        int[] op_args = new int[X.length()];
        for (int i = 0; i < X.length(); i++) {
            char digit = X.charAt(i);
            op_args[i] = (digit == '1') ? 1 : 0;
        }
        System.out.println("Original string: " + X);
        System.out.println("Split array: " + op_args[0]+op_args[1]+op_args[2]);

        state.pc = 0;
        int regA, regB, offset;
        /*
        int pc = state.pc;
        int total = 0;
        int[] arg = new int[3];
        int regA, regB, destReg;
        for(int i = 1; i != 1; i++){
            printState(state);
            total++;
            switch (mem[pc] >> 22){
                case 0: //add 000
                    rFormat(mem[pc], arg);
                    regA = state.reg[arg[0]];
                    regB = state.reg[arg[1]];
                    state.reg[arg[2]] = regA + regB;
                    break;

                case 1://nand 001
                    rFormat(mem[pc], arg);
                    regA = state.reg[arg[0]];
                    regB = state.reg[arg[1]];
                    state.reg[arg[2]] = ~(regA & regB);
                    break;

                case 2://lw 010
                    iFormat(mem[pc], arg);
                    regA = state.reg[arg[0]];
                    regB = state.reg[arg[1]];
                    //add code here ไม่รู้ละทำต่อให้หน่อย
                    break;

                case 3://sw 011
                    iFormat(mem[pc], arg);
                    regA = state.reg[arg[0]];
                    regB = state.reg[arg[1]];
                    //add code here ไม่รู้ละทำต่อให้หน่อย
                    break;

                case 4: //beq 100:
                    iFormat(mem[pc], arg);
                    regA = state.reg[arg[0]];
                    regB = state.reg[arg[1]];
                    //add code here ไม่รู้ละทำต่อให้หน่อย
                    break;

                case 5://jalr 101: เก็บค่า PC+1 ไว้ใน regB ซึ่ง PC คือ address ของ jalr instruction และกระโดดไปที่ address ที่ถูกเก็บไว้ใน regA แต่ถ้า regA และ regB คือ register ตัวเดียวกัน ให้เก็บ PC+1 ก่อน และค่อยกระโดดไปที่ PC+1
                    jFormat(mem[pc], arg);
                    regA = state.reg[arg[0]];
                    regB = state.reg[arg[1]];
                    if (regA = regB){
                        pc++;
                    }else {
                        pc = regA;
                    }
                    break;

                case 6://bhalt : เพิ่มค่า PC เหมือน instructions อื่นๆ และ halt เครื่อง นั่นคือให้ simulator รู้ว่าเครื่องมีการ halted เกิดขึ้น
                    pc++;
                    i = -1;
                    break;

                case 7://noop
                    //ไม่ทำอะไร
                    break;
            }
        }
    }

    private static void rFormat(int bit, int[] arg){ //r-format
        arg[0] = (bit & (7 << 19 )) >> 19; // regA เอา bit ที่ 22-20
        arg[1] = (bit & (7 << 16 )) >> 16; // regB เอา bit ที่ 19-17
        arg[2] = bit & 7; // destReg เอา bit ที่ 2-0
    }

    private static void iFormat(int bit, int[] arg){
        arg[0] = (bit & (7 << 19 )) >> 19; // regA เอา bit ที่ 22-20
        arg[1] = (bit & (7 << 16 )) >> 16; // regB เอา bit ที่ 19-17
        // ไม่รู้ละทำต่อให้หน่อย
    }

    private static void jFormat(int bit, int[] arg) {
        arg[0] = bit & 0x3FFFFFF; // regA เอา 22 bit แรก (0x3FFFFFF คือ 1 22ตัว)
    }
         */
    }
}