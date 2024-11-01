package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.PoRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Arrays;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterTaskStepDefinitions {

    private final TaskService taskService;
    private final PoRepository poRepository = new PoRepositoryImpl();
    private final POService poService = new POService(poRepository);

    private Map<String, Object> searchFilters;
    private List<Task> resultTasks;
    private final PO po;
    private String errorMessage;

    public FilterTaskStepDefinitions() {
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        this.taskService = new TaskService(taskRepository, poRepository);

        String cpf = "12345678900";
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password123";
        int levels = 5;
        int rating = 4;
        int ratingCount = 5;
        int totalRating = 20;
        String profilePicture = "https://example.com/profile/johndoe.jpg";
        String bio = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
        this.po = new PO(cpf, name, email, password, new ArrayList<>(), profilePicture, bio);

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
        List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);
        po.setPoints(500);

        taskService.createTask(po.getId().getId(), description, title, deadline, reward, numberOfMeetings,
                numberOfHuntersRequired, ratingRequired, tags);
    }

    @Given("que o hunter pesquisa por filtros de reward {int}, numberOfMeetings {int} e ratingRequired {double} e tags {string}")
    public void hunterPesquisandoPorFiltros(int reward, int numberOfMeetings, double ratingRequired,
            String tagsString) {
        List<Tags> tags = Arrays.stream(tagsString.split(","))
                .map(String::trim)
                .map(Tags::valueOf)
                .toList();

        searchFilters = new HashMap<>();
        searchFilters.put("reward", reward);
        searchFilters.put("numberOfMeetings", numberOfMeetings);
        searchFilters.put("ratingRequired", ratingRequired);
        searchFilters.put("tags", tags);
    }

    @When("o hunter busca por tasks novas")
    public void hunterBuscaPorTasksNovas() {
        resultTasks = taskService.findByFilter(searchFilters);
    }

    @Then("as tasks {string} retornadas pelo sistema")
    public void sistemaRetornaTasksDisponiveis(String answer) {
        if (answer.equals("não são")) {
            assertTrue(resultTasks.isEmpty());
            System.out.println("Nenhuma task encontrada.");
        } else {
            assertNotNull(resultTasks);
            System.out.println("Encontramos a task: " + resultTasks);
            System.out.println("Descrição: " + resultTasks.get(0).getDescription());
            System.out.println("Deadline: " + resultTasks.get(0).getDeadline());
            System.out.println("Recompensa: " + resultTasks.get(0).getReward());
            System.out.println("Número de reuniões: " + resultTasks.get(0).getNumberOfMeetings());
            System.out.println("Número de caçadores necessários: " + resultTasks.get(0).getNumberOfHuntersRequired());
            System.out.println("Rating necessário: " + resultTasks.get(0).getRatingRequired());
            System.out.println("Tags: " + resultTasks.get(0).getTags());

        }
    }

    @And("o sistema sugere redefinir os filtros ou remover alguns para ampliar a busca")
    public void giveNewSuggestion() {
        System.out.println("Sugestão: redefina os filtros ou remova alguns para ampliar a busca.");
    }
}
