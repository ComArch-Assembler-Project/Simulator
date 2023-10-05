# Simulator
Assembly simulator

> [!IMPORTANT]
> (simulator) อย่าแก้ printState หรือ stateStruct
> (simulator) Call printState 1 ครั้ง ก่อน instruction executes และ 1 ครั้ง  ก่อน simulator exits.  อย่า call printState ที่อื่น.
> (simulator) อย่า print "@@@" ที่ใดยกเว้นใน printState.
> (simulator) state.numMemory ต้อง เท่ากับ จำนวนบรรทัด ใน machine-code file.
> (simulator) Initialize ทุก registers ให้เป็น 0.

- status
   - [x] ADD 000
   - [x] NAND 001
   - [x] LW 010
   - [x] SW 011
   - [x] BEQ 100
   - [x] JALR 101
   - [x] BHALT 110
   - [x] NOOP 111

> [!IMPORTANT]
> R-type instructions (add, nand)
> - Bits 24-22 opcode
> - Bits 21-19 reg A (rs)
> - Bits 18-16 reg B (rt)
> - Bits 15-3 ไม่ใช้ (ควรตั้งไว้ที่ 0)
> - Bits 2-0  destReg (rd)


> [!IMPORTANT]
> I-type instructions (lw, sw, beq)
> - Bits 24-22 opcode
> - Bits 21-19 reg A (rs)
> - Bits 18-16 reg B (rt)
> - Bits 15-0 offsetField (เลข16-bit และเป็น 2’s complement  โดยอยู่ในช่วง –32768 ถึง 32767)

> [!IMPORTANT]
> J-Type instructions (jalr)
> - Bits 24-22 opcode
> - Bits 21-19 reg A (rs)
> - Bits 18-16 reg B (rd)
> - Bits 15-0 ไม่ใช้ (ควรตั้งไว้ที่ 0)


> [!IMPORTANT]
> O-type instructions (halt, noop)
> - Bits 24-22 opcode
> - Bits 21-0 ไม่ใช้ (ควรตั้งไว้ที่ 0)

#### Behavioral Simulator
- ส่วนที่ 2 ของ project นี้ก็คือการเขียน  program ที่สามารถ simulate machine code program input ของ ส่วนนี้ก็คือ machine code ที่ถูกสร้างจาก assembler program นี้ควรเริ่มจากการ
   - initialize registers ทุกตัวและ set program counter เป็น 0 และมันจะ simulate จนกระทั่ง halt simulator ควรจะเรียก printState (อธิบายข้างล่าง)
   - ก่อนที่จะทำแต่ละ instruction และก่อนที่จะ exit จาก program function นี้จะ print state ปัจจุบันของ machine (program counter, registers, memory) มันจะ print memory contents สำหรับแต่ละ memory location ที่ถูกกำหนดใน machine code file (addresses 0-9 ในตัวอย่างข้างบน) ใช้ machine code จาก assembler เป็น test case
- สำหรับ lw, sw และ beq อย่าลืมว่า offsetField เป็น 16 bits 2’s complement number ดังนั้นต้องแปลง negative ให้เป็น 32 bits negative integer ถ้าใช้เครื่องที่เป็น 32 bit integer โดยใช้ sign extension ใช้ function ในการแปลง
``` C++
    int
    convertNum(int num)
    {
          /* convert a 16-bit number into a 32-bit integer */
          if (num & (1<<15) ) {
              num -= (1<<16);
          }
          return(num);
    }
```

How to run:
java simulator.java machine_code.txt
