package pl.coderslab.lotto;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        game();
    }

    /**
     * display sorted entered numbers
     * @return list with 6 numbers
     */
    private static List<Integer> choiceNumbers() {

        Scanner scan = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();

        System.out.println("Start enter your numbers: ");
        while(numbers.size() < 6) {
            try {
                int num = Integer.parseInt(scan.nextLine());
                if(numbers.contains(num)) {
                    System.out.println("This number is already existed");
                } else if(num < 50 && num > 0){
                    numbers.add(num);
                } else {
                    System.out.println("Enter valid number");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter valid number");
            }
        }

        Collections.sort(numbers);

        System.out.println("Your numbers: ");
        for(int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        return numbers;
    }

    /**
     *
     * @return list of rolled numbers
     */
    private static List<Integer> rollNumbers() {

        List<Integer> numbers = new ArrayList<>();
        List<Integer> rolled = new ArrayList<>();
        for(int i = 1; i < 50; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        for(int i = 0; i < 6; i++) {
            rolled.add(i, numbers.get(i));
        }

        System.out.println("Rolled numbers");
        for(int num : rolled) {
            System.out.print(num + " ");
        }
        System.out.println();

        return rolled;
    }

    /**
     *
     * @param playersNumbers
     * @param rolledNumbers
     * @return number of caught numbers
     */
    private static int checkWin(List<Integer> playersNumbers, List<Integer> rolledNumbers) {

        int counter = 0;
        for(int num : playersNumbers) {
            if(rolledNumbers.contains(num)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * game tracking
     */
    private static void game() {

        int winningAmount = checkWin(choiceNumbers(), rollNumbers());
        if (winningAmount > 2)
            System.out.println("Congratulations! You have caught " + winningAmount + " numbers.");
        else if (winningAmount > 0) System.out.println("Sorry you have lost, but you have caught: "
                + winningAmount + " numbers.");
        else System.out.println("This time you have not caught any number...");

//        // test
//        List<Integer> array = new ArrayList<>();
//        array.add(5);
//        array.add(11);
//        array.add(23);
//        array.add(24);
//        array.add(29);
//        array.add(43);
//        winningAmount = checkWin(array, rollNumbers());
//
//        while (true) {
//
//
//            if (winningAmount > 5) {
//                System.out.println("WON");
//                System.out.println(array.toString());
//                break;
//            }
//
//            if (winningAmount > 2)
//                System.out.println("Congratulations! You have caught " + winningAmount + " numbers.");
//            else if (winningAmount > 0) System.out.println("Sorry you have lost, but you have caught: "
//                    + winningAmount + " numbers.");
//            else System.out.println("This time you have not caught any number...");
//        }
    }
}
