public class ALU
{

    public static Longword doOpt(Bit opr1, Bit opr2, Bit opr3, Bit opr4, Longword a, Longword b)
    {
        boolean bit1 = opr1.getValue();
        boolean bit2 = opr2.getValue();
        boolean bit3 = opr3.getValue();
        boolean bit4 = opr4.getValue();

        Longword result = new Longword();
        if (bit1 && !bit2 && !bit3 && !bit4){
            // 1000 and
            result = a.and(b);
        }
        else if (bit1 && !bit2 && !bit3 && bit4) {
            // 1001 or
            result = a.or(b);
        }
        else if (bit1 && !bit2 && bit3 && !bit4) {
            // 1010 xor
            result = a.xor(b);
        }
        else if (bit1 && !bit2 && bit3 && bit4) {
            // 1011 not
            result = a.not();
        }
        else if (bit1 && bit2 && !bit3 && !bit4) {
            // 1100 left shift
            int amount = lastFiveBits(b);
            result = a.leftShift(amount);
        }
        else if (bit1 && bit2 && !bit3 && bit4) {
            // 1101 right shift
            int amount = lastFiveBits(b);
            result = a.rightShift(amount);
        }
        else if (bit1 && bit2 && bit3 && !bit4) {
            // 1110 add
            result = RippleAdder.add(a, b);
        }
        else if (bit1 && bit2 && bit3 && bit4) {
            // 1111 subtract
            result = RippleAdder.subtract(a, b);
        }
        else if (!bit1 && bit2 && bit3 && bit4) {
            // 0111 multiply
            result = Multiplier.multiply(a, b);
        }
        return result;
    }

    private static int lastFiveBits(Longword b)
    {
        int amount = 0;
        for (int i = 0; i < 5; i++){
            if(b.getBit(i).getValue()){
                amount += Math.pow(2, i);
            }
        }
        return amount;
    }

}


