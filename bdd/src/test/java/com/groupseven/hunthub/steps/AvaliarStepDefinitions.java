package com.groupseven.hunthub.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskId;
import com.groupseven.hunthub.domain.models.TaskStatus;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.HunterRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.PoRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;

import com.groupseven.hunthub.steps.builder.BasicHunterBuilder;
import com.groupseven.hunthub.steps.builder.BasicPoBuilder;
import com.groupseven.hunthub.steps.builder.BasicTaskBuilder;
import com.groupseven.hunthub.steps.director.HunterDirector;
import com.groupseven.hunthub.steps.director.PODirector;
import com.groupseven.hunthub.steps.director.TaskDirector;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

//import static org.mockito.Mockito.*;
public class AvaliarStepDefinitions {

    private TaskService taskService;
    private final NotificationService notificationService = new NotificationService(new NotificationRepositoryImpl());
    private final PoRepository poRepository = new PoRepositoryImpl();
    private final HunterService hunterService = new HunterService(new HunterRepositoryImpl(), poRepository);
    private final POService poService = new POService(poRepository);
    private PO po;
    private Hunter hunter1;
    private Hunter hunter2;
    private final Task task;
    List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);

    List<Hunter> hunters;

    public AvaliarStepDefinitions() {
        this.hunters = new ArrayList<>();

        BasicPoBuilder poBuilder = new BasicPoBuilder();
        PODirector poDirector = new PODirector(poBuilder);
        poDirector.constructPO();
        this.po = poBuilder.getPo();

        BasicTaskBuilder taskBuilder = new BasicTaskBuilder();
        TaskDirector taskDirector = new TaskDirector(taskBuilder);
        taskDirector.constructTask();
        this.task = taskBuilder.getTask();

        task.setPO(po);

        BasicHunterBuilder hunterBuilder = new BasicHunterBuilder();
        HunterDirector hunterDirector = new HunterDirector(hunterBuilder);
        this.hunter1 = hunterDirector.getSpecificHunter("Jessie");

        this.hunter2 = hunterDirector.getSpecificHunter("Alex");

        //hunter1 = new Hunter("12345678901", "Jessie Hunter", "jessiehunter@example.com", "passwordhunter", null, null, "Desenvolvedor experiente", "https://example.com/profile/jessie.jpg", null, null, null, null);
        //hunter2 = new Hunter("12345678902", "Alex Hunter", "alexhunter@example.com", "passwordhunter2", null, null, "Caçador de soluções", "https://example.com/profile/alex.jpg", null, null, null, null);
        this.hunters.add(hunter1);
        this.hunters.add(hunter2);


        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        this.taskService = new TaskService(taskRepository, poRepository, notificationService);
    }

    @Given("que a Task foi finalizada")
    public void task_completada_com_sucesso() {
        task.setStatus(TaskStatus.DONE);
    }

    @When("os users \\(PO e hunters) são notificados que a Task foi finalizada")
    public void notifyPOandHunters() {

        var response = notificationService.NotifyPO(this.task.getPo(), this.task.getTitle(), "Task finalizada");
        assertTrue(response);
        for (Hunter hunter : hunters) {
            var responseHunter = notificationService.NotifyHunter(hunter, this.task.getTitle(), "Task finalizada");
            assertTrue(responseHunter);
        }
    }

    @Then("os hunters avaliam o PO e os outros hunters")
    public void hunterAvalia() {

        int rating1 = 4;
        int rating2 = 5;
        int ratingForPO = 5;

        hunterService.rateHunter(hunter1, hunter2, rating1);
        hunterService.rateHunter(hunter1, hunter2, rating2);
        hunterService.ratePO(po, ratingForPO);

        hunterService.rateHunter(hunter2, hunter1, rating1);
        hunterService.rateHunter(hunter2, hunter1, rating2);
        hunterService.ratePO(po, ratingForPO);

        assertEquals(4.5, hunter2.getRating());
        assertEquals(4.5, hunter1.getRating());

        assertEquals(ratingForPO, po.getRating());
    }

    @And("o PO avalia os hunters")
    public void poAvaliaHunters() {
        int ratingForHunter1 = 5;
        int ratingForHunter2 = 4;
        poService.rateHunter(hunter1, ratingForHunter1);
        poService.rateHunter(hunter2, ratingForHunter2);

        assertEquals("4,67", hunter1.ratingToString());
        assertEquals("4,33", hunter2.ratingToString());

    }

    @And("o pagamento é liberado para os hunters")
    public void liberarPagamentoHunters() {
        int totalReward = task.getReward();
        int numberOfHunters = hunters.size();
        if (numberOfHunters == 0) {
            fail("Nenhum hunter está associado à Task.");
        }
        int rewardPerHunter = totalReward / numberOfHunters;
        for (Hunter hunter : hunters) {
            hunter.setPoints(hunter.getPoints() + rewardPerHunter);
            // System.out.println("Pagando " + rewardPerHunter + " pontos ao Hunter: " + hunter.getName());
        }
        for (Hunter hunter : hunters) {
            assertEquals(hunter.getPoints(), rewardPerHunter);
        }
    }
}
