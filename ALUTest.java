import org.junit.Test;
import static org.junit.Assert.*;

public class ALUTest
{
    @Test
    public void testAnd()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        Bit bit3 = new Bit(false);
        Bit bit4 = new Bit(false);
        assertEquals(0, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(0), new Longword(0)).getSigned());
        assertEquals(4, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(5), new Longword(6)).getSigned());
        assertEquals(321, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(325), new Longword(987)).getSigned());
    }

    @Test
    public void testOr()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        Bit bit3 = new Bit(false);
        Bit bit4 = new Bit(true);
        assertEquals(1, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(1), new Longword(0)).getSigned());
        assertEquals(7, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(5), new Longword(6)).getSigned());
        assertEquals(991, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(325), new Longword(987)).getSigned());
    }

    @Test
    public void testXor()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        Bit bit3 = new Bit(true);
        Bit bit4 = new Bit(false);
        assertEquals(1, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(1), new Longword(0)).getSigned());
        assertEquals(3, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(5), new Longword(6)).getSigned());
        assertEquals(670, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(325), new Longword(987)).getSigned());
    }

    @Test
    public void testNot()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        Bit bit3 = new Bit(true);
        Bit bit4 = new Bit(true);
        assertEquals(-2, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(1), new Longword(0)).getSigned());
        assertEquals(-6, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(5), new Longword(6)).getSigned());
        assertEquals(-326, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(325), new Longword(987)).getSigned());
    }

    @Test
    public void testLeftShift()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        Bit bit3 = new Bit(false);
        Bit bit4 = new Bit(false);
        assertEquals(4, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(1), new Longword(2)).getSigned());
        assertEquals(1408, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(22), new Longword(6)).getSigned());
        assertEquals(389120, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(95), new Longword(12)).getSigned());
    }

    @Test
    public void testRightShift()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        Bit bit3 = new Bit(false);
        Bit bit4 = new Bit(true);
        assertEquals(1, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(4), new Longword(2)).getSigned());
        assertEquals(6080, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(389120), new Longword(6)).getSigned());
        assertEquals(101, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(3251), new Longword(5)).getSigned());
    }

    @Test
    public void testAdd()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        Bit bit3 = new Bit(true);
        Bit bit4 = new Bit(false);
        assertEquals(0, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(0), new Longword(0)).getSigned());
        assertEquals(2, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(1), new Longword(1)).getSigned());
        assertEquals(24, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(23), new Longword(1)).getSigned());
        assertEquals(150, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(50), new Longword(100)).getSigned());
        assertEquals(-1, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(-50), new Longword(49)).getSigned());
        assertEquals(-1, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(49), new Longword(-50)).getSigned());
    }

    @Test
    public void testSubtract()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        Bit bit3 = new Bit(true);
        Bit bit4 = new Bit(true);
        assertEquals(0, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(0), new Longword(0)).getSigned());
        assertEquals(0, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(1), new Longword(1)).getSigned());
        assertEquals(22, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(23), new Longword(1)).getSigned());
        assertEquals(-50, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(50), new Longword(100)).getSigned());
        assertEquals(-99, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(-50), new Longword(49)).getSigned());
        assertEquals(99, ALU.doOpt(bit1, bit2, bit3, bit4, new Longword(49), new Longword(-50)).getSigned());
    }

    @Test
    public void testMultiply()
    {
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(true);
        Bit bit3 = new Bit(true);
        Bit bit4 = new Bit(true);
        assertEquals(0, ALU.doOpt(bit1, bit2, bit3, bit4,new Longword(0), new Longword(0)).getSigned());
        assertEquals(2, ALU.doOpt(bit1, bit2, bit3, bit4,new Longword(2), new Longword(1)).getSigned());
        assertEquals(27, ALU.doOpt(bit1, bit2, bit3, bit4,new Longword(9), new Longword(3)).getSigned());
        assertEquals(502, ALU.doOpt(bit1, bit2, bit3, bit4,new Longword(251), new Longword(2)).getSigned());
        assertEquals(-138, ALU.doOpt(bit1, bit2, bit3, bit4,new Longword(69), new Longword(-2)).getSigned());
        assertEquals(-1, ALU.doOpt(bit1, bit2, bit3, bit4,new Longword(1), new Longword(-1)).getSigned());
    }


}



