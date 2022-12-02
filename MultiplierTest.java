import org.junit.Test;
import static org.junit.Assert.*;

public class MultiplierTest
{
    @Test
    public void testMultiplier()
    {
        assertEquals(0, Multiplier.multiply(new Longword(0), new Longword(0)).getSigned());
        assertEquals(2, Multiplier.multiply(new Longword(1), new Longword(2)).getSigned());
        assertEquals(28, Multiplier.multiply(new Longword(4), new Longword(7)).getSigned());
        assertEquals(502, Multiplier.multiply(new Longword(2), new Longword(251)).getSigned());
        assertEquals(-69, Multiplier.multiply(new Longword(-1), new Longword(69)).getSigned());
        assertEquals(-1, Multiplier.multiply(new Longword(-1), new Longword(1)).getSigned());

    }
}
