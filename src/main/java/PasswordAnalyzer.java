import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isStrong = false;
        boolean continueProgram = true;

        while (!isStrong && continueProgram) {
            isStrong = handlePasswordInput(scanner);
            if (!isStrong) {
            }
        }
        scanner.close();
    }


    private static boolean handlePasswordInput(Scanner scanner) {
        String password = getUserInput("Enter your password: ", scanner);
        if (isStrongPassword(password)) {
            System.out.println("Congratulations! Your password is strong and secure.");
            System.exit(0);
            return true;
        } else {
            handleWeakPassword(password, scanner);
            return false;
        }
    }

    private static void handleWeakPassword(String password, Scanner scanner) {
        System.out.println("Your password is not strong enough, ");
        String suggestedPassword = suggestStrongPassword(password);
        System.out.println("Would you like to use this suggested stronger password instead? " + suggestedPassword);

        String response = getUserInput("Use suggested password? (yes/no): ", scanner);
        if ("yes".equalsIgnoreCase(response)) {
            System.exit(0);
            // User accepted the suggested password
        } else if ("no".equalsIgnoreCase(response)) {
            handlePasswordInput(scanner);
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }
    }


    private static String getUserInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static boolean isStrongPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private static String suggestStrongPassword(String weakPassword) {
        StringBuilder suggestedPassword = new StringBuilder(weakPassword);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&+=";

        // Check for missing criteria and add corresponding characters
        if (!weakPassword.matches(".*[A-Z].*")) suggestedPassword.append("A");
        if (!weakPassword.matches(".*[a-z].*")) suggestedPassword.append("a");
        if (!weakPassword.matches(".*[0-9].*")) suggestedPassword.append("1");
        if (!weakPassword.matches(".*[@#$%^&+=].*")) suggestedPassword.append("@");

        while (suggestedPassword.length() < 12) { // Add random characters until we reach the desired length
            int index = (int) (Math.random() * characters.length());
            suggestedPassword.append(characters.charAt(index));
        }

        return suggestedPassword.toString();
    }
}
