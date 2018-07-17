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
        array.add("yes");
        array.add("no");
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
     * user type answer on computer's questions
     */
    private static void answerValue() {
        fillData();

        int counter = 1;
        System.out.println("Please think and remember number.");

        // begining values of computer's bound
        int lowerBoundComp = 1;
        int upperBoundComp = 1000;



        while(true) {
            //first guess
            int compNumber = computerInput(lowerBoundComp, upperBoundComp);
            System.out.println("Now my choice is: " + compNumber);
            System.out.println("Is it correct?");
            String answerToCheck = userAnswer();

            // check if input computer's is correct
            if(answerToCheck.equals(array.get(0))) {
                System.out.println("Congratulation, computer won! It took round(s): " + counter);
                break;
            } else if(answerToCheck.equals("no")) {

                System.out.println("Is it too high?");
                answerToCheck = userAnswer();
                if (answerToCheck.equals("yes")) {
                    upperBoundComp = compNumber;
                    counter++;
                    continue;
                } else if(answerToCheck.equals("no")) {

                    System.out.println("Is it too low?");
                    answerToCheck = userAnswer();
                    if (answerToCheck.equals("yes")) {
                        lowerBoundComp = compNumber;
                        counter++;
                        continue;
                    } else if(answerToCheck.equals("no")) {
                        System.out.println("Do not cheat!!!");
                    }
                }
            }
        }
    }
}
