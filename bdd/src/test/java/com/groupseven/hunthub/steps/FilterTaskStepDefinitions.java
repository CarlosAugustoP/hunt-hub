package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilterTaskStepDefinitions {

    private final TaskService taskService;
    private Map<String, Object> searchFilters;
    private List<Task> resultTasks;
    private final PO po;
    private String errorMessage; // Variable to store error message for invalid filters

    // Constructor to initialize TaskService and PO
    public FilterTaskStepDefinitions() {
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl(); // Use your actual implementation here
        this.taskService = new TaskService(taskRepository);

        // Initialize PO with sufficient points and other details
        Long cpf = 12345678900L;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password123";
        int levels = 5;
        int rating = 4;
        String profilePicture = "https://example.com/profile/johndoe.jpg";
        String bio = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
        this.po = new PO(cpf, name, email, password, levels, rating, new ArrayList<>(), profilePicture, bio);

        // Create a task to ensure there are tasks available for filtering
        try {
            createSampleTask();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createSampleTask() throws ParseException {
        String description = "Sample task description";
        String title = "Sample Task Title";
        Date deadline = new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-31");
        int reward = 100;
        int numberOfMeetings = 5;
        int numberOfHuntersRequired = 2;
        double ratingRequired = 4.5;

        // Set points for PO to ensure they can create a task
        po.setPoints(500); // Ensure points are sufficient

        taskService.createTask(po, description, title, description, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired);
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

    @Given("que o hunter define os filtros de pesquisa inválidos")
    public void hunterDefineFiltrosInvalidos() {
        // Simulate adding invalid filters
        searchFilters.put("tags", "inexistente"); // Invalid tag
        searchFilters.put("localizacao", "fora da area"); // Invalid location
        searchFilters.put("nivelDificuldade", "inexistente"); // Invalid difficulty level
    }

    @When("o hunter busca por tasks novas")
    public void hunterBuscaPorTasksNovas() {
        resultTasks = taskService.findByFilter(searchFilters);

        // Check if the filters are invalid and handle accordingly
        if (resultTasks == null || resultTasks.isEmpty()) {
            errorMessage = "Nenhum resultado corresponde aos filtros aplicados. Sugestão: redefina os filtros ou remova alguns para ampliar a busca.";
        }
    }

    @Then("o sistema retorna as tasks disponíveis que correspondem aos filtros definidos")
    public void sistemaRetornaTasksDisponiveis() {
        if (resultTasks != null && !resultTasks.isEmpty()) {
            // Process the result tasks as needed
            System.out.println("Tasks correspondentes aos filtros definidos: " + resultTasks);
        } else {
            System.out.println("Nenhuma task foi retornada que corresponda aos filtros definidos.");
        }
    }

    @Then("o sistema não retorna nenhuma task disponível")
    public void sistemaNaoRetornaNenhumaTaskDisponivel() {
        if (resultTasks == null || resultTasks.isEmpty()) {
            System.out.println("Nenhuma task foi retornada que corresponda aos filtros inválidos.");
            System.out.println(errorMessage);
        }
    }

    @Then("o sistema exibe uma mensagem de erro informando que nenhum resultado corresponde aos filtros aplicados")
    public void showErrorMessage() {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            System.out.println("Mensagem de erro: " + errorMessage);
        } else {
            System.out.println("Nenhuma mensagem de erro a ser exibida.");
        }
    }

    @Then("o sistema sugere redefinir os filtros ou remover alguns para ampliar a busca")
    public void giveNewSuggestion() {
        System.out.println("Sugestão: redefina os filtros ou remova alguns para ampliar a busca.");
    }
}
