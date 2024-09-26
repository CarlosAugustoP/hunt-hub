Feature: Buscar por tasks novas

    Scenario: O hunter deseja buscar por tasks novas com filtros de pesquisa e busca por filtros válidos
        Given que o hunter pesquisa por filtros
        And que o hunter define os filtros de pesquisa (ex: tags, localização, nível de dificuldade)
        When o hunter busca por tasks novas
        Then o sistema retorna as tasks disponíveis que correspondem aos filtros definidos
    
    Scenario: O hunter tenta buscar por tasks novas com filtros inválidos
        Given que o hunter pesquisa por filtros
        And que o hunter define os filtros de pesquisa inválidos (ex: tags inexistentes, localização fora da área de cobertura, nível de dificuldade inexistente)
        When o hunter busca por tasks novas
        Then o sistema não retorna nenhuma task disponível
        And o sistema exibe uma mensagem de erro informando que nenhum resultado corresponde aos filtros aplicados
        And o sistema sugere redefinir os filtros ou remover alguns para ampliar a busca