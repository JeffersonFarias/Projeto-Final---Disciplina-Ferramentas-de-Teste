[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/1dFIfYeF)
# Projeto - Banco

Com base no projeto anexado a este post, realizar as seguintes tarefas:

1) Escrever testes em JUnit e encontrar todos os bugs que existem na implementação atual;
2) Garantir 100% de cobertura de branch;
3) Matar *todos* os mutantes gerados pela ferramenta PIT.

O projeto já está com JaCoCo e PIT configurados no gradle. Toda vez que se dá um comando `gradle build`, o relatório de JaCoCo já é gerado na pasta `app/build/reports/jacoco/test/html`. Para gerar os mutantes e avaliar a sua suíte de testes, use `gradle pitest`.

Sugerimos seguir a ordem das tarefas, pois a tarefa 2 será facilitada pela tarefa 1 e o mesmo acontece com relação a tarefa 3. A ferramenta PIT está configurada para verificar um mínimo de 70% de score de mutação. Portanto, enquanto não houver uma suíte de testes boa o suficiente para matar pelo menos 70% dos mutantes gerados, o build não vai ser considerado _successful_. 

Para entregar:
faça um .zip do projeto após rodar o comando `gradle clean` (para diminuir o tamanho do arquivo gerado) e anexe aqui:
faça um documento curto no google docs explicando a experiência, os bugs revelados pelos testes e as dificuldades encontradas na realização das tarefas, e compartilhe por aqui.