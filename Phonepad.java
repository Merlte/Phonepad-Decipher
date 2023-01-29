import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Phonepad {
    private TreeMap<String, Character> dict = new TreeMap<>();
    private List<String> invalids = new ArrayList<>();

    public Phonepad() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int index = 0;

        for (int i = 2; i <= 9; i++) {
            for (int j = 1; j <= 3; j++) {
                dict.put(Integer.toString(i).repeat(j), alphabet.charAt(index));
                index++;

                if ((i == 7 && j == 3) || i == 9 && j == 3) {
                    dict.put(Integer.toString(i).repeat(j + 1), alphabet.charAt(index));
                    index++;
                }
            }
        }

        assert (index == 25);
    }

    public Character getChar(String key) {
        if (!getDict().containsKey(key)) {
            invalids.add(key);
            return Character.MIN_VALUE; // '\u0000'
        }

        return getDict().get(key);
    }

    public TreeMap<String, Character> getDict() {
        return dict;
    }

    public List<String> getInvalids() {
        return invalids;
    }

    public void printDict() {
        System.out.println("---------");
        for (Map.Entry<String, Character> entry : dict.entrySet()) {
            System.out.println(entry.getValue() + " : " + entry.getKey());
        }
        System.out.println("---------");
    }

    public void printInvalids() {
        System.out.print("\u001B[31mInvalid Inputs: " + getInvalids() + "\u001B[0m");
        System.out.print("\n");
    }

    public void clearInvalids() {
        invalids = new ArrayList<>();
    }

    public static void main(String args[]) {
        Phonepad phonepad = new Phonepad();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter phonepad number sequence to decode (separated by spaces)");
        System.out.println("Alternatively, type:");
        System.out.println("\u001B[1mexit\u001B[0m to quit the program");
        System.out.println("\u001B[1mprint\u001B[0m to display the dictionary");
        System.out.println("--------------------------------------------------------------");

        String msg = sc.nextLine();

        while (!msg.equals("exit")) {
            if (msg.trim().equals("print")) {
                phonepad.printDict();
            } else {
                for (String key : msg.split(" ")) {
                    System.out.print("\u001B[38;5;78m" + phonepad.getChar(key) + "\u001B[0m");
                }

                System.out.print("\n");

                if (!phonepad.getInvalids().isEmpty()) {
                    System.out.println("-");
                    phonepad.printInvalids();
                    phonepad.clearInvalids();
                }
            }

            msg = sc.nextLine();
        }

        sc.close();
    }
}