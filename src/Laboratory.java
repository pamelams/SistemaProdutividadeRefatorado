import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Laboratory {
    private ArrayList<Collaborator> collaborators = new ArrayList<Collaborator>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<AcademicProduction> productions = new ArrayList<AcademicProduction>();
    private Scanner read = new Scanner(System.in);

    // colocar alguns metodos de menu aqui
    public void admLogin() {
        String user, password;
        System.out.println("\n>Digite o nome de usuario: ");
        user = read.nextLine();
        System.out.println("\n>Digite a senha: ");
        password = read.nextLine();
        if(user.equals("admin") && password.equals("1234")) {
            Menu.adminMenu(this);
        }
        else {
            System.out.println("\nUsuario ou senha incorretos!");
        }
    }
    public void login() {
        String email, password;
        Collaborator person = null;
        System.out.println("\n>Digite seu email: ");
        email = read.nextLine();
        System.out.println("\n>Digite sua senha: ");
        password = read.nextLine();
        person = checkEmail(email);
        if(person == null) {
            System.out.println("\nEmail nao cadastrado!");
        }
        else if(person.getPassword().equals(password)){
            Menu.menuCollaborator(this, person);
        }
        else {
            System.out.println("\nEmail ou senha incorretos!");
        }
    }
    public Collaborator checkEmail(String email) {
        for(int i = 0; i < collaborators.size(); i++){  // verifica se o email ja foi cadastrado
            if(email.equals(collaborators.get(i).getEmail())){
                return collaborators.get(i);
            }
        }
        return null;
    }
    public void printMyInformation(Collaborator me) {
        System.out.println("\n");
        System.out.println("#########--MINHAS INFORMACOES--#########");
        System.out.println(me);
    }
    public void addNewCollaborator() {
        CompareName cn = new CompareName();
        String name, email, password, confirm;
        int selec;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR NOVO COLABORADOR--#########");
        System.out.println("\n>Digite o nome do novo colaborador: ");
        name = read.nextLine();
        System.out.println("\n>Digite o email do novo colaborador: ");
        email = read.nextLine();
        if(checkEmail(email) != null) {
            System.out.println("\nEmail ja cadastrado!");
            return;
        }
        do{
            System.out.println("\n>Digite a senha do novo colaborador: ");
            password = read.nextLine();
            System.out.println("\n>Confirme a senha: ");
            confirm = read.nextLine();
            if(!(password.equals(confirm))) {
                System.out.println("\nSenha incorreta!");
            }
        }while(!(password.equals(confirm)));    // confirmacao de senha
        System.out.println(">Selecione o tipo de vinculo:");
        System.out.println("(1) Professor");
        System.out.println("(2) Pesquisador");
        System.out.println("(3) Aluno");
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 0 || selec > 3);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            Professor newCollaborator = new Professor(name, email, password);
            collaborators.add(newCollaborator);
            System.out.println("\n");
            System.out.println(newCollaborator);
        }
        else if(selec == 2) {
            Researcher newCollaborator = new Researcher(name, email, password);
            collaborators.add(newCollaborator);
            System.out.println("\n");
            System.out.println(newCollaborator);
        }
        else if(selec == 3) {
            String type = "Aluno";
            System.out.println("\n>Selecione o tipo de aluno:");
            System.out.println("(1) Aluno de graduacao");
            System.out.println("(2) Aluno de mestrado");
            System.out.println("(3) Aluno de doutorado");
            do {
                selec = read.nextInt();
                read.nextLine();
            } while(selec < 1 || selec > 3);
            if(selec == 1) {
                type = "Aluno de graduacao";
            }
            else if(selec == 2) {
                type = "Aluno de mestrado";
            }
            else if(selec == 3) {
                type = "Aluno de doutorado";
            }
            Student newCollaborator = new Student(name, email, password, type);
            collaborators.add(newCollaborator);
            System.out.println("\n");
            System.out.println(newCollaborator);
        }
        Collections.sort(collaborators, cn);
    }
    public void editCollaborator() {
        int selec;
        String change, confirm;
        System.out.println("\n>Selecione o colaborador que deseja editar:");
        Collaborator person = searchCollaborator(collaborators);
        if(person == null) {
            return;
        } 
        System.out.println("\n");
        System.out.println(person);
        System.out.println("\n");
        System.out.println("#########--EDITAR COLABORADOR--#########");
        System.out.println("(0) Voltar");
        System.out.println("(1) Editar nome");
        System.out.println("(2) Editar email");
        System.out.println("(3) Editar senha");
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 0 || selec > 3);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            System.out.println(">Digite o nome do colaborador: ");
            change = read.nextLine();
            person.setName(change);
        }
        else if(selec == 2) {
            System.out.println(">Digite o email do colaborador: ");
            change = read.nextLine();
            person.setEmail(change);
        }
        else if(selec == 3) {
            System.out.println(">Digite o nome do colaborador: ");
            change = read.nextLine();
            do{
                System.out.println("\n>Digite a nova senha do colaborador: ");
                change = read.nextLine();
                System.out.println("\n>Confirme a senha: ");
                confirm = read.nextLine();
                if(!(change.equals(confirm))) {
                    System.out.println("\nSenha incorreta!");
                }
            }while(!(change.equals(confirm)));    // confirmacao de senha
            person.setPassword(change);
        }
        System.out.println("\n");
        System.out.println(person);
    }
    public void addNewProject() {
        CompareTitle ct = new CompareTitle();
        String title, fundingAgency, objective, description;
        LocalDate startDate, endDate;
        int day, month, year;
        Double fundingValue;
        int selec; 
        System.out.println("\n");
        System.out.println("#########--ADICIONAR NOVO PROJETO--#########");
        System.out.println("\n>Digite o titulo do projeto: ");
        title = read.nextLine();
        for(int i = 0; i < projects.size(); i++){  // verifica se nome de projeto ja existe
            if(title.equals(projects.get(i).getTitle())){
                System.out.println("\nTitulo ja cadastrado!");
                return;
            }
        }
        Project newProject = new Project(title);
        System.out.println("\n>Digite a data de inicio do projeto(dia, mes e ano separados por espaço): ");
        day = read.nextInt();
        month = read.nextInt();
        year = read.nextInt();
        read.nextLine();
        startDate = LocalDate.of(year, month, day);
        newProject.setStartDate(startDate);  
        System.out.println("\n>Digite a data de termino do projeto(dia, mes e ano separados por espaço): ");
        day = read.nextInt();
        month = read.nextInt();
        year = read.nextInt();
        read.nextLine();
        endDate = LocalDate.of(year, month, day);
        newProject.setEndDate(endDate);
        System.out.println("\n>Adicionar agencia financiadora?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = read.nextInt();
        read.nextLine();
        if(selec == 1) {
            System.out.println("\n>Informe a agencia financiadora do projeto: ");
            fundingAgency = read.nextLine();
            newProject.setFundingAgency(fundingAgency);
        }
        System.out.println("\n>Adicionar valor financiado?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = read.nextInt();
        read.nextLine();
        if(selec == 1) {
            System.out.println("\n>Informe o valor financiado(separado por ponto): ");
            fundingValue = Double.parseDouble(read.nextLine());
            newProject.setFundingValue(fundingValue);
        }
        System.out.println("\n>Adicionar objetivo do projeto?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = read.nextInt();
        read.nextLine();
        if(selec == 1) {
            System.out.println("\n>Informe o objetivo do projeto: ");
            objective = read.nextLine();
            newProject.setObjective(objective);
        }
        System.out.println("\n>Adicionar descricao do projeto?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = read.nextInt();
        read.nextLine();
        if(selec == 1) {
            System.out.println("\n>Informe a descricao do projeto: ");
            description = read.nextLine();
            newProject.setDescription(description);
        }
        System.out.println("\n>Adicionar participantes?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = read.nextInt();
        read.nextLine();
        if(selec == 1) {
            boolean added;
            do{
                added = false;
                Collaborator participant = searchCollaborator(collaborators);
                if(participant != null) {
                    if(newProject.getParticipants() != null) {
                        for(int i = 0; i < newProject.getParticipants().size(); i++) {
                            if(participant.getEmail() == newProject.getParticipants().get(i).getEmail()) {
                                added = true;
                                break;
                            }
                        }
                    }
                    if(added == false) {
                        if(participant.getClass().getSimpleName() == "Student") {
                            Student st = (Student) participant;
                            newProject.addParticipant(st);
                        }
                        else {
                            newProject.addParticipant(participant); 
                        }            
                    } 
                    else {
                        System.out.println("\nColaborador ja participa do projeto!");
                    } 
                }
                System.out.println("\n>Adicionar outro participante?");
                System.out.println("\n(1) Sim");
                System.out.println("\n(2) Nao");
                selec = read.nextInt();
                read.nextLine();
            }while(selec == 1);
        }
        projects.add(newProject);
        Collections.sort(projects, ct); // mantem a lista de projetos em ordem alfabetica
        System.out.println("\n");
        System.out.println(newProject);
    }
    public void editProject() {
        int selec;
        System.out.println("\n>Selecione o projeto que deseja editar:");
        Project pj = searchProject(projects);
        if(pj == null) {
            return;
        }
        System.out.println("\n");
        System.out.println(pj);
        System.out.println("\n");
        System.out.println("#########--EDITAR PROJETO--#########");
        System.out.println("(0) Voltar");
        System.out.println("(1) Editar titulo");
        System.out.println("(2) Editar data de inicio");
        System.out.println("(3) Editar data de termino");
        System.out.println("(4) Editar agencia financiadora");
        System.out.println("(5) Editar valor financiado");
        System.out.println("(6) Editar objetivo");
        System.out.println("(7) Editar descricao");
        System.out.println("(8) Adicionar participante");
        System.out.println("(9) Remover participante");
        System.out.println("(10) Mudar status");
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 0 || selec > 10);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            String title;
            CompareTitle ct = new CompareTitle();
            System.out.println("\n>Digite o novo titulo do projeto: ");
            title = read.nextLine();
            for(int i = 0; i < projects.size(); i++){  // verifica se nome de projeto ja existe
                if(title.equals(projects.get(i).getTitle())){
                    System.out.println("\nTitulo ja cadastrado!");
                    return;
                }
            }
            pj.setTitle(title);
            Collections.sort(projects, ct); // mantem a lista de projetos em ordem alfabetica
        }
        else if(selec == 2) {
            int day, month, year;
            LocalDate startDate;
            System.out.println("\n>Digite a data de inicio do projeto(dia, mes e ano separados por espaço): ");
            day = read.nextInt();
            month = read.nextInt();
            year = read.nextInt();
            read.nextLine();
            startDate = LocalDate.of(year, month, day);
            pj.setStartDate(startDate);
        }
        else if(selec == 3) {
            int day, month, year;
            LocalDate endDate;
            System.out.println("\n>Digite a data de termino do projeto(dia, mes e ano separados por espaço): ");
            day = read.nextInt();
            month = read.nextInt();
            year = read.nextInt();
            read.nextLine();
            endDate = LocalDate.of(year, month, day);
            pj.setEndDate(endDate);
        }
        else if(selec == 4) {
            String fundingAgency;
            System.out.println("\n>Digite o nome da agencia financiadora: ");
            fundingAgency = read.nextLine();
            pj.setFundingAgency(fundingAgency);
        }
        else if(selec == 5) {
            Double fundingValue;
            System.out.println("\n>Digite o valor financiado: ");
            fundingValue = read.nextDouble();
            read.nextLine();
            pj.setFundingValue(fundingValue);
        }
        else if(selec == 6) {
            String objective;
            System.out.println("\n>Digite o objetivo do projeto: ");
            objective = read.nextLine();
            pj.setObjective(objective);
        }
        else if(selec == 7) {
            String description;
            System.out.println("\n>Digite a descricao do projeto: ");
            description = read.nextLine();
            pj.setDescription(description);
        }
        else if(selec == 8) {
            Collaborator participant;
            boolean added;
            System.out.println("\n>Adicionar participante:");
            if(pj.getStatus() == 0) {   // verifica se projeto esta em andamento
                do{
                    added = false;
                    participant = searchCollaborator(collaborators);
                    if(participant != null) {
                        if(pj.getParticipants() != null) {
                            for(int i = 0; i < pj.getParticipants().size(); i++) {
                                if(participant.getEmail() == pj.getParticipants().get(i).getEmail()) {
                                    added = true;
                                    break;
                                }
                            }
                        }
                        if(added == false) {
                            if(participant.getClass().getSimpleName() == "Student") {
                                Student st = (Student) participant;
                                pj.addParticipant(st);
                            }
                            else{
                                pj.addParticipant(participant);
                            }  
                        }
                        else {
                            System.out.println("\nColaborador ja participa do projeto!");
                        } 
                    }
                    System.out.println("\n>Adicionar outro participante?");
                    System.out.println("\n(1) Sim");
                    System.out.println("\n(2) Nao");
                    do {
                        selec = read.nextInt();
                        read.nextLine();
                    } while(selec < 1 || selec > 2);
                } while(selec == 1);
            }
            else {
                System.out.println("\nNao e possivel fazer alocacao! (O projeto nao esta em elaboracao)");
            }  
        }
        else if(selec == 9) {
            Collaborator participant;
            System.out.println("\n>Remover participante: ");
            if(pj.getStatus() == 0) {
                participant = searchCollaborator(pj.getParticipants());
                if(participant != null) {
                    pj.removeParticipant(participant.getEmail());
                }   
            }
            else {
                System.out.println("\nNao e possivel fazer alocacao! (O projeto nao esta em elaboracao)");
            }
        }
        else if(selec == 10) {
            System.out.println("\n>Mudar status: ");
            pj.changeStatus();
        }
        System.out.println("\n");
        System.out.println(pj);
    }
    public void addAcademicProductionMenu() {
        int selec;
        String title;
        int yearOfPublication;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR PRODUCAO ACADEMICA--#########");
        System.out.println("\n>Selecione o tipo de producao academica: ");
        System.out.println("\n(1) Publicacao");
        System.out.println("\n(2) Orientacao");
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 1 || selec > 2);
        if(selec == 1) {
            Collaborator author;
            String conferenceName;
            Project associatedProject;
            boolean added;
            System.out.println("\n");
            System.out.println("#########--ADICIONAR PUBLICACAO--#########");
            System.out.println("\n>Digite o titulo da publicacao: ");
            title = read.nextLine();
            System.out.println("\n>Digite o ano de publicacao: ");
            yearOfPublication = read.nextInt();
            read.nextLine();
            System.out.println("\n>Digite o nome da conferencia onde foi publicada: ");
            conferenceName = read.nextLine();
            Publication newPublication = new Publication(title, yearOfPublication, conferenceName);
            System.out.println("\n>Adicionar autores: ");
            do{
                added = false;
                author = searchCollaborator(collaborators);
                if(author != null) {
                    if(newPublication.getAuthors() != null) {
                        for(int i = 0; i < newPublication.getAuthors().size(); i++) {   // verifica se autor ja foi adicionado
                            if(author.getEmail() == newPublication.getAuthors().get(i).getEmail()) {
                                added = true;
                                break;
                            }
                        }
                    }
                    if(added == false) {
                        newPublication.addAuthor(author);
                        author.addAcademicProduction(newPublication);
                    } 
                    else {
                        System.out.println("\nAutor ja foi adicionado!");
                    }
                }
                System.out.println("\n>Adicionar outro autor?");
                System.out.println("\n(1) Sim");
                System.out.println("\n(2) Nao");
                do {
                    selec = read.nextInt();
                    read.nextLine();
                } while(selec < 1 || selec > 2);
            } while(selec == 1);
            System.out.println("\n>Adicionar projeto de pesquisa associado? (O projeto precisa estar em andamento).");
            System.out.println("\n(1) Sim");
            System.out.println("\n(2) Nao");
            do {
                selec = read.nextInt();
                read.nextLine();
            } while(selec < 1 || selec > 2);
            if(selec == 1) {
                do {
                    ArrayList<Project> inProgress = new ArrayList<Project>();
                    for(int i = 0; i < projects.size(); i++) {
                        if(projects.get(i).getStatus() == 1) {
                            inProgress.add(projects.get(i));
                        }
                    }
                    associatedProject = searchProject(inProgress);  // busca apenas entre os projetos em andamento
                    if(associatedProject != null) {
                        associatedProject.addPublication(newPublication);
                    }
                    else {
                        System.out.println("\n>Tentar novamente?");
                        System.out.println("\n(1) Nao");
                        System.out.println("\n(2) Sim");
                        selec = read.nextInt();
                        read.nextLine();
                    }
                } while(selec == 2);
            }
            productions.add(newPublication);
            System.out.println("\n");
            System.out.println(newPublication);
            selec = 1;
        }
        else if(selec == 2) {
            Professor advisor;
            Student student;
            ArrayList<Collaborator> professors = new ArrayList<Collaborator>();
            ArrayList<Collaborator> students = new ArrayList<Collaborator>();
            System.out.println("\n");
            System.out.println("#########--ADICIONAR ORIENTACAO--#########");
            System.out.println("\n>Digite o titulo da orientacao: ");
            title = read.nextLine();
            System.out.println("\n>Digite o ano de publicacao: ");
            yearOfPublication = read.nextInt();
            read.nextLine();
            System.out.println("\n>Adicionar orientador: ");
            for(int i = 0; i < collaborators.size(); i++) {
                if(collaborators.get(i).getClass().getSimpleName() == "Professor") {
                    professors.add((Professor) collaborators.get(i));
                }
                else if(collaborators.get(i).getClass().getSimpleName() == "Student") {
                    students.add((Student) collaborators.get(i));
                }
            }
            do{
                advisor = (Professor) searchCollaborator(professors);
                if(advisor == null) {
                    System.out.println("\n(1) Tentar novamente");
                    System.out.println("\n(2) Cancelar");
                    selec = read.nextInt();
                    read.nextLine();
                    if(selec == 2) {
                        return;
                    }
                }
                else {
                    selec = 0;
                }
            } while(selec == 1);
            
            System.out.println("\n>Adicionar aluno: ");
            do {
                student = (Student) searchCollaborator(students);
                if(student == null) {
                    System.out.println("\n(1) Tentar novamente");
                    System.out.println("\n(2) Cancelar");
                    selec = read.nextInt();
                    read.nextLine();
                    if(selec == 2) {
                        return;
                    }
                }
                else {
                    selec = 0;
                }
            } while(selec == 1);
            
            Guidance newGuidance = new Guidance(title, yearOfPublication, advisor, student);
            productions.add(newGuidance);
            advisor.addAcademicProduction(newGuidance);
            student.addAcademicProduction(newGuidance);
            System.out.println("\n");
            System.out.println(newGuidance);
        }
    }
    public Collaborator searchCollaborator(ArrayList<Collaborator> collaborators) {
        ArrayList<Collaborator> solution = new ArrayList<Collaborator>();
        String name;
        int selec;
        System.out.println("\n");
        System.out.println("#########--BUSCAR COLABORADOR--#########");
        System.out.println(">Digite o nome ou email do colaborador: ");
        name = read.nextLine();
        name = name.toLowerCase();
        for(int i = 0; i < collaborators.size(); i++) {
            if(collaborators.get(i).getName().toLowerCase().contains(name) || collaborators.get(i).getEmail().toLowerCase().contains(name)) {
                solution.add(collaborators.get(i));
            }
        }
        if(solution.size() == 0) {
            System.out.println("Nenhum colaborador encontrado.");
            return null;
        }
        System.out.println("\n>Selecione o colaborador: ");
        for(int i = 0; i < solution.size(); i++) {
            System.out.println("\n("+ i +")"+" "+ solution.get(i).getName() + "\n    Email: " + solution.get(i).getEmail() + "\n");
        }
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 0 || selec > solution.size()); // verifica se a entrada esta dentro do limite
        return solution.get(selec);
    }
    public void searchByCollaborator() {
        Collaborator person = searchCollaborator(collaborators);
        if(person != null) {
            System.out.println(person);
        } 
    }
    public Project searchProject(ArrayList<Project> projects) {
        ArrayList<Project> solution = new ArrayList<Project>();
        String name;
        int selec;
        System.out.println("\n");
        System.out.println("#########--BUSCAR PROJETO--#########");
        System.out.println(">Digite o titulo do projeto: ");
        name = read.nextLine();
        name = name.toLowerCase();
        for(int i = 0; i < projects.size(); i++) {
            if(projects.get(i).getTitle().toLowerCase().contains(name)) {
                solution.add(projects.get(i));
            }
        }
        if(solution.size() == 0) {
            System.out.println("Nenhum projeto encontrado.");
            return null;
        }
        System.out.println("\n>Selecione o projeto: ");
        for(int i = 0; i < solution.size(); i++) {
            System.out.println("\n("+ i +")"+" "+ solution.get(i).getTitle() + "\n    Descricao: " + solution.get(i).getDescription() + "\n");
        }
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 0 || selec > solution.size()); // verifica se a entrada esta dentro do limite
        return solution.get(selec);
    }
    public void searchByProject() {
        Project pj = searchProject(projects);
        if(pj != null) {
            System.out.println(pj);
        }
    }
    public AcademicProduction searchProduction(ArrayList<AcademicProduction> productions) {
        ArrayList<AcademicProduction> solution = new ArrayList<AcademicProduction>();
        String name;
        int selec;
        System.out.println("\n");
        System.out.println("#########--BUSCAR PRODUCAO ACADEMICA--#########");
        System.out.println(">Digite o titulo da producao academica: ");
        name = read.nextLine();
        name = name.toLowerCase();
        for(int i = 0; i < productions.size(); i++) {
            if(productions.get(i).getTitle().toLowerCase().contains(name)) {
                solution.add(productions.get(i));
            }
        }
        if(solution.size() == 0) {
            System.out.println("Nenhuma producao academica encontrada.");
            return null;
        }
        System.out.println("\n>Selecione a producao academica: ");
        for(int i = 0; i < solution.size(); i++) {
            System.out.println("\n("+ i +")"+" "+ solution.get(i).getTitle() + "\n    Ano de publicacao: " + solution.get(i).getYearOfPublication() + "\n");
        }
        do {
            selec = read.nextInt();
            read.nextLine();
        } while(selec < 0 || selec > solution.size()); // verifica se a entrada esta dentro do limite
        return solution.get(selec);
    }
    public void searchByProduction() {
        AcademicProduction ap = searchProduction(productions);
        if(ap != null) {
            System.out.println(ap);
        }
    }
    /* Relatorio de produção academica do laboratorio */
    public void productionReport() {
        int nCollaborators = 0, nInElaboration = 0, nInProgress = 0, nCompleted = 0, nProjects = 0, nPublications = 0, nGuidances = 0;
        for(int i = 0; i < collaborators.size(); i++) {
            nCollaborators = collaborators.size();;
        }
        if(projects.size() != 0) {
            for(int i = 0; i < projects.size(); i++) {
                nProjects += 1;
                if(projects.get(i).getStatus() == 0) {
                    nInElaboration += 1;
                }
                else if(projects.get(i).getStatus() == 1) {
                    nInProgress += 1;
                }
                else if(projects.get(i).getStatus() == 2) {
                    nCompleted += 1;
                }
            }
        }
        if(productions.size() != 0) {
            for(int i = 0; i < productions.size(); i++) {
                if(productions.get(i).getClass().getSimpleName() == "Publication") {
                    nPublications += 1;
                }
                else if(productions.get(i).getClass().getSimpleName() == "Guidance") {
                    nGuidances += 1;
                }
            }
        }
        System.out.println("\n");
        System.out.println("#########--RELATORIO DE PRODUTIVIDADE--#########");
        System.out.println("\nNumero de colaboradores: " + nCollaborators);
        System.out.println("\nNumero de projetos em elaboracao: " + nInElaboration);
        System.out.println("\nNumero de projetos em andamento: " + nInProgress);
        System.out.println("\nNumero de projetos concluidos: " + nCompleted);
        System.out.println("\nNumero total de projetos: " + nProjects);
        System.out.println("\nNumero de publicacoes: " + nPublications);
        System.out.println("\nNumero de orientacoes: " + nGuidances);
    }
}
