import java.nio.LongBuffer;
import java.util.Base64;

public class Computer
{
    // when halt bit is false, computer keep looping
    private Bit bit = new Bit(false);
    // opcode bits
    private Bit opBit1, opBit2, opBit3, opBit4;
    private Longword Base64;
    private Longword PC;
    private Longword currentInstruction = new Longword();
    private Longword[] registers = new Longword[16];
    private Longword op1 = new Longword();
    private Longword op2 = new Longword();
    private Longword result = new Longword();
    private Memory mem;// private Memory object

    private Longword getLast4Bits = new Longword(15);
    private Longword getLast8Bits = new Longword(255);
    private Longword getLast12Bits = new Longword(4095);
    private Longword getLast10Bits = new Longword(1023);

    private boolean halt = false;
    private boolean move = false;
    private boolean interrupt = false;
    private boolean jump = false;
    private boolean compare = false;
    private boolean branch = false;
    private boolean isALUInstruction = false;
    private boolean popPush = false;
    public Bit[] compareBits;

    // stack pointer
    private Longword SP = new Longword(1020);

    public Computer(Memory mem, Longword[] registers){
        this.mem = new Memory();
        for (int i = 0; i < registers.length; i++){
            this.registers[i] = new Longword();
        }
        this.PC = new Longword(0);
        this.compareBits = new Bit[2];
        this.compareBits[0] = new Bit(false);
        this.compareBits[1] = new Bit(false);
    }
    public void fetch()
    {
        // reading next instruction from memory (16 bits)
        currentInstruction = this.mem.read(PC).and(new Longword(65535));;
        // getting address for next instruction
        this.PC = RippleAdder.add(this.PC, new Longword(2));

    }

    public void decode() {

        // get opcode bits from instruction
        opBit1 = currentInstruction.getBit(15);
        opBit2 = currentInstruction.getBit(14);
        opBit3 = currentInstruction.getBit(13);
        opBit4 = currentInstruction.getBit(12);

        halt = isHalt(opBit1, opBit2, opBit3, opBit4); //0
        move = isMove(opBit1, opBit2, opBit3, opBit4); //1
        interrupt = isInterrupt(opBit1, opBit2, opBit3, opBit4); //2
        jump = isJump(opBit1, opBit2, opBit3, opBit4);//3
        compare = isCompare(opBit1, opBit2, opBit3, opBit4); //4
        branch = isBranch(opBit1, opBit2, opBit3, opBit4); //5
        popPush = isPopPush(opBit1, opBit2, opBit3, opBit4); //6

        bit = new Bit(halt);
        isALUInstruction = !move && !halt  && !interrupt && !jump && !compare && !branch && !popPush;

        if(isALUInstruction){ // if not halt instruction and not move instruction and not an interrupt
            // shifting and masking to get the source registers
            int R1 = currentInstruction.rightShift(8).and(getLast4Bits).getSigned();
            int R2 = currentInstruction.rightShift(4).and(getLast4Bits).getSigned();

            // get values in source registers
            op1 = this.registers[R1];
            op2 = this.registers[R2];
        }

    }


