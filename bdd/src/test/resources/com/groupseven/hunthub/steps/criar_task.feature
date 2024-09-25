Feature: Criar task

Scenario: O PO possui uma ideia para uma nova Task e os pontos necessarios para cria-la
    Given que o PO possui a quantidade de pontos necessaria 500 para criar uma nova Task 300
    When o PO cria uma nova Task com os detalhes: description "Desenvolver nova funcionalidade"; title "Nova Funcionalidade"; deadline "2024-10-01"; reward 100; numberOfMeetings 2; numberOfHuntersRequired 3
    Then a Task e criada com sucesso
    And a Task aparece no sistema para os hunters
    And o pagamento do valor da task e feito e retido no sistema ate a finalizacao da task

Scenario: O PO não possui pontos suficientes para criar uma nova Task
    Given que o PO não possui a quantidade de pontos necessária para criar uma nova Task
    When o PO tenta criar uma nova Task com os detalhes:
        | description                | "Desenvolver nova funcionalidade" |
        | title                      | "Nova Funcionalidade"             |
        | deadline                   | "2024-10-01"                      |
        | reward                     | 100                               |
        | numberOfMeetings           | 2                                 |
        | numberOfHuntersRequired    | 3                                 |
    Then o sistema não deixa o PO criar a Task
    And o PO é notificado que não possui pontos suficientes
    And o PO é redirecionado para a página de compra de pontos

