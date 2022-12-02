import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CPUTest3
{
    @Test
    public void testAssemble()
    {
        // checks conversion of assembly instructions of pop push call and return to bits
        String[] test = new String[]{"Pop R1", "Push R2", "Call 6", "Return"};
        String[] assembledCode = Assembler.assemble(test);
        assertEquals("0110010000000001", assembledCode[0]);
        assertEquals("0110000000000010", assembledCode[1]);
        assertEquals("0110100000000110", assembledCode[2]);
        assertEquals("0110110000000000", assembledCode[3]);
    }

    @Test
    public void testCallReturn()
    {
        // check outputs. First prints registers(interrupt0) and then memory (interrupt 1)
        // 1. jump to address 6 and push address of "Interrupt 1" in stack
        // 2. Interrupt 0 is at address 6
        // 3. Return pops the value of address in stack and jumps to that address
        // 4. interrupt 1 is at address that is popped out of stack
        String[] test = new String[]{"Call 6", "Interrupt 1", "halt", "Interrupt 0", "Return"};
        String[] programArray = Assembler.assemble(test);

        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);

        computer.preload(programArray);
        computer.run();
    }

    //test for pop and push instructions
    @Test
    public void testPopPush()
    {
        String[] test = new String[]{"move R1 5", "Push R1", "Pop R2", "halt"};
        // 1. move 5 to R1
        // 2. push value of R1 in stack
        // 3. pop value in stack to R2
        String[] programArray = Assembler.assemble(test);

        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);

        computer.preload(programArray);
        computer.run();
        assertEquals(5, computer.getMem().read(new Longword(1020)).getSigned());
        assertEquals(5, computer.getRegisters()[1].getSigned());
    }

}

