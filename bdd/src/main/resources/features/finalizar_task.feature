Feature: Encerrar a task quando seus requisitos forem cumpridos
    
    Scenario: O hunter solicita a finalização da Task e o PO aceita dentro do prazo
        Given que o PO possui uma Task cadastrada no sistema
        And que a Task possui hunters trabalhando nela
        When o hunter solicita para finalizar a Task
        Then o PO é notificado da solicitação de finalização
        And o PO tem até 72 horas úteis para aceitar ou negar a solicitação
        When o PO aceita a solicitação dentro do prazo
        Then a Task é finalizada com sucesso
        And os hunters que trabalharam na Task são notificados que a Task foi finalizada
        And o pagamento é liberado para os hunters
        And os hunters evoluem de level no sistema

    Scenario: O hunter solicita a finalização da Task e o PO não responde dentro do prazo
        Given que o PO possui uma Task cadastrada no sistema
        And que a Task possui hunters trabalhando nela
        When o hunter solicita para finalizar a Task
        Then o PO é notificado da solicitação de finalização
        And o PO tem até 72 horas úteis para aceitar ou negar a solicitação
        When o PO não responde dentro do prazo
        Then a Task é finalizada automaticamente com sucesso
        And os hunters que trabalharam na Task são notificados que a Task foi finalizada
        And o pagamento é liberado para os hunters
        And os hunters evoluem de level no sistema

    Scenario: O hunter solicita a finalização da Task e o PO rejeita dentro do prazo
        Given que o PO possui uma Task cadastrada no sistema
        And que a Task possui hunters trabalhando nela
        When o hunter solicita para finalizar a Task
        Then o PO é notificado da solicitação de finalização
        And o PO tem até 72 horas úteis para aceitar ou negar a solicitação
        When o PO rejeita a solicitação dentro do prazo
        Then a Task não é finalizada
        And o PO deve fornecer feedback do motivo da rejeição
        And o hunter é notificado da rejeição e do feedback


    Scenario: O PO solicita alterações após a solicitação de finalização do hunter
        Given que o PO possui uma Task cadastrada no sistema
        And que a Task possui hunters trabalhando nela
        When o hunter solicita para finalizar a Task
        Then o PO é notificado da solicitação de finalização
        And o PO tem até 72 horas úteis para aceitar ou negar a solicitação
        When o PO rejeita a solicitação e solicita alterações
        Then o hunter é notificado das alterações solicitadas
        And o hunter pode reenviar a solicitação de finalização após realizar as alterações

    Scenario: O PO solicita o cancelamento da Task após a solicitação de finalização do hunter
        Given que o PO possui uma Task cadastrada no sistema
        And que a Task possui hunters trabalhando nela
        And o hunter solicitou a finalização da Task
        When o PO decide cancelar a Task
        Then o PO é estornado da recompensa da Task
        And a Task é finalizada
        And os hunters que trabalharam na Task são notificados que a Task foi cancelada
        And os hunters possuem o código desenvolvido até aquele ponto
        And o sistema retém as mensagens trocadas entre o PO e os hunters