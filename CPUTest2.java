import org.junit.Test;
import static org.junit.Assert.*;

public class CPUTest2
{
    // test for conversion of assembly to bits
    @Test
    public void testAssemble()
    {
        String[] test = new String[]{"jump 4", "compare R1 R2"};
        String[] assembledCode = Assembler.assemble(test);
        assertEquals("0011000000000100", assembledCode[0]);
        assertEquals("0100000000010010", assembledCode[1]);
    }

    //test for jump
    @Test
    public void testJump()
    {
        String[] test = new String[]{"jump 4","move R1 5","interrupt 0","halt"};
        String[] programArray = Assembler.assemble(test);

        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);

        computer.preload(programArray);
        computer.run();
        assertEquals(0, computer.getRegisters()[1].getSigned());
    }

    //test for branch
    @Test
    public void testBranch()
    {
        String[] test = new String[]{"move R4 15","move R5 15","compare R5 R4", "branchifequal 2", "add R4 R5 R6", "interrupt 0", "halt"};
        String[] programArray = Assembler.assemble(test);

        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);

        computer.preload(programArray);
        computer.run();
        // as R4 and R5 are equal, branch will branch 2 ahead (interrupt 0) and R3 wouldn't show sum of R1 R2
        assertEquals(0, computer.getRegisters()[6].getSigned());
    }

}


