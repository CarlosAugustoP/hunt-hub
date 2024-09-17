Feature: No final de uma task, os usuários se avaliam mutuamente

    Scenario: Uma task é finalizada, e os hunters encontram-se em avaliação.
        Given que a Task foi finalizada
        When os hunters são notificados que a Task foi finalizada
        Then os hunters avaliam o PO e os outros hunters
        And o PO sobe sua avaliação com base no que foi avaliado no sistema

    Scenario: Uma task é finalizada, e o PO avalia os hunters.
        Given que a Task foi finalizada
        When o PO é notificado que a Task foi finalizada
        Then o PO avalia os hunters
        And os hunters sobem sua avaliação com base no que foi avaliado no sistema