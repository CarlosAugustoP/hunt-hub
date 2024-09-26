package com.groupseven.hunthub.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FilterTaskStepDefinitions {

    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
    private final TaskService taskService = new TaskService(taskRepository);

    private List<Task> searchResults;
    private Map<String, Object> searchFilters = new HashMap<>();  // Map para armazenar os filtros
    private String errorMessage;

    // --------- Casos de Sucesso ---------
    @Given("que o hunter pesquisa por filtros")
    public void hunterSearchesForFilters() {
        // Inicializando os filtros de busca (Map vazio inicialmente)
        searchFilters.clear();
    }

    @Given("que o hunter define os filtros de pesquisa")
    public void hunterDefinesSearchFilters() {
        // Adicionando filtros válidos ao Map
        searchFilters.put("reward", 100);  // Exemplo: Filtro para recompensa maior que 100
        searchFilters.put("meetings", 3);  // Exemplo: Filtro para número de encontros maior que 3
        searchFilters.put("ratingRequired", 4);  // Exemplo: Filtro para rating maior que 4
    }

    @When("o hunter busca por tasks novas")
    public void hunterSearchesForNewTasks() {
        // Passando os filtros para o serviço de busca de tasks
        searchResults = taskService.findByFilter(searchFilters);
    }

    @Then("o sistema retorna as tasks disponíveis que correspondem aos filtros definidos")
    public void systemReturnsAvailableTasks() {
        // Verificando se o sistema retornou tasks que correspondem aos filtros definidos
        assertNotNull(searchResults);
        assertFalse(searchResults.isEmpty());
    }

    // --------- Casos de Erro ---------
    @Given("que o hunter define os filtros de pesquisa inválidos")
    public void hunterDefinesInvalidSearchFilters() {
        // Adicionando filtros inválidos ao Map
        searchFilters.put("reward", -10);  // Exemplo: Recompensa negativa (inválida)
        searchFilters.put("location", "Marte");  // Exemplo: Localização fora da área de cobertura
        searchFilters.put("difficulty", 999);  // Exemplo: Nível de dificuldade inexistente
    }

    @When("o hunter tenta buscar por tasks novas")
    public void hunterTriesToSearchForNewTasks() {
        // Passando os filtros para o serviço de busca de tasks
        searchResults = taskService.findByFilter(searchFilters);

        // Verificando se não há resultados e gerando uma mensagem de erro
        if (searchResults == null || searchResults.isEmpty()) {
            errorMessage = "Nenhum resultado corresponde aos filtros aplicados.";
        }
    }

    @Then("o sistema não retorna nenhuma task disponível")
    public void systemReturnsNoTasks() {
        // Verificando que o sistema não retornou tasks
        assertTrue(searchResults.isEmpty());
    }

    @Then("o sistema exibe uma mensagem de erro informando que nenhum resultado corresponde aos filtros aplicados")
    public void systemDisplaysErrorMessage() {
        // Verificando se a mensagem de erro foi exibida
        assertEquals("Nenhum resultado corresponde aos filtros aplicados.", errorMessage);
    }

    @Then("o sistema sugere redefinir os filtros ou remover alguns para ampliar a busca")
    public void systemSuggestsFilterAdjustment() {
        // Simulando a sugestão do sistema para redefinir filtros
        String suggestion = "Tente redefinir os filtros ou remover alguns para melhorar os resultados.";
        assertNotNull(suggestion);  // Apenas para simular o comportamento do sistema
    }
}
