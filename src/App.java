import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) {
        Laboratory lab = new Laboratory();
        Menu.homePage(lab);
    }
}