import java.util.Arrays;
//adding jump, compare, and branch method in here
public class Assembler
{
    public static String[] assemble(String[] instructions)
    {
        int n = instructions.length;
        String[] bitInstructions = new String[n];
        for(int i = 0; i < n; i++){
            String currentInstruction = instructions[i].toLowerCase();
            String[] instructionsParts = currentInstruction.split(" ");
            String command = instructionsParts[0];

            switch (command) {
                case "move" -> {
                    bitInstructions[i] = "0001";
                    if (instructionsParts.length == 3) {
                        String reg = registerToBits(instructionsParts[1]);
                        String val = valueTo8Bits(instructionsParts[2]);
                        bitInstructions[i] = bitInstructions[i].concat(reg).concat(val);
                    } else {
                        bitInstructions[i] = bitInstructions[i].concat("00000000");
                    }
                }
                case "and" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1000");
                case "or" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1001");
                case "xor" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1010");
                case "not" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1011");
                case "left-shift" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1100");
                case "right-shift" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1101");
                case "add" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1110");
                case "sub" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "1111");
                case "multiply" -> bitInstructions[i] = srcDstRegInst(instructionsParts, "0111");
                case "jump" -> {
                    bitInstructions[i] = "0011";
                    if(instructionsParts.length== 2){
                        String posNum = instructionsParts[1];

                        if(isInteger(posNum)){
                            String address = valueTo12Bits(posNum);
                            System.out.println(address);
                            bitInstructions[i] = bitInstructions[i].concat(address);
                        }
                        else{
                            System.out.println(posNum + " is not a valid address");
                        }
                    }
                    else{
                        System.out.println("Syntax for jump comamnd is \"jump [address to jump to]\"");
                    }
                }
                case "compare" -> {
                    bitInstructions[i] = "0100";
                    if(instructionsParts.length== 3){
                        String r1 = registerToBits(instructionsParts[1]);
                        String r2 = registerToBits(instructionsParts[2]);
                        bitInstructions[i] = bitInstructions[i].concat("0000").concat(r1).concat(r2);
                    }
                    else{
                        System.out.println("Syntax for compare comamnd is \"compare 0000 [register 1] [register2]\"");
                    }
                    break;
                }
                case "branchifequal" -> bitInstructions[i] = branchInsToBits(instructionsParts, "01");
                case "branchifnotequal" -> bitInstructions[i] = branchInsToBits(instructionsParts, "00");
                case "branchifgreaterthan" -> bitInstructions[i] =branchInsToBits(instructionsParts, "10");
                case "branchifgreaterthanorequal" -> bitInstructions[i] =branchInsToBits(instructionsParts, "11");
                case "halt" -> bitInstructions[i] = "0000000000000000";

                case "interrupt" -> {
                    bitInstructions[i] = "0010";
                    if (instructionsParts.length == 2) {
                        String interrupt = valueTo12Bits(instructionsParts[1]);
                        bitInstructions[i] = bitInstructions[i].concat(interrupt);
                    }
                }
                case "push" -> {
                    bitInstructions[i] = "0110";
                    if (instructionsParts.length == 2) {
                        String reg = registerToBits(instructionsParts[1]);
                        bitInstructions[i] = bitInstructions[i].concat("00000000").concat(reg);
                    }else{
                        System.out.println("Invalid Command for " + command);
                    }
                }
                case "pop" -> {
                    bitInstructions[i] = "0110";
                    if (instructionsParts.length == 2) {
                        String reg = registerToBits(instructionsParts[1]);
                        bitInstructions[i] = bitInstructions[i].concat("01000000").concat(reg);
                    }else{
                        System.out.println("Invalid Command for " + command);
                    }
                }
                case "call" -> {
                    bitInstructions[i] = "0110";
                    if (instructionsParts.length == 2) {
                        String address = valueTo10Bits(instructionsParts[1]);
                        bitInstructions[i] = bitInstructions[i].concat("10").concat(address);
                    }else{
                        System.out.println("Invalid Command for " + command);
                    }
                }
                case "return" -> {
                    bitInstructions[i] = "0110";
                    if (instructionsParts.length == 1) {
                        bitInstructions[i] = bitInstructions[i].concat("110000000000");
                    } else{
                        System.out.println("Invalid Command for " + command);
                    }
                }
                default -> System.out.println("Invalid Command:" + command);
            }
        }
        return bitInstructions;
    }

    public static String registerToBits(String register){

        if(register.charAt(0) == 'r'){
            int r = Integer.parseInt(register.substring(1));
            if(r>=0 && r<=15){
                Longword l = new Longword(r);
                String s = l.toString().replace(" ", "");
                return s.substring(s.length()-4);
            }
            return "0000";
        }
        return "";
    }

    public static String valueTo8Bits(String integer){
        int r = Integer.parseInt(integer);
        Longword l = new Longword(r);
        String s = l.toString().replace(" ", "");
        return s.substring(s.length()-8);

    }

    public static String valueTo9Bits(String integer){
        int r = Integer.parseInt(integer);
        Longword l = new Longword(r);
        String s = l.toString().replace(" ", "");
        return s.substring(s.length()-9);

    }
    public static String valueTo10Bits(String integer){
        int r = Integer.parseInt(integer);
        Longword l = new Longword(r);
        String s = l.toString().replace(" ", "");
        return s.substring(s.length()-10);

    }

    public static String valueTo12Bits(String integer){
        int r = Integer.parseInt(integer);
        Longword l = new Longword(r);
        String s = l.toString().replace(" ", "");
        return s.substring(s.length()-12);

    }



    public static boolean isInteger(String str)
    {
        int i, len = str.length();
        if (len == 0)
        {
            return false;
        }
        for (i = 0 ; i < len ; i++)
        {
            if (str.charAt(i) == '.')
            {
                System.out.println("Float numbers are not allowed");
                return false;
            }
            if (str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != '2'
                    && str.charAt(i) != '3' && str.charAt(i) != '4' && str.charAt(i) != '5'
                    && str.charAt(i) != '6' && str.charAt(i) != '7' && str.charAt(i) != '8'
                    && str.charAt(i) != '9' || (str.charAt(i) == '-' && i > 0))
            {
                return false;
            }
        }
        return true;
    }

    public static String branchInsToBits(String[] instructionParts, String conditionBits) {
        String bitInstruction = "0101".concat(conditionBits);
        if (instructionParts.length == 2) {
            String address = instructionParts[1];
            String s = "";
            if (address.charAt(0) == '-') {
                bitInstruction = bitInstruction.concat("1");
                s = valueTo9Bits(address.substring(1));
            } else {
                bitInstruction = bitInstruction.concat("0");
                s = valueTo9Bits(address.substring(0));
            }
            bitInstruction = bitInstruction.concat(s);
            return bitInstruction;

        } else {
            System.out.println("Syntax for branch comamnd is \"branch [decrease of increase in address]\"");
            return null;
        }

    }
    public static String srcDstRegInst(String[] instructionsParts, String bitsCommand){
        String bitInstruction = bitsCommand;
        if(instructionsParts.length == 4){
            String reg1 = registerToBits(instructionsParts[1]);
            String reg2 = registerToBits(instructionsParts[2]);
            String reg3 = registerToBits(instructionsParts[3]);
            bitInstruction = bitInstruction.concat(reg1).concat(reg2).concat(reg3);
        }
        else{
            bitInstruction = bitInstruction.concat("000000000000");
        }
        return bitInstruction;
    }

    //    For testing uncomment this one
    public static void main(String[] args) {
        String[] test = new String[]{"Pop R1", "Push R2", "Call 6", "Return"};
        System.out.println(Arrays.toString(assemble(test)));
    }

}

