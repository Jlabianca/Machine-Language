import org.junit.Test;
import static org.junit.Assert.*;

public class AssemblerTest
{
    @Test
    public void testMove()
    {
        String[] test = new String[]{"move R2 25", "move R3 100"};
        String[] bitTest = Assembler.assemble(test);
        assertEquals("0001001000011001", bitTest[0]);
        assertEquals("0001001101100100", bitTest[1]);
    }

    @Test
    public void testOps()
    {
        String[] test = new String[]{"add R2 R3 R4", "multiply R2 R3 R5"};
        String[] bitTest = Assembler.assemble(test);
        assertEquals("1110001000110100", bitTest[0]);
        assertEquals("0111001000110101", bitTest[1]);
    }

    @Test
    public void testInterrupt()
    {
        String[] test = new String[]{"Interrupt 0", "Interrupt 1"};
        String[] bitTest = Assembler.assemble(test);
        assertEquals("0010000000000000", bitTest[0]);
        assertEquals("0010000000000001", bitTest[1]);
    }

}

