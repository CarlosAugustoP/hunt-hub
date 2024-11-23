package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.PoRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import com.groupseven.hunthub.steps.builder.BasicPoBuilder;
import com.groupseven.hunthub.steps.director.PODirector;
import com.groupseven.hunthub.steps.interfaces.PoBuilder;
import com.groupseven.hunthub.steps.interfaces.TaskBuilder;
import com.groupseven.hunthub.steps.director.TaskDirector;
import com.groupseven.hunthub.steps.builder.BasicTaskBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterTaskStepDefinitions {

    private final TaskService taskService;
    private final POService poService;
    private Map<String, Object> searchFilters;
    private List<Task> resultTasks;
    private String errorMessage;
    private PO po;
    private Task task;

    public FilterTaskStepDefinitions() {
        PoRepositoryImpl poRepository = new PoRepositoryImpl();
        this.poService = new POService(poRepository);

        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        NotificationService notificationService = new NotificationService(new NotificationRepositoryImpl());
        this.taskService = new TaskService(taskRepository, poRepository, notificationService);

        PoBuilder basicPoBuilder = new BasicPoBuilder();
        PODirector poDirector = new PODirector(basicPoBuilder);
        poDirector.constructPO();
        this.po = poDirector.getPO();
        poRepository.save(po);

        TaskBuilder basicTaskBuilder = new BasicTaskBuilder();
        TaskDirector taskDirector = new TaskDirector(basicTaskBuilder);
        taskDirector.constructTask();
        this.task = taskDirector.getTask();
        task.setPO(po);

        try {
            createSampleTask();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createSampleTask() throws ParseException {
        po.setPoints(500);
        taskService.createTask(po.getId().getId(), task.getDescription(), task.getTitle(), task.getDeadline(), task.getReward(), task.getNumberOfMeetings(), task.getNumberOfHuntersRequired(), task.getRatingRequired(), task.getTags());
    }

    @Given("que o hunter pesquisa por filtros de reward {int}, numberOfMeetings {int}, ratingRequired {double} e tags {string}")
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
        } else {
            assertNotNull(resultTasks);
        }
    }

    @And("o sistema sugere redefinir os filtros ou remover alguns para ampliar a busca")
    public void giveNewSuggestion() {
        System.out.println("Sugestão: redefina os filtros ou remova alguns para ampliar a busca.");
    }
}
