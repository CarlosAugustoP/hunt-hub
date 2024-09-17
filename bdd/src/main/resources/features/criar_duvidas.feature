Feature: Adicionar dúvidas nas tasks para que os POs possam esclarecê-las
    
    Scenario: O hunter tem dúvidas sobre a realização da task
        Given que o hunter encontra uma task
        When o hunter publica uma dúvida
        Then o PO é notificado que o hunter tem dúvidas sobre a Task
        And os usuários poderão ver a dúvida do hunter 
    
    Scenario: O PO recebe dúvidas de hunters sobre a realização da task
        Given que o PO criou uma Task
        When o PO responde as dúvidas dos hunters
        Then o hunter é notificado que suas dúvidas foram respondidas