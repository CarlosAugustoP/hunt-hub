Feature: Envio de mensagens dentro de um chatroom presente na própria task 

    Scenario: O hunter deseja enviar uma mensagem para os outros dentro de um chatroom de uma task
        Given que o hunter foi aprovado para uma Task
        When o hunter envia uma mensagem no chatroom da Task
        Then a mensagem é enviada com sucesso
        And o PO é notificado que o hunter enviou uma mensagem
        And os outros hunters que trabalham na Task são notificados que o hunter enviou uma mensagem

    Scenario: O PO deseja enviar uma mensagem para os hunters dentro de um chatroom de uma 
        Given que o PO possui uma Task criada
        And que a Task possui hunters trabalhando nela
        When o PO envia uma mensagem no chatroom da Task
        Then a mensagem é enviada com sucesso
        And os hunters que trabalham na Task são notificados que o PO enviou uma mensagem

    Scenario: O usuário envia uma mensagem, mas houve falha no envio 
        Given que o usuário enviou uma mensagem
        When a mensagem não é enviada
        Then o usuário é notificado que a mensagem não foi enviada
        And o usuário pode tentar enviar a mensagem novamente
