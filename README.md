# Sistema de Produtividade Acadêmica

## Como executar:

Para executar o sistema, basta compilar todas as classes java juntas e executar a classe App.

## Para fazer login como administrador:

* Usuário: admin
* Senha: 1234

## Refatoramento

Esse projeto é o refatoramento do projeto [Sistema de Produtividade Acadêmica](https://github.com/pamelams/SistemaProdutividadeAcademica). Foi realizado o tratamento de exceções e a implementação dos seguintes padrões de projeto:

### Extract Method

Os métodos [addNewCollaborator](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Laboratory.java#L122) e [editCollaborator](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Laboratory.java#L139) possuíam muitas duplicações de código entre si, isso foi resolvido com a criação de vários métodos que continham as partes duplicadas. O mesmo ocorreu com os métodos [addNewProject](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Laboratory.java#L312) e [editProject](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Laboratory.java#L363). Todos esses métodos se encontravam na classe "Menu" e foram movidos para uma nova classe "Laboratory" no refatoramento.

### Extract Class
A classe [Menu](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/master/src/Menu.java) estava sobrecarregada com muitos métodos e seu propósito já não estava claro. Isso foi resolvido com a criação das novas classes [Laboratory](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/master/src/Laboratory.java) e [ReadData](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/master/src/ReadData.java) para lidar com as diferentes responsabilidades.

### Move Accumulation to Collecting Parameter

O pré-processamento da string a ser mostrada ao printar o objeto foi retirado do método "toString" e colocado em novos métodos que serão invocados pelo método "writeContents", que por sua vez retornará a string pronta para o "toString". Isso foi feito nas classes [Collaborator](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Collaborator.java#L116), [Project](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Project.java#L266), [Guidance](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Guidance.java#L29) e [Publication](https://github.com/pamelams/SistemaProdutividadeRefatorado/blob/80105b09d97226f583924199b06ca41041eb0ade/src/Publication.java#L65).

## UML

![SistemaProdutividade-UML](https://user-images.githubusercontent.com/32972537/105046582-08664f80-5a48-11eb-8407-6bd6cbad622a.png)

## Funcionalidades do sistema:

### Login como administrador:

O administrador é responsável pela manutenção de todas as informações do sistema. Assim, é ele o responsável por adicionar novos colaboradores, projetos e produções acadêmicas ao sistema. Também é possível editar as informações de colaboradores e projetos já adicionados.

### Login como colaborador:

Os colaboradores podem fazer login no sistema através de seu email e senha. Os colaboradores não tem permissão de modificar as informações do sistema, sendo possível apenas consultar as informações dos projetos, dos colaboradores e das produções acadêmicas.

### Alocação de participantes:

A alocação e desalocação de participantes é permitida apenas quando o projeto está “em elaboração”. Um aluno de graduação não pode estar participando de mais de dois projetos não concluídos. 

### Alteração do status

O administrador pode mudar o status de um projeto de "em elaboração" para "em andamento" apenas quando se constarem todas as informações básicas a respeito do projeto cadastradas (título, data de início, data de término, agência financiadora, valor financiado, objetivo, descrição e participantes). Deve haver pelo menos um professor alocado para um projeto entrar "em andamento".
O administrador pode mudar o status de um projeto de "em andamento" para "concluído" apenas se existir alguma publicação associada ao projeto.

### Produção acadêmica

O sistema permite a inclusão de informações referentes à produção acadêmica (orientações e publicações). Uma orientação é feita por um professor que orienta um aluno do laboratório. Uma orientação possui título e ano de publicação.
Todos os colaboradores podem ter publicações, e uma publicação pode ter vários autores. Uma publicação possui título, nome da conferência onde foi publicada, ano de publicação e projeto de pesquisa associado (se houver). Uma publicação só poderá ser associada a um projeto quando o status do projeto
estiver “em andamento”.

### Consulta

* Consulta por colaborador: Através do nome ou email informado, o sistema mostrará uma lista de colaboradores correspondente. Selecionando o colaborador desejado, o sistema mostrará suas informações(nome, email, um histórico contendo a lista de projetos nos quais este colaborador participa ou participou, ordenados de forma decrescente pela data de término, e a lista de sua produção acadêmica).
* Consulta por projeto: Através do título informado, o sistema mostrará uma lista de projetos correspondente. Selecionando o projeto desejado, o sistema mostrará seus dados (incluindo colaboradores alocados e uma lista contendo toda a produção acadêmica do projeto, ordenada de forma decrescente de data (ano)).
* Consulta por produção acadêmica: Através do título da publicação ou orientação, o sistema informará uma lista de produções acadêmicas correspondente. Selecionando a produção acadêmica desejada, o sistema mostrará seus dados.

### Relatório de produtividade acadêmica

O sistema fornece um relatório de produção acadêmica do laboratório, contendo:
* Número de colaboradores;
* Número de projetos em elaboração;
* Número de projetos em andamento;
* Número de projetos concluídos;
* Número total de projetos;
* Número de produção acadêmica por tipo de produção.