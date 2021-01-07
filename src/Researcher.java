import java.util.ArrayList;

public class Researcher extends Collaborator {
    public Researcher(String name, String email, String password) {
        super(name, email, password);
    }
    public Researcher(String name, String email, String password, ArrayList<Project> history, ArrayList<AcademicProduction> academicProduction) {
        super(name, email, password, history, academicProduction);
    }
    @Override
    public String toString() {
        return super.toString() + "\nVinculo: Pesquisador";
    }
}