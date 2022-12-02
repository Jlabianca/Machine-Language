import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.lang.reflect.Field;
import java.util.ArrayList;
import sun.misc.Unsafe;
import static java.lang.System.out;

public class Memory
{

    final private Bit[] Memory = new Bit[1024*8];

    // initializing all memory with false bits.
    public Memory(){
        for (int i = 0; i < Memory.length; i++){
            Memory[i] = new Bit(false);
        }
    }

    public Longword read(Longword address) {

        Longword value = new Longword();
        int addr = address.getSigned();

        if (addr < 0 || addr >= 1021) {
            System.out.println("Invalid Input. Type between 0 and 1020.");
        }

        else {
            for (int i = 0; i < 32; i++) {
                value.setBit(i, Memory[addr * 8 + i]);
            }
        }

        return value;

    }

    public void write(Longword address, Longword value) {

        int addr = address.getSigned();

        if (addr < 0 || addr >= 1021) {
            System.out.println("Invalid Input. Type between 0 and 1020.");
            return;
        }

        else {
            for (int i = 0; i < 32; i++) {
                Memory[addr * 8 + i] = value.getBit(i);
            }
        }
    }


    void runTests()
    {
        Result result = JUnitCore.runClasses(TestMemory.class);
        for (Failure failure : result.getFailures())
        {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

    static void main(String[] args)
    {
        new Memory().runTests();
    }

}


