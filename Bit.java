import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static java.lang.System.out;

public class Bit {
    private boolean bit;

    public Bit() {
        this.bit = false;
    }

    public Bit(boolean bit) {
        this.bit = bit;
    }

    void set(boolean value) {
        // sets the value of the bit
        this.bit = value;
    }

    void toggle() {
        // changes the value from true to false or false to true
        this.bit = !this.bit;
    }

    void set() {
        // sets the bit to true
        this.bit = true;
    }

    void clear() {
        // sets the bit to false
        this.bit = false;
    }

    boolean getValue() {
        // returns the current value
        return this.bit;
    }

    Bit and(Bit other) {
        // performs and on two bits and returns a new bit set to the result
        return new Bit(this.bit && other.bit);
    }

    Bit or(Bit other) {
        // performs or on two bits and returns a new bit set to the result
        return new Bit(this.bit || other.bit);
    }

    Bit xor(Bit other) {
        // performs xor on two bits and returns a new bit set to the result
        return new Bit((this.bit && !other.bit) || (!this.bit && other.bit));
    }

    Bit not() {
        // performs not on the existing bit, returning the result as a new bit
        return new Bit(!this.bit);
    }

    @Override
    public String toString() {
        // returns “t” or “f”
        if (this.bit) {
            return "t";
        } else {
            return "f";
        }
    }

    public void runTests() {
        Result result = JUnitCore.runClasses(BitTest.class);

        for (Failure failure : result.getFailures()) {
            out.println(failure.toString());
        }

        out.println(result.wasSuccessful());
    }

    public static void main(String[] args) {
        new Bit().runTests();
    }
}