    public void execute() {
        if (isALUInstruction){// if all bits are not zero
            result = ALU.doOpt(opBit1, opBit2, opBit3, opBit4, op1, op2);
        }
        else if (move){
            move();
        }
        else if (jump){
            // jump to given address
            int address = currentInstruction.and(getLast12Bits).getSigned();
            PC = new Longword(address);

        }
        else if (compare){
            int r1 = currentInstruction.and(getLast4Bits).getSigned();
            int r2 = currentInstruction.rightShift(4).and(getLast4Bits).getSigned();
            op1 = registers[r1];
            op2 = registers[r2];
            Longword op3 = new Longword();
            op3.copy(op2);
            int c = RippleAdder.subtract(op1, op3).getSigned();

            if(c > 0){
                // if register1 < register 2 ---> unset bit 0 of compareBits
                compareBits[0] = new Bit(false);
            }
            else if (c < 0){
                // if register1 > register 2 ---> set bit 0 of compareBits
                compareBits[0] = new Bit(true);
            }
            if (c == 0){
                // if register1 = register 2 ---> set bit 1 of compareBits
                compareBits[1] = new Bit(true);
            }
            else if (c != 0){
                // if register1 = register 2 ---> unset bit 1 of compareBits
                compareBits[1] = new Bit(false);
            }
        }
        else if (branch){
            // getting condition bit 0

            boolean C0 = currentInstruction.rightShift(11).and(new Longword(1)).getBit(0).getValue();
            // getting condition bit 1
            boolean C1 = currentInstruction.rightShift(10).and(new Longword(1)).getBit(0).getValue();
            // getting signed bit
            boolean S = currentInstruction.rightShift(9).and(new Longword(1)).getBit(0).getValue();
            // getting address (getting last 9 bits)
            long address = currentInstruction.and(new Longword(511)).getUnsigned();
            if (S){
                address = -1*address;
            }

            System.out.println("address "+ address);
            if (C0 && C1){
                //11 greater than or equal to
                if(compareBits[1].getValue() || compareBits[0].getValue()){
                    PC = RippleAdder.add(PC, new Longword((int)address));
                    System.out.println(PC);
                }
            }
            else if (C0 && !C1){
                //10 greater than
                if(compareBits[0].getValue()){
                    PC = RippleAdder.add(PC, new Longword((int)address));
                }

            }
            else if(!C0 && C1){
                //01 equal
                if(compareBits[1].getValue()){
                    PC = RippleAdder.add(PC, new Longword((int)address));
                }
            }
            else if(!C0 && !C1){
                //00 not equal
                if(!compareBits[1].getValue()){
                    PC = RippleAdder.add(PC, new Longword((int)address));
                }
            }

        }
        else if (interrupt) {
            int value = currentInstruction.and(getLast12Bits).getSigned();
            if(value == 0){ //0010 0000 0000 0000
                // print all registers
                for(int i = 0; i < this.registers.length; i++){
                    Longword l = this.registers[i];
                    if (l != null){
                        String s = l.toString();
                        System.out.println("Register " + i + " = " + s);
                    }
                    else{
                        System.out.println("Register" + i + " = " + l);
                    }

                }
            }
            else if(value == 1){ //0010 0000 0000 0001
                // print all registers
                for(int i = 0; i < 1024; i = i + 4){
                    String s = this.mem.read(new Longword(i)).toString().replace(" ", "");
                    System.out.println("mem[" + i + "] = " + s.substring(0,4) +" " + s.substring(4,8) +" "
                            + s.substring(8,12) +" " + s.substring(12,16));
                    System.out.println("mem[" + (i+2) + "] = "+ s.substring(16,20) +" " + s.substring(20,24) +" "
                            + s.substring(24,28) +" " + s.substring(28,32));
                }
            }

        }
        else if(popPush){

            int lastTwoBits = currentInstruction.rightShift(10).and(new Longword(3)).getSigned();
            // push
            if(lastTwoBits == 0){
                push();
            } else if (lastTwoBits == 1) {
                pop();
            }else if (lastTwoBits == 2) {
                call();
            }else if (lastTwoBits == 3) {
                Return();
            }
        }
        else{
            System.out.println("");
        }
    }
    //PC = currentInstruction

    public void store() {
        currentInstruction = Base64;
        if (isALUInstruction){
            // masking to get destination register
            int R3 = currentInstruction.and(getLast4Bits).getSigned();
            // storing result in destination register
            this.registers[R3] = result;
            System.out.println(result.getSigned());
        }
        move = false;
        interrupt = false;
        jump = false;
        branch = false;
        compare = false;
        popPush = false;

    }

    public boolean isHalt(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        // return true if all are zero bits
        return !(bit1.getValue() || bit2.getValue() || bit3.getValue() || bit4.getValue());
    }

    public Longword[] getRegisters()
    {
        return this.registers;

    }

    public Memory getMem()
    {
        return this.mem;
    }

    public void move(){

        // register to which value is to be moved
        int R = currentInstruction.rightShift(8).and(getLast4Bits).getSigned();
        // value to be moved to register R
        Longword value = currentInstruction.and(getLast8Bits);
        this.registers[R] = value;

    }

