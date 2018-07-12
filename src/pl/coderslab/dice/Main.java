package pl.coderslab.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("The result is: " + dice(parseInfo()));
    }

    /**
     * store input value
     */
    private static String input = null;

    /**
     *
     * @return value of index 'D' if exists
     */
    private static int scanAndFindIndexD() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter info about dice: ");
        input = scan.nextLine();
        return input.indexOf('D');
    }

    /**
     * 
     * @param indexD
     * @return amount of throws
     */
    private static int checkValueBeforeD(int indexD) {

        Integer number = null;
        String beforeD = input.substring(0,indexD);

        if(beforeD.length() == 0) {
            number = 1;
        } else {
            try {
                number = Integer.parseInt(beforeD);

            } catch (NumberFormatException e) {
                System.out.println("Amount before letter D is not a value! Please enter correct value:");
                Scanner scan = new Scanner(System.in);
                number = scan.nextInt();
                checkValueBeforeD(number);
            }
        }
        return number;
    }

    /**
     *
     * @param indexD
     * @return array with two values. 1st - kind of dice. 2nd - additional value.
     */
    private static int[] checkValueAfterD(int indexD) {

        String afterD = input.substring(indexD + 1);

        int[] results = new int[2];
        int indexPlus = afterD.indexOf('+');
        int indexMinus = afterD.indexOf('-');

        if(indexPlus == indexMinus) {
            results[0] = Integer.parseInt(afterD);
            results[1] = 0; // if both index are equals -1, there is no sign '+' or '-'
        } else if(indexPlus > indexMinus) {
            results[0] = Integer.parseInt(afterD.substring(0, indexPlus));
            results[1] = Integer.parseInt(afterD.substring(indexPlus + 1));
        } else if(indexMinus > indexPlus) {
            results[0] = Integer.parseInt(afterD.substring(0, indexMinus));
            results[1] -= Integer.parseInt(afterD.substring(indexMinus + 1));
        }
        return results;
    }

    /**
     *
     * @return join all three values in one array
     * @throws NumberFormatException
     */
    private static int[] parseInfo() throws NumberFormatException {

        int[] result = new int[3];
        List<Integer> listDice = new ArrayList<>();
        listDice.add(3);
        listDice.add(4);
        listDice.add(6);
        listDice.add(8);
        listDice.add(10);
        listDice.add(12);
        listDice.add(20);
        listDice.add(100);

        // input text and find index D
        Integer indexD = null;
        while(true) {
            indexD = scanAndFindIndexD();
            if (indexD > -1) break;
        }

        // amount of throws
        result[0] = checkValueBeforeD(indexD);

        // kind of dice
        result[1] = checkValueAfterD(indexD)[0];
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter valid type of dice!");
            try{
                result[1] = scan.nextInt();
                if(listDice.contains(result[1])) break;
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // additional value
        result[2] = checkValueAfterD(indexD)[1];

        return result;
    }

    /**
     *
     * @param parameters
     * @return final result
     */
    private static int dice(int[] parameters) {

        for(int i : parameters) {
            System.out.println(i);
        }
        return parameters[0] * parameters[1] + parameters[2];
    }
}
