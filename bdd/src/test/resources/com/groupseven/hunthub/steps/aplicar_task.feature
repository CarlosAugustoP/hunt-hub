Feature: Aplicar para uma Task

  Scenario: Há vagas disponíveis
    Given que o hunter é qualificado para a Task, e há vagas disponíveis
    When o hunter aplica para uma Task
    Then a aplicação e perfil do hunter é enviada com sucesso
    And o hunter é notificado que a aplicação foi enviada com sucesso
    And o PO é notificado que o hunter aplicou para a Task e terá acesso ao perfil

  Scenario: O hunter é qualificado
    Given que o hunter é qualificado para a Task, e há vagas disponíveis
    When o hunter aplica para uma Task
    Then a aplicação e perfil do hunter é enviada com sucesso
    And o hunter é notificado que a aplicação foi enviada com sucesso
    And o PO é notificado que o hunter aplicou para a Task e terá acesso ao perfil

  Scenario: O hunter não possui a qualificação necessária para aplicar.
    Given que o hunter não é qualificado para entrar na task
    When o hunter tenta aplicar para uma Task
    Then a aplicação não é enviada
    And o sistema não permite que o hunter aplique para a Task
    And o PO não é notificado que o hunter aplicou para a Task

  Scenario: Não há mais vagas disponíveis
    Given que o hunter possui uma conta no sistema
    When o hunter aplica para uma Task
    Then o hunter não é permitido a aplicar para a Task

