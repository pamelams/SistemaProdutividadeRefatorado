import java.util.ArrayList;

public class Professor extends Collaborator {
    public Professor(String name, String email, String password) {
        super(name, email, password);
    }
    public Professor(String name, String email, String password, ArrayList<Project> history, ArrayList<AcademicProduction> academicProduction) {
        super(name, email, password, history, academicProduction);
    }

    @Override
    public String toString() {
        return super.toString() + "\nVinculo: Professor";
    }
}
