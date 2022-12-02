import org.junit.Test;
import static org.junit.Assert.*;
public class LongwordTest
{
    @Test
    public void testGetBit()
    {
        Longword longword = new Longword();
        longword.set(0);
        Bit bit = longword.getBit(0);
        assertFalse(bit.getValue());
    }

    @Test
    public void testSetBit()
    {
        Longword longword = new Longword();
        Bit bit = new Bit(true);
        longword.setBit(0, bit);
        assertTrue(longword.getBit(0).getValue());
    }

    @Test
    public void testAnd()
    {
        Longword longword1 = new Longword();
        Longword longword2 = new Longword();
        longword1.set(0);
        longword2.set(0);
        Longword longword3 = longword1.and(longword2);
        assertFalse(longword3.getBit(0).getValue());
    }

    @Test
    public void testOr()
    {
        Longword longword1 = new Longword();
        Longword longword2 = new Longword();
        longword1.set(0);
        longword2.set(0);
        Longword longword3 = longword1.or(longword2);
        assertFalse(longword3.getBit(0).getValue());
    }

    @Test
    public void testXor()
    {
        Longword longword1 = new Longword();
        Longword longword2 = new Longword();
        longword1.set(0);
        longword2.set(0);
        Longword longword3 = longword1.xor(longword2);
        assertFalse(longword3.getBit(0).getValue());
    }

    @Test
    public void testNot()
    {
        Longword longword = new Longword();
        longword.set(0);
        Longword longword2 = longword.not();
        assertTrue(longword2.getBit(0).getValue());
    }

    @Test
    public void testRightShift()
    {
        Longword longword = new Longword();
        longword.set(0);
        Longword longword2 = longword.rightShift(0);
        assertFalse(longword2.getBit(0).getValue());
    }

    @Test
    public void testLeftShift()
    {
        Longword longword = new Longword();
        longword.set(0);
        Longword longword2 = longword.leftShift(0);
        assertFalse(longword2.getBit(0).getValue());
    }

    @Test
    public void testToString()
    {
        Longword longword = new Longword();
        longword.set(0);
        String string = longword.toString();
        assertEquals(" 0000 0000 0000 0000 0000 0000 0000 0000", string);
    }

    @Test
    public void testGetUnsigned()
    {
        Longword longword = new Longword();
        longword.set(0);
        long unsigned = longword.getUnsigned();
        assertEquals(0, unsigned);
    }

    @Test
    public void testGetSigned()
    {
        Longword longword = new Longword();
        longword.set(0);
        long signed = longword.getSigned();
        assertEquals(0, signed);
    }

    @Test
    public void testCopy()
    {
        Longword longword = new Longword();
        longword.set(0);
        Longword longword2 = new Longword();
        longword2.copy(longword);
        assertEquals(longword.toString(), longword2.toString());
    }

    @Test
    public void testSet()
    {
        Longword longword = new Longword();
        longword.set(0);
        assertEquals(0, longword.getSigned());
    }
}