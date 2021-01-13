import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Laboratory {
    private ArrayList<Collaborator> collaborators = new ArrayList<Collaborator>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<AcademicProduction> productions = new ArrayList<AcademicProduction>();
    private Scanner read = new Scanner(System.in);

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
            Menu.collaboratorMenu(this, person);
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
    public String setCollaboratorName() {
        System.out.println("\n>Digite o nome do colaborador: ");
        return read.nextLine();
    }
    public String setCollaboratorEmail() {
        System.out.println("\n>Digite o email do colaborador: ");
        return read.nextLine();
    }
    public String setCollaboratorPassword() {
        String password, confirm;
        do{
            System.out.println("\n>Digite a senha: ");
            password = read.nextLine();
            System.out.println("\n>Confirme a senha: ");
            confirm = read.nextLine();
            if(!(password.equals(confirm))) {
                System.out.println("\nSenha incorreta!");
            }
        }while(!(password.equals(confirm)));    // confirmacao de senha
        return password;
    }
    public void addNewCollaborator() {
        CompareName cn = new CompareName();
        String name, email, password;
        int selec;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR NOVO COLABORADOR--#########");
        name = setCollaboratorName();
        email = setCollaboratorEmail();
        if(checkEmail(email) != null) {
            System.out.println("\nEmail ja cadastrado!");
            return;
        }
        password = setCollaboratorPassword();
        System.out.println("\n>Selecione o tipo de vinculo:");
        System.out.println("(1) Professor");
        System.out.println("(2) Pesquisador");
        System.out.println("(3) Aluno");
        selec = Menu.readOption(0, 3);
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
            selec = Menu.readOption(1, 3);
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
        String change;
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
        selec = Menu.readOption(0, 3);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            change = setCollaboratorName();
            person.setName(change);
        }
        else if(selec == 2) {
            change = setCollaboratorEmail();
            person.setEmail(change);
        }
        else if(selec == 3) {
            change = setCollaboratorPassword();
            person.setPassword(change);
        }
        System.out.println("\n");
        System.out.println(person);
    }
    public void setProjectTitle(Project pj) {
        String title;
        if(pj.getStatus() != 0) {
            System.out.println("O titulo nao pode ser editado! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Digite o titulo do projeto: ");
        title = read.nextLine();
        for(int i = 0; i < projects.size(); i++){  // verifica se nome de projeto ja existe
            if(title.equals(projects.get(i).getTitle())){
                System.out.println("\nTitulo ja cadastrado!");
                return;
            }
        }
        pj.setTitle(title);
    }
    public void setProjectStartDate(Project pj) {
        int day, month, year;
        LocalDate startDate;
        if(pj.getStatus() != 0) {
            System.out.println("A data de inicio nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Digite a data de inicio do projeto(dia, mes e ano separados por espaço): ");
        startDate = readDate();
        pj.setStartDate(startDate);
    }
    public void setProjectEndDate(Project pj) {
        LocalDate endDate;
        int day, month, year;
        if(pj.getStatus() != 0) {
            System.out.println("A data de termino nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Digite a data de termino do projeto(dia, mes e ano separados por espaço): ");
        endDate = readDate();
        pj.setEndDate(endDate);
    }
    public LocalDate readDate() {
        LocalDate date;
        int day, month, year;
        boolean done;
        do {
            try {
                day = read.nextInt();
                month = read.nextInt();
                year = read.nextInt();
                date = LocalDate.of(year, month, day);
                read.nextLine();
                done = true;
                return date;
            } catch(Exception e) {
                read.nextLine();
                System.out.println("\nEntrada invalida! Tente novamente: ");
                done = false;
            }
        } while(!done);
        return null;
    }
    public void setProjectFundingAgency(Project pj) {
        String fundingAgency;
        if(pj.getStatus() != 0) {
            System.out.println("A agencia financiadora nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe a agencia financiadora do projeto: ");
        fundingAgency = read.nextLine();
        pj.setFundingAgency(fundingAgency);
    }
    public void setProjectFundingValue(Project pj) {
        Double fundingValue;
        if(pj.getStatus() != 0) {
            System.out.println("O valor financiado nao pode ser editado! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe o valor financiado(separado por ponto): ");
        fundingValue = readDouble();
        pj.setFundingValue(fundingValue);
    }
    public Double readDouble() {
        Double num = (double) 0;
        boolean done;
        do {
            try {
                System.out.print("\n> ");
                num = Double.parseDouble(read.nextLine());
                done = true;
                return num;
            } catch(Exception e) {
                System.out.println("\nEntrada invalida! Tente novamente: ");
                done = false;
            }
        } while(!done);
        return num;
    }
    public void setProjectObjective(Project pj) {
        String objective;
        if(pj.getStatus() != 0) {
            System.out.println("O objetivo do projeto nao pode ser editado! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe o objetivo do projeto: ");
        objective = read.nextLine();
        pj.setObjective(objective);
    }
    public void setProjectDescription(Project pj) {
        String description;
        if(pj.getStatus() != 0) {
            System.out.println("A descricao do projeto nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe a descricao do projeto: ");
        description = read.nextLine();
        pj.setDescription(description);
    }
    public void addProjectParticipant(Project pj) {
        Collaborator participant;
        boolean added;
        int selec;
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
                        pj.addParticipant(participant); 
                    }
                    else {
                        System.out.println("\nColaborador ja participa do projeto!");
                    } 
                }
                System.out.println("\n>Adicionar outro participante?");
                System.out.println("\n(1) Sim");
                System.out.println("\n(2) Nao");
                selec = Menu.readOption(1, 2);
            } while(selec == 1);
        }
        else {
            System.out.println("\nNao e possivel fazer alocacao! (O projeto nao esta em elaboracao)");
        }  
    }
    public void removeProjectParticipant(Project pj) {
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
    public void changeProjectStatus(Project pj) {
        System.out.println("\n>Mudar status: ");
        pj.changeStatus();
    }
    public void addNewProject() {
        CompareTitle ct = new CompareTitle();
        Project newProject = new Project();
        int selec; 
        System.out.println("\n");
        System.out.println("#########--ADICIONAR NOVO PROJETO--#########");
        setProjectTitle(newProject);
        if(newProject.getTitle() == null) {
            return;
        }
        setProjectStartDate(newProject);
        setProjectEndDate(newProject);
        System.out.println("\n>Adicionar agencia financiadora?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = Menu.readOption(1, 2);
        if(selec == 1) {
            setProjectFundingAgency(newProject);
        }
        System.out.println("\n>Adicionar valor financiado?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = Menu.readOption(1, 2);
        if(selec == 1) {
            setProjectFundingValue(newProject);
        }
        System.out.println("\n>Adicionar objetivo do projeto?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = Menu.readOption(1, 2);
        if(selec == 1) {
            setProjectObjective(newProject);
        }
        System.out.println("\n>Adicionar descricao do projeto?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = Menu.readOption(1, 2);        
        if(selec == 1) {
            setProjectDescription(newProject);
        }
        System.out.println("\n>Adicionar participantes?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = Menu.readOption(1, 2);
        if(selec == 1) {
            addProjectParticipant(newProject);
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
        else if(pj.getStatus() == 2) {
            System.out.println("\nNao e possivel editar! (o projeto foi concluido)");
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
        selec = Menu.readOption(0, 10);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            CompareTitle ct = new CompareTitle();
            setProjectTitle(pj);
            if(pj.getTitle() == null) {
                return;
            }
            Collections.sort(projects, ct); // mantem a lista de projetos em ordem alfabetica
        }
        else if(selec == 2) {
            setProjectStartDate(pj);
        }
        else if(selec == 3) {
           setProjectEndDate(pj);
        }
        else if(selec == 4) {
            setProjectFundingAgency(pj);
        }
        else if(selec == 5) {
            setProjectFundingValue(pj);
        }
        else if(selec == 6) {
            setProjectObjective(pj);
        }
        else if(selec == 7) {
            setProjectDescription(pj);
        }
        else if(selec == 8) {
            addProjectParticipant(pj);
        }
        else if(selec == 9) {
            removeProjectParticipant(pj);
        }
        else if(selec == 10) {
            changeProjectStatus(pj); 
        }
        System.out.println("\n");
        System.out.println(pj);
    }
    public void addPublication() {
        String title;
        int yearOfPublication, selec;
        Collaborator author;
        String conferenceName;
        Project associatedProject;
        boolean added;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR PUBLICACAO--#########");
        System.out.println("\n>Digite o titulo da publicacao: ");
        title = read.nextLine();
        System.out.println("\n>Digite o ano de publicacao: ");
        yearOfPublication = readInt();
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
            selec = Menu.readOption(1, 2);
        } while(selec == 1);
        if(newPublication.getAuthors() == null) {
            System.out.println("Nenhum autor foi associado! (A publicacao nao foi registrada)");
            return;
        }
        System.out.println("\n>Adicionar projeto de pesquisa associado? (O projeto precisa estar em andamento).");
        System.out.println("\n(1) Sim");
        System.out.println("\n(2) Nao");
        selec = Menu.readOption(1, 2);
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
                    selec = Menu.readOption(1, 2);
                }
            } while(selec == 2);
        }
        productions.add(newPublication);
        System.out.println("\n");
        System.out.println(newPublication);
    }
    public void addGuidance() {
        int selec;
        String title;
        int yearOfPublication;
        Professor advisor;
        Student student;
        ArrayList<Collaborator> professors = new ArrayList<Collaborator>();
        ArrayList<Collaborator> students = new ArrayList<Collaborator>();
        System.out.println("\n");
        System.out.println("#########--ADICIONAR ORIENTACAO--#########");
        System.out.println("\n>Digite o titulo da orientacao: ");
        title = read.nextLine();
        System.out.println("\n>Digite o ano de publicacao: ");
        yearOfPublication = readInt();
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
                selec = Menu.readOption(1, 2);
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
                selec = Menu.readOption(1, 2);
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
    public int readInt() {
        int num;
        boolean done;
        do {
            try {
                System.out.print("\n> ");
                num = read.nextInt();
                read.nextLine();
                done = true;
                return num;
            } catch(Exception e) {
                System.out.println("\nEntrada invalida! Tente novamente: ");
                read.nextLine();
                done = false;
            }
        } while(!done);
        return -1;
    }
    public void addAcademicProductionMenu() {
        int selec;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR PRODUCAO ACADEMICA--#########");
        System.out.println("\n>Selecione o tipo de producao academica: ");
        System.out.println("\n(0) Voltar");
        System.out.println("\n(1) Publicacao");
        System.out.println("\n(2) Orientacao");
        selec = Menu.readOption(0, 2);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            addPublication();
        }
        else if(selec == 2) {
            addGuidance();
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
        selec = Menu.readOption(0, solution.size() - 1);
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
        selec = Menu.readOption(0, solution.size() - 1);
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
        selec = Menu.readOption(0, solution.size() - 1);
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
