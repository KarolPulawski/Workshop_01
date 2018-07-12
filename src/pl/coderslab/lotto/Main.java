package pl.coderslab.lotto;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        setNumber();
    }

    /**
     * display sorted entered numbers
     * @return list with 6 numbers
     */
    private static List<Integer> setNumber() {

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

        for(int num : numbers) {
            System.out.print(num + " ");
        }

        return numbers;
    }
}
