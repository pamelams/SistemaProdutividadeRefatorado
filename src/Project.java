import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Project {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String fundingAgency;
    private Double fundingValue;
    private String objective;
    private String description;
    private ArrayList<Collaborator> participants;
    private ArrayList<Publication> publications;
    private int status; // 0 = em elaboracao; 1 = em andamento; 2 = concluido.

    public Project(){
        this.title = null;
        this.startDate = null;
        this.endDate = null;
        this.fundingAgency = null;
        this.fundingValue = null;
        this.objective = null;
        this.description = null;
        this.participants = null;
        this.publications = null;
        this.status = 0;
    }
    public Project(String title, LocalDate startDate, LocalDate endDate, String fundingAgency, Double fundingValue, String objective, String description, ArrayList<Collaborator> participants, ArrayList<Publication> publications, int status){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundingAgency = fundingAgency;
        this.fundingValue = fundingValue;
        this.objective = objective;
        this.description = description;
        this.participants = participants;
        this.publications = publications;
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public String printStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = this.startDate.format(formatter);
        return formattedDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public String printEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = this.endDate.format(formatter);
        return formattedDate;
    }
    public void setFundingAgency(String fundingAgency) {
        this.fundingAgency = fundingAgency;
    }
    public String getFundingAgency() {
        return fundingAgency;
    }
    public void setFundingValue(Double fundingValue) {
        this.fundingValue = fundingValue;
    }
    public Double getFundingValue() {
        return fundingValue;
    }
    public void setObjective(String objective) {
        this.objective = objective;
    }
    public String getObjective() {
        return objective;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<Collaborator> getParticipants() {
        return participants;
    }
    public void addParticipant(Collaborator newParticipant) {
        if(this.participants == null){
            this.participants = new ArrayList<Collaborator>();
        }
        if(newParticipant.getClass().getSimpleName() == "Student") {
            Student st = (Student) newParticipant;
            if(st.getStatus() == 2) {
                System.out.println("\nNao foi possivel adicionar " + newParticipant.getName() + " (Aluno de graduacao ja participa de dois projetos nao concluidos).");
                return;
            }
        }
        this.participants.add(newParticipant);
        newParticipant.addHistory(this);
        System.out.println("\n" + newParticipant.getName() + " foi adicionado!");
    }
    public void removeParticipant(String email) {
        for(int i = 0; i < this.participants.size(); i++) {
            if(email == this.participants.get(i).getEmail()){
                String name = this.participants.get(i).getName();
                this.participants.get(i).removeHistory(this);
                this.participants.remove(i);
                System.out.println("\n" + name + " foi removido!");
                return;
            }
        }
        System.out.println("Participante nao foi encontrado.");
    }
    public ArrayList<Publication> getPublications() {
        return publications;
    }
    /* Adiciona publicacoes em ordem decrescente de data. */
    public void addPublication(Publication newPublication){
        if(this.publications == null){
            this.publications = new ArrayList<Publication>();
        } 
        newPublication.setAssociatedProject(this);
        for(int i = 0; i < this.publications.size(); i++){
            if(newPublication.getYearOfPublication() > this.publications.get(i).getYearOfPublication()) {
                this.publications.add(i, newPublication);
                return;
            } 
        }
        this.publications.add(newPublication);
    }
    public int getStatus() {
        return this.status;
    }
    public void changeStatus() {
        if(this.status == 0) {
            if(this.checkBasicInformation() == true) {
                this.status = 1;
                System.out.println("Status alterado com sucesso! O projeto esta em andamento.");
            }
            else {
                System.out.println("Nao foi possivel alterar o status, informacoes basicas nao estao preenchidas.");
            }
        }
        else if(this.status == 1) {
            if(this.publications != null) {
                this.status = 2;
                System.out.println("Status alterado com sucesso! O projeto foi concluido.");
            }
            else {
                System.out.println("Nao foi possivel alterar o status. Nao ha publicacoes associadas ao projeto.");
            }
        }
    }
    /* checar se as informacoes basicas foram preenchidas */
    public boolean checkBasicInformation() {
        boolean isProfessor = false;
        if(this.participants != null) {
            for(int i = 0; i < this.participants.size(); i++) {
                if(this.participants.get(i).getClass().getSimpleName() == "Professor"){
                    isProfessor = true;
                    break;
                }
            }
        }
        if(isProfessor && this.title != null && this.startDate != null && this.endDate != null && this.fundingAgency != null 
        && this.fundingValue != null && this.objective != null && this.description != null) {
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public String toString() {
        String toPrint;
        toPrint = "Titulo: " + this.getTitle();
        if(this.getStatus() == 0) {
            toPrint = toPrint + "\nStatus: Em elaboracao";
        }
        else if(this.getStatus() == 1) {
            toPrint = toPrint + "\nStatus: Em andamento";
        }
        else if(this.getStatus() == 2) {
            toPrint = toPrint + "\nStatus: Concluido";
        }
        toPrint = toPrint + "\nData de inicio: " + this.printStartDate();
        toPrint = toPrint + "\nData de termino: " + this.printEndDate();
        toPrint = toPrint + "\nAgencia financiadora: ";
        if(this.getFundingAgency() != null) {
            toPrint = toPrint + this.getFundingAgency();
        }
        toPrint = toPrint + "\nValor financiado: ";
        if(this.getFundingValue() != null) {
            toPrint = toPrint + this.getFundingValue();
        }
        toPrint = toPrint + "\nObjetivo: ";
        if(this.getObjective() != null) {
            toPrint = toPrint + this.getObjective();
        }
        toPrint = toPrint + "\nDescrição: ";
        if(this.getDescription() != null) {
            toPrint = toPrint + this.getDescription();
        }
        toPrint = toPrint + "\nParticipantes: ";
        if(this.participants != null) {
            for(int i = 0; i < this.getParticipants().size(); i++){
                if(i == 0) {
                    toPrint = toPrint + this.getParticipants().get(i).getName();
                }
                else {
                    toPrint = toPrint + "\n               " + this.getParticipants().get(i).getName();
                }
            }
        }
        toPrint = toPrint + "\nPublicacoes: ";
        if(this.publications != null) {
            for(int i = 0; i < this.getPublications().size(); i++){
                if(i == 0) {
                    toPrint = toPrint + this.getPublications().get(i).getTitle();
                }
                else {
                    toPrint = toPrint + "\n             " + this.getPublications().get(i).getTitle() + " (" + this.getPublications().get(i).getYearOfPublication() + ")";
                }
            }
        }
        return toPrint;
    }
}