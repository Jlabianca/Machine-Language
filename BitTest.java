import org.junit.Test;

import static org.junit.Assert.*;

public class BitTest
{

    @Test
    public void testSetValue()
    {
        Bit bit = new Bit();
        bit.set(false);
        assertFalse(bit.getValue());
    }

    @Test
    public void testToggle()
    {
        Bit bit = new Bit();
        bit.toggle();
        assertTrue(bit.getValue());
    }

    @Test
    public void testSet()
    {
        Bit bit = new Bit();
        bit.set();
        assertTrue(bit.getValue());
    }

    @Test
    public void testClear()
    {
        Bit bit = new Bit(true);
        bit.clear();
        assertFalse(bit.getValue());
    }

    @Test
    public void testAnd()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        Bit bit3 = bit1.and(bit2);
        assertTrue(bit3.getValue());
    }

    @Test
    public void testOr()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(false);
        Bit bit3 = bit1.or(bit2);
        assertTrue(bit3.getValue());
    }

    @Test
    public void testXor()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = new Bit(true);
        Bit bit3 = bit1.xor(bit2);
        assertFalse(bit3.getValue());
    }

    @Test
    public void testNot()
    {
        Bit bit1 = new Bit(true);
        Bit bit2 = bit1.not();
        assertFalse(bit2.getValue());
    }

    @Test
    public void testToString()
    {
        Bit bit1 = new Bit(true);
        assertEquals("t", bit1.toString());
    }
}





