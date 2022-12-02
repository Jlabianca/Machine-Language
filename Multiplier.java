import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static java.lang.System.out;

public class Multiplier
{

    public static Longword multiply(Longword a, Longword b)
    {
        Longword temp = a;
        Longword result = new Longword();
        for (int i = 0; i < 32; i++){
            if (b.getBit(i).getValue())
            {
                result = RippleAdder.add(result, temp);
            }
            temp = temp.leftShift(1);
        }
        return result;
    }

    void runTests()
    {
        Result result = JUnitCore.runClasses(MultiplierTest.class);

        for (Failure failure : result.getFailures())
        {
            out.println(failure.toString());
        }

        out.println(result.wasSuccessful());
    }

    public static void main(String[] args)
    {
        new MultiplierTest().testMultiplier();
    }

}



