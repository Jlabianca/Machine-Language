import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static java.lang.System.out;

public class RippleAdder
{
    public static Longword add(Longword a, Longword b)
    {
        Bit carry = new Bit();
        Longword res = new Longword();
        for (int i = 0; i < 32; i++) {
            res.setBit(i, addBit(a.getBit(i), b.getBit(i), carry));
        }
        return res;
    }

    private static Bit addBit(Bit a, Bit b, Bit carry) {
        Bit res = new Bit(false);
        if (a.xor(b).xor(carry).getValue())
            res.set();
        if (a.and(b).or(carry.and(a.xor(b))).getValue())
            carry.set();
        else carry.set(false);
        return res;
    }

    public static Longword subtract(Longword a, Longword b)
    {
        Longword twosComplement = b.twosComlpement();
        return add(a, twosComplement);
    }

    static void runTests()
    {
        Result result = JUnitCore.runClasses(TestRippleAdder.class);

        for (Failure failure : result.getFailures())
        {
            out.println(failure.toString());
        }
        out.println(result.wasSuccessful());
    }

    public static void main(String[] args)
    {
        runTests();
    }

}




