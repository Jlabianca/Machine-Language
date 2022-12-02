import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static java.lang.System.out;

public class Longword implements ILongword
{
    private final Bit[] longword = new Bit[32];

    Longword()
    {
        for (int i = 0; i < 32; i++)
        {
            this.longword[i] = new Bit(false);
        }
    }

    public Longword(int i) {
        this.set(i);
    }

    @Override
    public Bit getBit(int i) // Get Bit i
    {
        return this.longword[i];
    }

    @Override
    public void setBit(int i, Bit value) // set bit i's value
    {
        this.longword[i] = value;
    }

    @Override
    public Longword and(Longword other) // and two Longwords, returning a third
    {
        Longword result = new Longword();
        for (int i=0;i<32;i++)
        {
            result.longword[i] = this.longword[i].and(other.longword[i]);
        }
        return result;
    }

    @Override
    public Longword or(Longword other) // or two Longwords, returning a third
    {
        Longword result = new Longword();
        for (int i=0;i<32;i++)
        {
            result.longword[i] = this.longword[i].or(other.longword[i]);
        }
        return result;
    }

    @Override
    public Longword xor(Longword other) // xor two Longwords, returning a third
    {
        Longword result = new Longword();
        for (int i=0;i<32;i++)
        {
            result.longword[i] = this.longword[i].xor(other.longword[i]);
        }
        return result;
    }

    @Override
    public Longword not() // negate this Longword, creating another
    {
        Longword result = new Longword();
        for (int i=0;i<32;i++)
        {
            result.longword[i] = this.longword[i].not();
        }
        return result;
    }

    @Override
    public Longword rightShift(int amount) // rightshift this Longword by amount bits, creating a new Longword
    {
        Longword result = new Longword();
        for (int i=0;i<32;i++)
        {
            if (i+amount<32)
                result.longword[i] = this.longword[i+amount];
            else
                result.longword[i] = new Bit(false);
        }
        return result;
    }

    @Override
    public Longword leftShift(int amount) // leftshift this Longword by amount bits, creating a new Longword
    {
        Longword result = new Longword();
        for (int i=0;i<32;i++)
        {
            if (i-amount>=0)
                result.longword[i] = this.longword[i-amount];
            else
                result.longword[i] = new Bit(false);
        }
        return result;
    }

    @Override
    public long getUnsigned() // returns the value of this Longword as a long
    {
        long result = 0;
        for (int i=0;i<32;i++)
        {
            if (this.longword[i].getValue())
                result += Math.pow(2, i);
        }
        return result;
    }

    @Override
    public int getSigned() // returns the value of this Longword as a signed int
    {
        int result = 0;
        if (this.longword[31].getValue())
            result = (int) (Math.pow(2, 31) * -1);
        for (int i=0;i<31;i++)
        {
            if (this.longword[i].getValue())
                result += Math.pow(2, i);
        }
        return result;
    }

//    public Bit[] getSlicedWord(int start, int end){
//        Bit[] slice = new Bit[end - start];
//        // Copy elements of arr to slice
//        for (int i = 0; i < slice.length; i++) {
//            slice[i] = [start + i];
//        }
//        return slice;
//    }

    @Override
    public void copy(Longword other) // copies the values of the bits from another Longword into this one
    {
        System.arraycopy(other.longword, 0, this.longword, 0, 32);
    }

    @Override
    public void set(int value) // set the value of the bits of this Longword (used for tests)
    {
        boolean neg = false;
        if (value < 0) {
            neg = true;
            value *= -1;
        }
        for (int i=0;i<32;i++)
        {
            if (value%2==1)
                this.longword[i] = new Bit(true);
            else
                this.longword[i] = new Bit(false);
            value /= 2;
        }
        if (neg)
            this.copy(this.twosComlpement());
    }

    public Longword twosComlpement()
    {
        Longword res = new Longword();
        res.copy(this);
        int i = 0;
        while (i < 32 && !res.getBit(i).getValue())
            i++;
        for (i++; i < 32; i++) {
            res.setBit(i, res.getBit(i).not());
        }
        return res;
    }

    @Override
    public String toString() // returns a comma separated string of f's and t's: "fffff (etcetera)" for example
    {
        StringBuilder result = new StringBuilder();
        for (int i=0;i<32;i++)
        {
            if (this.longword[i].getValue())
                result.insert(0, "1");
            else
                result.insert(0, "0");
            if( (i+1) % 4 == 0){
                result.insert(0, " ");
            }

        }
        return result.toString();
    }

    void runTests()
    {
        Result result = JUnitCore.runClasses(LongwordTest.class);

        for (Failure failure : result.getFailures())
        {
            out.println(failure.toString());
        }

        out.println(result.wasSuccessful());
    }

    static void main(String[] args)
    {
        new Longword().runTests();
    }

}

