import org.junit.Test;
import static org.junit.Assert.*;
public class TestMemory
{
    @Test
    public void testMemory()
    {
        // testing if memory is initialized correctly
        Memory memory = new Memory();
        Longword value = memory.read(new Longword(1000));
        assertEquals(value.getSigned(), 0);
    }

    @Test
    public void testMemoryWriteRead()
    {
        Memory memory = new Memory();
        Longword address = new Longword(1000);
        Longword value = new Longword(2390);
        memory.write(address, value);
        Longword readValue = memory.read(address);
        assertEquals(readValue.getSigned(), 2390);

        address = new Longword(1020);
        value = new Longword(8);
        memory.write(address, value);
        readValue = memory.read(address);
        assertEquals(readValue.getSigned(), 8);
    }
}
