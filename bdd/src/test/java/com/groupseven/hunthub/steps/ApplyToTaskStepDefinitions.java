package com.groupseven.hunthub.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.PoRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackages = "com.groupseven.hunthub")
public class ApplyToTaskStepDefinitions {

    private Exception exception;
    private boolean isPoNotified = false;
    private boolean isHunterNotified = false;

    NotificationService notificationService;

    TaskService taskService;

    private Hunter hunter;
    private PO po;
    private Task task;
    private TaskStatus taskStatus;

    private final String cpfHunter = "12345678901";
    private final String nameHunter = "John Doe";
    private final String emailHunter = "john.doe@example.com";
    private final String passwordHunter = "hunter123";
    private final String linkPortfolio = "https://portfolio.example.com/johndoe";
    private final String bioHunter = "Experienced developer and hunter.";
    private final String profilePictureHunter = "https://example.com/johndoe.jpg";
    private final List<String> certificationsHunter = List.of("Java Certification", "AWS Certified");
    private final List<String> linksHunter = List.of("https://github.com/johndoe", "https://linkedin.com/in/johndoe");
    private final List<Achievement> achievementsHunter = new ArrayList<>();
    private final List<Project> projectsHunter = new ArrayList<>();

    private final String cpfPO = "98765432101";
    private final String namePO = "Jane Smith";
    private final String emailPO = "jane.smith@example.com";
    private final String passwordPO = "po123";
    private final String profilePicturePO = "https://example.com/janesmith.jpg";
    private final String bioPO = "Product Owner with 10 years of experience.";

    private final String descriptionTask = "Develop a new feature for the application.";
    private final String titleTask = "Feature Development";
    private final Date deadlineTask = new Date();
    private final int rewardTask = 1000;
    private final int numberOfMeetingsTask = 5;
    private final int numberOfHuntersRequiredTask = 3;
    private final List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);

    @Before
    public void setUp() {

        isPoNotified = false;
        isHunterNotified = false;
        exception = null;

        hunter = new Hunter(
                cpfHunter,
                nameHunter,
                emailHunter,
                passwordHunter,
                linkPortfolio,
                new ArrayList<>(),
                bioHunter,
                profilePictureHunter,
                certificationsHunter,
                linksHunter,
                achievementsHunter,
                projectsHunter);
        hunter.setRating(0.0);

        po = new PO(
                cpfPO,
                namePO,
                emailPO,
                passwordPO,
                new ArrayList<>(),
                profilePicturePO,
                bioPO);

        notificationService = new NotificationService(new NotificationRepositoryImpl());
        taskService = new TaskService(new TaskRepositoryImpl(), new PoRepositoryImpl(), notificationService);
    }

    @Given("que o hunter tem a avaliação {double} e a Task tem a avaliação necessária {double} e o status da vaga é {string}")
    public void task_qualification(double hunterRating, double taskRating, String taskStatusStr) {
        hunter.setRating(hunterRating);

        task = new Task(
                po,
                descriptionTask,
                titleTask,
                deadlineTask,
                rewardTask,
                numberOfMeetingsTask,
                numberOfHuntersRequiredTask,
                taskRating,
                tags,
                new TaskId(UUID.randomUUID()));

        taskStatus = TaskStatus.valueOf(taskStatusStr.toUpperCase());
        task.setStatus(taskStatus);
        po.addTask(task);
        taskService.createTask(task);
    }

    @When("o hunter aplica para a Task")
    public void hunter_apply_to_task() {
        try {
            taskService.applyHunterToTask(task, hunter);

            isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                    "Você aplicou nessa Task! O PO foi notificado!");
            isPoNotified = notificationService.NotifyPO(po, task.getTitle(),
                    "Você recebeu uma aplicação nova nessa Task do usuário " + hunter.getName());

        } catch (Exception e) {
            exception = e;
        }
    }


    @Then("a aplicação {string}")
    public void task_applied(String taskApplied) {
        if (taskApplied.equals("não é enviada")) {
            assertNotNull(exception, "Expected an exception, but none occurred");
            if (task.getStatus() == TaskStatus.DONE) {
                assertEquals("Cannot apply to task. The task is already closed.", exception.getMessage());
            } else if (task.getStatus() == TaskStatus.PENDING) {
                assertEquals("Cannot apply to task. Rating required: " + task.getRatingRequired()
                        + ". Your rating " + hunter.getRating(), exception.getMessage());
            }
        } else {
            assertNull(exception, "Did not expect an exception, but one occurred");
            assertFalse(task.getHuntersApplied().isEmpty(), "No hunters have applied to the task.");
            assertEquals(hunter, task.getHuntersApplied().get(task.getHuntersApplied().size() - 1));

        }
    }

    @And("o hunter é notificado que a aplicação foi enviada com sucesso")
    public void o_hunter_eh_notificado_que_a_aplicacao_foi_enviada_com_sucesso() {
        assertTrue(isHunterNotified);
    }

    @And("o PO é notificado que o hunter aplicou para a Task")
    public void o_po_eh_notificado_que_o_hunter_aplicou_para_a_task() {
        assertTrue(isPoNotified);
    }

    @Given("que a aplicação de um hunter foi recebida pelo PO")
    public void que_a_aplicacao_de_um_hunter_foi_recebida_pelo_PO() {

        task = new Task(
                po,
                descriptionTask,
                titleTask,
                deadlineTask,
                rewardTask,
                numberOfMeetingsTask,
                numberOfHuntersRequiredTask,
                0.0,
                tags,
                new TaskId(UUID.randomUUID())
        );
        task.setStatus(TaskStatus.PENDING);
        po.addTask(task);
        taskService.createTask(task);

        try {
            taskService.applyHunterToTask(task, hunter);
        }

        catch (Exception e) {
            exception = e;
        }
        isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                "Você aplicou nessa Task! O PO foi notificado!");
        isPoNotified = notificationService.NotifyPO(po, task.getTitle(),
                "Você recebeu uma aplicação nova nessa Task do usuário " + hunter.getName());
    }

    @When("o PO {string} o hunter para a Task")
    public void po_acao_hunter_para_task(String action) {
        if (action.equals("aceita")) {
            taskService.acceptHunter(task, hunter);
            isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                    "Você foi aceito para a Task!");
        }

        else if (action.equals("recusa")) {
            taskService.declineHunter(task, hunter);
            isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                    "Você foi recusado para a Task.");
        }
    }

    @Then("o hunter é {string} da Task")
    public void verifica_status_hunter_na_task(String status) {
        if (status.equals("designado a fazer parte")) {
            assertTrue(task.getHunters().contains(hunter));
        }

        else if (status.equals("removido da lista de aplicados")) {
            assertFalse(task.getHuntersApplied().contains(hunter));
        }
    }

    @And("o hunter é notificado que foi {string} para fazer parte da Task")
    public void hunter_notificado_para_task(String notificacaoStatus) {
        assertTrue(isHunterNotified);
    }
}