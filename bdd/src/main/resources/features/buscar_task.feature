Feature: Buscar por tasks novas

    Scenario: O hunter deseja buscar por tasks com filtros de pesquisa
        Given que o hunter efetua uma pesquisa por uma task 
        And que o hunter define os filtros de pesquisa (ex: tags, localização, nível de dificuldade)
        When o hunter busca por tasks novas
        Then o sistema retorna as tasks disponíveis que correspondem aos filtros definidos

    Scenario: O hunter deseja buscar por tasks novas sem filtros de pesquisa
        Given que o hunter efetua uma pesquisa por uma task 
        When o hunter busca por tasks novas
        Then o sistema retorna as tasks disponíveis ordenada por data de criação

    Scenario: O hunter deseja buscar por tasks que não existem
        Given que o hunter efetua uma pesquisa por uma task  
        When o hunter busca pela task
        Then o sistema retorna que não há tasks disponíveis que correspondem aos filtros definidos

    Scenario: O hunter deseja buscar por uma task em específico
        Given que o hunter efetua uma pesquisa por uma task  
        When o hunter busca pela task
        Then o sistema retorna a task desejada

    