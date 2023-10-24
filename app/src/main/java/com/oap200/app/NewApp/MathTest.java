public class MathTest {
    public static void main(String[] args) {
        // Square root of an int
        int number = 25;
        double squareRoot = Math.sqrt(number);
        System.out.println("Square root of " + number + " = " + squareRoot);

        // Floor, ceiling, and round of a double
        double value = 3.7;
        double floor = Math.floor(value);
        double ceiling = Math.ceil(value);
        double round = Math.round(value);
        System.out.println("Floor of " + value + " = " + floor);
        System.out.println("Ceiling of " + value + " = " + ceiling);
        System.out.println("Round of " + value + " = " + round);

        // Larger and smaller of an int and a double
        int integer = 5;
        double floatingPoint = 3.2;
        int larger = (int) Math.max(integer, floatingPoint);
        int smaller = (int) Math.min(integer, floatingPoint);
        System.out.println("Larger of " + integer + " and " + floatingPoint + " = " + larger);
        System.out.println("Smaller of " + integer + " and " + floatingPoint + " = " + smaller);
    }
}