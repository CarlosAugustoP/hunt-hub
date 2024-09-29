Feature: No final de uma task, os usuários se avaliam mutuamente

    Scenario: Uma task é finalizada, e os usuários encontram-se em avaliação.
        Given que a Task foi finalizada
        When os users (PO e hunters) são notificados que a Task foi finalizada
        Then os hunters avaliam o PO e os outros hunters
        And o PO avalia os hunters
        And o pagamento é liberado para os hunters

