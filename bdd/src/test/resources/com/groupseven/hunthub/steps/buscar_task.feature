Feature: Buscar por tasks novas

    Scenario: O hunter deseja buscar por tasks novas com filtros de pesquisa e busca por filtros válidos
        Given que o hunter pesquisa por filtros de reward 100, numberOfMeetings 5, ratingRequired 4.5 e tags "JAVA, SPRING"
        When o hunter busca por tasks novas
        Then as tasks "são" retornadas pelo sistema
    
    Scenario: O hunter tenta buscar por tasks novas com filtros inválidos
        Given que o hunter pesquisa por filtros de reward 100000, numberOfMeetings 0, ratingRequired 5.5 e tags "PYTHON"
        When o hunter busca por tasks novas
        Then as tasks "não são" retornadas pelo sistema
        And o sistema sugere redefinir os filtros ou remover alguns para ampliar a busca