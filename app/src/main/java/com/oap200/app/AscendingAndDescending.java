import java.util.Scanner;

public class AscendingAndDescending {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter three integers
        System.out.print("Enter first integer: ");
        int num1 = scanner.nextInt();
        System.out.print("Enter second integer: ");
        int num2 = scanner.nextInt();
        System.out.print("Enter third integer: ");
        int num3 = scanner.nextInt();

        // Display integers in ascending order
        // Inside the if sentences its organized so the number will be seen in ascending order
        System.out.print("Ascending order: ");
        // first it find out if number one is the smallest number
        if (num1 <= num2 && num1 <= num3) {
            System.out.print(num1 + " ");
           // Here it determines who is smallest of num2 and num3 and therefore will come next
           // All this else if codes is a good example of decision making in java
         
            if (num2 <= num3) {
                System.out.print(num2 + " " + num3);
            } else {
                System.out.print(num3 + " " + num2);
            }
        } else if (num2 <= num1 && num2 <= num3) {
            System.out.print(num2 + " ");
            if (num1 <= num3) {
                System.out.print(num1 + " " + num3);
            } else {
                System.out.print(num3 + " " + num1);
            }
        } else {
            System.out.print(num3 + " ");
            if (num1 <= num2) {
                System.out.print(num1 + " " + num2);
            } else {
                System.out.print(num2 + " " + num1);
            }
        }

        // Display integers in descending order
        System.out.print("\nDescending order: ");
        if (num1 >= num2 && num1 >= num3) {
            System.out.print(num1 + " ");
            if (num2 >= num3) {
                System.out.print(num2 + " " + num3);
            } else {
                System.out.print(num3 + " " + num2);
            }
        } else if (num2 >= num1 && num2 >= num3) {
            System.out.print(num2 + " ");
            if (num1 >= num3) {
                System.out.print(num1 + " " + num3);
            } else {
                System.out.print(num3 + " " + num1);
            }
        } else {
            System.out.print(num3 + " ");
            if (num1 >= num2) {
                System.out.print(num1 + " " + num2);
            } else {
                System.out.print(num2 + " " + num1);
            }
        }

        scanner.close();
    }

}