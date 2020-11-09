import java.util.Scanner; // Import the Scanner class

public class MIPS extends convert_methods {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in); // Create a Scanner object
        String[] command_form_R = new String[6];
        String[] command_form_I = new String[10];
        String[] command_form_J = new String[10];

        System.out.println("Enter your command formt"); // R/I/J
        String value = myObj.nextLine(); // Read user input format
        if (value.charAt(0) == 'R' || value.charAt(0) == 'r') {
            command_form_R[0] = "R";

            System.out.println("Enter your format " + command_form_R[0] + " command"); // R/I/J
            value = myObj.nextLine(); // Read user input command
            System.out.println("ECO: " + value);

            String S1 = "", S2 = "", S3 = "", S4 = "";
            int mode1 = 1, mode2 = 0, mode3 = 0, mode4 = 0, j = 0;
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) != ' ') {
                    if (mode1 == 1) // command name
                        S1 += value.charAt(i);
                    if (mode2 == 1) // ragister RS
                        S2 += value.charAt(i);
                    if (mode3 == 1) // ragister RT
                        S3 += value.charAt(i);
                    if (mode4 == 1) // ragister RD
                        S4 += value.charAt(i);
                }
                if (value.charAt(i) == ' ') {
                    j++;
                    if (j == 1) {
                        mode1 = 0; // stop get the command
                        mode2 = 1; // start get ragister RS
                    }
                    if (j == 2) {
                        mode2 = 0; // stop take ragister RS
                        mode3 = 1; // start get ragister RT
                    }
                    if (j == 3) {
                        mode3 = 0; // stop take ragister RT
                        mode4 = 1; // start get ragister RD
                    }
                    continue;
                }
            }
            String temp_To_Check = S1;
            command_form_R[1] = S1; // save command name
            command_form_R[2] = S2; // save ragister RS
            command_form_R[3] = S3; // save ragister RT
            command_form_R[4] = S4; // save ragister RD
            if (case_of_zero_registers(temp_To_Check)) // for exmaple syscall
            {
                command_form_R[2] = command_form_R[3] = command_form_R[4] = command_form_R[5] = "XXXXX";
                command_form_R[5] = func(command_form_R[1], "R");
                command_form_R[0] = "000000"; // opcode in R format is 6 bits of zero
                j = -1; // falge for this case
            }
            if (case_of_one_registers(temp_To_Check)) // for exmaple mflo/mfhi
            {
                temp_To_Check = command_form_R[2]; // make a swop
                command_form_R[2] = command_form_R[3];
                command_form_R[3] = temp_To_Check;
            }
            if (case_of_tow_registers(temp_To_Check)) // for empale mult/div
            {
                temp_To_Check = command_form_R[4]; // make a swop
                command_form_R[4] = command_form_R[3];
                command_form_R[3] = temp_To_Check;
            }

            System.out.println("System inishlize command");
            for (int i = 0; i <= 3; i++) {
                System.out.println(".");
            }
            if (j != -1) {
                command_form_R[5] = func(command_form_R[1], "R");
                command_form_R[0] = "000000"; // opcode in R format is 6 bits of zero
                command_form_R[1] = rgisterBits(command_form_R[2]);
                command_form_R[2] = rgisterBits(command_form_R[4]);
                command_form_R[3] = rgisterBits(command_form_R[3]);
                command_form_R[4] = "00000";
            }
            String conver = "";
            System.out.println(" OPCODE\t   RS\t   RT\t   RD\t  SHAMT\t  FUNCT");
            for (int i = 0; i < command_form_R.length; i++) {
                for (int k = 0; k < command_form_R[i].length(); k++) {
                    conver += command_form_R[i].charAt(k);
                }
                System.out.print("[" + command_form_R[i] + "] ");
            }
            if (j == -1) // case of syscall
            {
                conver = "0x0XXXXXXC";
            }
            System.out.println("\n");
            System.out.println("\nThe command in a binary set --> " + conver);
            conver = from_Bin_TO_Hex(conver);
            System.out.println("\nThe command in a hex set --> 0x" + conver + "\n");
        }
    }
}