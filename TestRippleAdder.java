import org.junit.Test;
import static org.junit.Assert.*;

public class TestRippleAdder
{
    @Test
    public void testAdd()
    {
        assertEquals(0, RippleAdder.add(new Longword(0), new Longword(0)).getSigned());
        assertEquals(2, RippleAdder.add(new Longword(1), new Longword(1)).getSigned());
        assertEquals(24, RippleAdder.add(new Longword(23), new Longword(1)).getSigned());
        assertEquals(150, RippleAdder.add(new Longword(50), new Longword(100)).getSigned());
        assertEquals(-1, RippleAdder.add(new Longword(-50), new Longword(49)).getSigned());
        assertEquals(-1, RippleAdder.add(new Longword(49), new Longword(-50)).getSigned());
    }

    @Test
    public void testSubtract()
    {
        assertEquals(0, RippleAdder.subtract(new Longword(0), new Longword(0)).getSigned());
        assertEquals(0, RippleAdder.subtract(new Longword(1), new Longword(1)).getSigned());
        assertEquals(22, RippleAdder.subtract(new Longword(23), new Longword(1)).getSigned());
        assertEquals(-50, RippleAdder.subtract(new Longword(50), new Longword(100)).getSigned());
        assertEquals(-99, RippleAdder.subtract(new Longword(-50), new Longword(49)).getSigned());
        assertEquals(99, RippleAdder.subtract(new Longword(49), new Longword(-50)).getSigned());
    }

}
