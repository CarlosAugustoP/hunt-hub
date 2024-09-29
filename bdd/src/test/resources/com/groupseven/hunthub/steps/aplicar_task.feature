Feature: Aplicar para uma Task
  Scenario: Há vagas disponíveis e o Hunter é qualificado
    Given que o hunter tem a avaliação 4.8 e a Task tem a avaliação necessária 4.7 e o status da vaga é "open"
    When o hunter aplica para a Task
    Then a aplicação "é enviada"
    And o hunter é notificado que a aplicação foi enviada com sucesso
    And o PO é notificado que o hunter aplicou para a Task

  Scenario: O hunter não possui a qualificação necessária para aplicar.
    Given que o hunter tem a avaliação 4.6 e a Task tem a avaliação necessária 4.7 e o status da vaga é "open"
    When o hunter aplica para a Task
    Then a aplicação "não é enviada"

  Scenario: Não há mais vagas disponíveis
    Given que o hunter tem a avaliação 4.7 e a Task tem a avaliação necessária 4.7 e o status da vaga é "closed"
    When o hunter aplica para a Task
    Then a aplicação "não é enviada"
