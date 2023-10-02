# Simulator
Assembly simulator

- status
   - [ ] ADD
   - [ ] NAND
   - [ ] LW
   - [ ] SW
   - [ ] BEQ
   - [ ] JALR
   - [ ] BHALT
   - [ ] NOOP

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

How to run:
2. java simulator.java machine_code.txt
