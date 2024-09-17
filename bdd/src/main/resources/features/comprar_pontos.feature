Feature: Comprar pontos virtuais para abrir tasks e realizar pagamentos

    Scenario: O PO deseja comprar pontos virtuais para abrir tasks
        Given que o PO possui o dinheiro necessário para comprar os pontos virtuais
        When o PO compra pontos virtuais
        Then os pontos virtuais são adicionados à conta do PO
        And o PO é notificado que os pontos foram adicionados com sucesso
        And o sistema retira uma taxa de 5-10% do valor total da compra, a depender de quanto foi comprado.
