package pl.coderslab.guessNumberByComp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //guessNumber();

////        // test correct of bound range of generate number
//        int num = -1;
//        while(num != 0) {
//            num = generateNumber(1,100);
//        }
//        System.out.println(num);
    }

    /**
     *
     * @param lowerBound
     * @param upperBound
     * @return generated number from bound
     */
    private static int generateNumber(int lowerBound, int upperBound) {

        return (int) (Math.random() * (upperBound - lowerBound + 1) + lowerBound);
    }

    /**
     *
     * @return input number from console
     */
    private static int inputNumber() {

        Scanner scan = new Scanner(System.in);

        Integer num = null;

        System.out.println("Please enter the number: ");
        while(num == null) {
            try {
                num = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter the valid number");
            }
        }

        return num;
    }

//    /**
//     *
//     * @param inputNumber
//     * @param generatedNumber
//     * @return true if two compared numbers are equal
//     */
//    private static boolean compareValue(int inputNumber, int generatedNumber) {
//
//        if (inputNumber == generatedNumber) return true;
//        else return false;
//    }

    /**
     * display mileage and result of the game
     */
    private static void guessNumber() {

        int counter = 1;

        // generate random number
        int generatedNumber;
        int lowerBound = 1;
        int upperBound = 100;
        generatedNumber = generateNumber(lowerBound, upperBound);

        //compare number from keyboard
        System.out.println("Let's play! Enter your number: ");
        while(true) {
            int numToCheck = inputNumber();
            if(numToCheck == generatedNumber) {
                System.out.println("Congratulations! You guessed number in row: " + counter);
                break;
            } else if(numToCheck > generatedNumber) {
                System.out.println("Try again! Your number is too high!");
            } else if(numToCheck < generatedNumber) {
                System.out.println("Try again! Your number is too low!");
            }
            counter++;
        }
    }
}
