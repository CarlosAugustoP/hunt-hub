Feature: Criar task

  Scenario: O PO possui uma ideia para uma nova Task e os pontos necessarios para cria-la
    Given que o PO possui a quantidade de pontos 500
    When o PO cria uma nova Task de 300 pontos
    Then a Task e criada com sucesso
    And a Task aparece no sistema para os hunters
    And o pagamento do valor da task e feito e retido no sistema ate a finalizacao da task

  Scenario: O PO não possui pontos suficientes para criar uma nova Task
    Given que o PO possui a quantidade de pontos 100
    When o PO cria uma nova Task de 300 pontos
    Then o sistema não deixa o PO criar a Task
