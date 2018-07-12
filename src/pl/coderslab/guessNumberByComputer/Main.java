package pl.coderslab.guessNumberByComputer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<String> array = new ArrayList<>();

    /**
     * fill array with permitted answers
     */
    private static void fillData() {
        array.add("Correct");
        array.add("Too low");
        array.add("Too high");
    }

    public static void main(String[] args) {
        answerValue();
    }

    /**
     *
     * @return user answer
     */
    private static String userAnswer() {
        Scanner scan = new Scanner(System.in);
        String input = "";

        // check if user enter proper answer
        while(true) {
            input = scan.nextLine();

            if(array.contains(input)) {
                break;
            }
        }
        System.out.println(input);
        return input;
    }

    /**
     * bound values must be changed with every round
     * @param lowerBound
     * @param upperBound
     * @return computer guess of number
     */
    private static int computerInput(int lowerBound, int upperBound) {
        return (upperBound - lowerBound) / 2 + lowerBound;
    }

    /**
     * display value to guess by computer
     */
    private static void setNumber() {

        Scanner scan = new Scanner(System.in);
        Integer number = null;

        System.out.println("Please set the number: ");
        while(number == null) {
            try {
                number = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid number!");
            }
        }

        System.out.println("Set number is: " + number);
    }

    private static void answerValue() {
        fillData();

        int counter = 1;
        setNumber();

        // begining values of computer's bound
        int lowerBoundComp = 1;
        int upperBoundComp = 1000;

        // first computer number
        int compNumber = computerInput(lowerBoundComp, upperBoundComp);

        String answerToCheck = "";

        System.out.println("My number is correct? :" + compNumber);
        while(true) {
            answerToCheck = userAnswer();
            // check if input computer's is correct or not
            if(answerToCheck.equals(array.get(0))) {
                System.out.println("Congratulation, computer won! It took round(s): " + counter);
                break;
            } else if(answerToCheck.equals(array.get(1))) { // computer must input number from new range
                lowerBoundComp = compNumber;
            } else if(answerToCheck.equals(array.get(2))) {
                upperBoundComp = compNumber;
            }
            compNumber = computerInput(lowerBoundComp, upperBoundComp);
            System.out.println("Now my number is correct? " + compNumber);
            counter++;
        }
    }
}
