import org.junit.Test;

import static org.junit.Assert.*;

public class CPUTest
{
    @Test
    public void testMove(){
        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);

        String[] arr = new String[2];
        arr[0] = "0001 0001 0000 0101"; // writing 5 at register 1
        arr[1] = "0001 0010 0000 0111"; // writing 7 at register 2

        computer.preload(arr);
        computer.run();
        assertEquals(computer.getRegisters()[1].getSigned(), 5);
        assertEquals(computer.getRegisters()[2].getSigned(), 7);
    }

    @Test
    public void testInterrupt(){
        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);

        String[] arr = new String[2];

        arr[0] = "0010 0000 0000 0000"; // interrupt 0
        arr[1] = "0010 0000 0000 0001"; // interrupt 1
        computer.preload(arr);
        computer.run();
    }

    /*@Test
    public void testProgram1(){
        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);
        String[] arr = new String[5];

        arr[0] = "0001 0001 0000 0101"; // writing 5 at register 1
        arr[1] = "0001 0010 0000 0111"; // writing 7 at register 2
        arr[2] = "1110 0001 0010 0011"; // adding values of R1 and R2 and storing in R3
        arr[3] = "0010 0000 0000 0000"; // interrupt 0
        arr[4] = "0010 0000 0000 0001"; // interrupt 1

        computer.preload(arr);
        computer.run();
//
        assertEquals(computer.getRegisters()[3].getSigned(), 12);
    }
    //
    @Test
    public void testProgram2(){
        Memory m = new Memory();
        Longword[] registers = new Longword[16];
        Computer computer = new Computer(m, registers);
        String[] arr = new String[6];
        arr[0] = "0001 0100 0000 1111"; // writing 15 at register 4
        arr[1] = "0001 0111 0000 1000"; // writing 8 at register 7
        arr[2] = "0111 0100 0111 1000"; // multiplying values of R4 and R7 and storing in R8
        arr[3] = "0000 0000 0000 0000"; // tested halt
        // now below instructions must not run
        arr[4] = "0010 0000 0000 0000"; // interrupt 0
        arr[5] = "0010 0000 0000 0001"; // interrupt 1

        computer.preload(arr);
        computer.run();

        assertEquals(computer.getRegisters()[8].getSigned(), 120);
    }*/
}



