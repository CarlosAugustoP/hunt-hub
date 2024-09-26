Feature: Buscar por tasks novas

    Scenario: O hunter deseja buscar por tasks novas com filtros de pesquisa
        Given que o hunter possui uma conta no sistema
        And que o hunter define os filtros de pesquisa (ex: tags, localização, nível de dificuldade)
        When o hunter busca por tasks novas
        Then o sistema retorna as tasks disponíveis que correspondem aos filtros definidos