    public void push(){
        int registerToPushFrom = currentInstruction.and(getLast4Bits).getSigned();
        Longword valueToPush = registers[registerToPushFrom];
        this.mem.write(SP, valueToPush);
        // updating SP
        SP = RippleAdder.subtract(SP, new Longword(4));

    }
    public void pop(){
        int registerToPopTo = currentInstruction.and(getLast4Bits).getSigned();
        Longword valueToPush = registers[registerToPopTo];
        // writing to register
        registers[registerToPopTo] = this.mem.read(SP);
        // updating SP
        SP = RippleAdder.add(SP, new Longword(4));

    }
    public void call() {
        Longword addressToJumpTo = currentInstruction.and(getLast10Bits);
        Longword addressToPush = PC;
        //pushing the address to
        this.mem.write(SP, addressToPush);
        //updating SP
        SP = RippleAdder.subtract(SP, new Longword(4));
        // Jumping to the address - 2, as at start of fetching instructiom 2 will be added to PC
        PC = addressToJumpTo;
    }
    public void Return(){
        // poping the address out
        Longword addressToReadAt = RippleAdder.add(SP, new Longword(4));
        Longword instructionToGoTo = this.mem.read(addressToReadAt);
        SP = RippleAdder.add(SP, new Longword(4));
        // set PC to the previous of our target instruction as 2 will be added in fetch function
        PC = instructionToGoTo;


    }

    public boolean isMove(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        //0001
        return (!bit1.getValue() && !bit2.getValue() && !bit3.getValue() && bit4.getValue());
    }

    public boolean isInterrupt(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        // 0010
        return (!bit1.getValue() && !bit2.getValue() && bit3.getValue() && !bit4.getValue());
    }

    public boolean isJump(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        //0011
        return (!bit1.getValue() && !bit2.getValue() && bit3.getValue() && bit4.getValue());
    }

    public boolean isCompare(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        //0100
        return (!bit1.getValue() && bit2.getValue() && !bit3.getValue() && !bit4.getValue());
    }

    public boolean isBranch(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        //0101
        return (!bit1.getValue() && bit2.getValue() && !bit3.getValue() && bit4.getValue());
    }

    public boolean isPopPush(Bit bit1, Bit bit2, Bit bit3, Bit bit4){
        //0110
        return (!bit1.getValue() && bit2.getValue() && bit3.getValue() && !bit4.getValue());
    }
    public void preload(String[] program){
        for(int i = 0, address = 0; i < program.length; i++, address = address+2){
            String instruction = program[i].replace(" ", "");
            Longword longword = new Longword();
            for(int j = 0; j < 16; j++){
                int instructionBit = Character.getNumericValue(instruction.charAt(15-j));
                if(instructionBit == 0){
                    longword.setBit(j, new Bit(false));
                }
                else{
                    longword.setBit(j, new Bit(true));
                }
            }
            this.mem.write(new Longword(address), longword);
        }
    }
    public void run(){

        while (!bit.getValue()){
            fetch();
            decode();
            execute();
            store();
        }
        if (!bit.getValue())
            System.out.print("Got Halted");
        else
            System.out.println("Program Completed");
    }

    //    For testing uncomment this one
    public static void main(String[] args) {
        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer c = new Computer(m, registers);
        String[] arr = new String[9];
        arr[0] = "0001 0001 0000 0101"; // writing 5 at register 1
        arr[1] = "0001 0010 0000 0111"; // writing 7 at register 2
        arr[2] = "1110 0001 0010 0011"; // adding values of R1 and R2 and storing in R3
        arr[3] = "0010 0000 0000 0000"; // interrupt 0
        arr[4] = "0010 0000 0000 0001"; // interrupt 1
        arr[5] = "0001 0100 0000 1111"; // writing 15 at register 4
        arr[6] = "0001 0111 0000 1000"; // writing 8 at register 7
        arr[7] = "0111 0100 0111 1000"; // multiplying values of R4 and R7 and storing in R8
        arr[8] = "0000 0000 0000 0000"; // halt
        c.preload(arr);
        c.run();

    }

}



