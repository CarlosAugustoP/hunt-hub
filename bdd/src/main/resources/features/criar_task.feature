Feature: Criar Task

Scenario: O PO possui uma ideia para uma nova Task, e os pontos necessários para criá-la
    Given que o PO possui a quantidade de pontos necessária para criar uma nova Task
    When o PO cria uma nova Task
    And o PO define os detalhes da Task (ex: tags, descrição, nível de avaliação necessário, prazo, quantia de reuniões, recompensa)
    Then a Task é criada com sucesso
    And a Task aparece no sistema para os hunters
    And o pagamento do valor da task é feito e retido no sistema até a finalização da task 
    And o PO poderá avaliar as aplicações dos hunters

Scenario: O PO não possui pontos suficientes para criar uma nova Task
    Given que o PO não possui a quantidade de pontos necessária para criar uma nova Task
    When o PO tenta criar uma nova Task
    Then o sistema não deixa o PO criar a Task
    And o PO é notificado que não possui pontos suficientes
    And o PO é redirecionado para a página de compra de pontos

