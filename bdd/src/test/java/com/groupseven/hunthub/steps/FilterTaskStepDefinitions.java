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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FilterTaskStepDefinitions {

    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
    private final TaskService taskService = new TaskService(taskRepository);
    private Map<String, Object> searchFilters;
    private List<Task> resultTasks;
    private final PO po;
    private String errorMessage;

    // Constructor to initialize TaskService and PO
    public FilterTaskStepDefinitions() {
        Long cpf = 12345678900L;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password123";
        int levels = 5;
        int rating = 4;
        int ratingCount = 5;
        int totalRating = 20;
        String profilePicture = "https://example.com/profile/johndoe.jpg";
        String bio = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
        this.po = new PO(cpf, name, email, password, levels, ratingCount, totalRating ,rating, new ArrayList<>(), profilePicture, bio);

        // Ensure sample task creation
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
        int reward = 100;  // Ensure this matches the test filter
        int numberOfMeetings = 5;  // Ensure this matches the test filter
        int numberOfHuntersRequired = 2;
        double ratingRequired = 4;  // Ensure this matches the test filter

        po.setPoints(500);  // Ensure PO has enough points

        // Create the task
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

    @When("o hunter busca por tasks novas")
    public void hunterBuscaPorTasksNovas() {
        resultTasks = taskService.findByFilter(searchFilters);

        if (resultTasks == null || resultTasks.isEmpty()) {
            errorMessage = "Nenhum resultado corresponde aos filtros aplicados. Sugestão: redefina os filtros ou remova alguns para ampliar a busca.";
        }
    }

    @Then("o sistema retorna as tasks disponíveis que correspondem aos filtros definidos")
    public void sistemaRetornaTasksDisponiveis() {
        if (resultTasks != null && !resultTasks.isEmpty()) {
            // Validação usando assertEquals para garantir que tasks correspondentes foram retornadas
            assertEquals(1, resultTasks.size(), "Deve haver uma task correspondente.");
            System.out.println("Tasks correspondentes aos filtros definidos: " + resultTasks);
        } else {
            fail("Nenhuma task foi retornada que corresponda aos filtros definidos.");
        }
    }
}
