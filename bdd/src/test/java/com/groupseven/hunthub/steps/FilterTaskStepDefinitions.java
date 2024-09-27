package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterTaskStepDefinitions {

    private final TaskService taskService;
    private Map<String, Object> searchFilters;
    private List<Task> resultTasks;

    // Constructor to initialize TaskService
    public FilterTaskStepDefinitions() {
        TaskRepository taskRepository = new TaskRepositoryImpl(); // Use your actual implementation here
        this.taskService = new TaskService(taskRepository);
        this.searchFilters = new HashMap<>();
    }

    @Given("que o hunter pesquisa por filtros")
    public void hunterPesquisandoPorFiltros() {
        searchFilters = new HashMap<>();
    }

    @Given("que o hunter define o filtro de pesquisa para reward com o valor {int}")
    public void hunterDefineFiltroPesquisaReward(int reward) {
        searchFilters.put("reward", reward);
    }

    @Given("que o hunter define o filtro de pesquisa para numberOfMeetings com o valor {int}")
    public void hunterDefineFiltroPesquisaNumberOfMeetings(int numberOfMeetings) {
        searchFilters.put("numberOfMeetings", numberOfMeetings);
    }

    @Given("que o hunter define o filtro de pesquisa para ratingRequired com o valor {double}")
    public void hunterDefineFiltroPesquisaRatingRequired(double ratingRequired) {
        searchFilters.put("ratingRequired", ratingRequired);
    }

    @When("o hunter busca por tasks novas")
    public void hunterBuscaPorTasksNovas() {
        resultTasks = taskService.findByFilter(searchFilters);
    }

    @Then("o sistema retorna as tasks disponíveis que correspondem aos filtros definidos")
    public void sistemaRetornaTasksDisponiveis() {
        if (resultTasks == null || resultTasks.isEmpty()) {
            throw new RuntimeException("Nenhuma task foi retornada!");
        }

        for (Task task : resultTasks) {
            int rewardFilter = (int) searchFilters.get("reward");
            int numberOfMeetingsFilter = (int) searchFilters.get("numberOfMeetings");
            double ratingRequiredFilter = (double) searchFilters.get("ratingRequired");

            if (task.getReward() < rewardFilter) {
                throw new RuntimeException("Task com reward abaixo do filtro!");
            }
            if (task.getNumberOfMeetings() < numberOfMeetingsFilter) {
                throw new RuntimeException("Task com numberOfMeetings abaixo do filtro!");
            }
            if (task.getRatingRequired() < ratingRequiredFilter) {
                throw new RuntimeException("Task com ratingRequired abaixo do filtro!");
            }
        }
    }
}
