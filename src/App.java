import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static ArrayList<Collaborator> collaborators = new ArrayList<Collaborator>();
    static ArrayList<Project> projects = new ArrayList<Project>();
    static ArrayList<AcademicProduction> productions = new ArrayList<AcademicProduction>();
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        Menu.homePage(collaborators, projects, productions);
    }
}