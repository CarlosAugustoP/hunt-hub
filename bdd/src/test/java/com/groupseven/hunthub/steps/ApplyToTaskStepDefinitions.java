package com.groupseven.hunthub.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.Before;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApplyToTaskStepDefinitions {

    Exception exception;
    boolean isPoNotified = false;
    boolean isHunterNotified = false;

    private final NotificationService notificationService = new NotificationService(new NotificationRepositoryImpl());
    private final TaskService taskService = new TaskService(new TaskRepositoryImpl());

    String cpfHunter = "12345678901";
    String nameHunter = "John Doe";
    String emailHunter = "john.doe@example.com";
    String passwordHunter = "hunter123";
    String linkPortfolio = "https://portfolio.example.com/johndoe";
    List<Task> tasksHunter = new ArrayList<>();
    String bioHunter = "Experienced developer and hunter.";
    String profilePictureHunter = "https://example.com/johndoe.jpg";
    List<String> certificationsHunter = List.of("Java Certification", "AWS Certified");
    List<String> linksHunter = List.of("https://github.com/johndoe", "https://linkedin.com/in/johndoe");
    List<Achievement> achievementsHunter = new ArrayList<>();
    List<Project> projectsHunter = new ArrayList<>();

    Hunter hunter;

    String cpfPO = "98765432101";
    String namePO = "Jane Smith";
    String emailPO = "jane.smith@example.com";
    String passwordPO = "po123";
    String profilePicturePO = "https://example.com/janesmith.jpg";
    String bioPO = "Product Owner with 10 years of experience.";

    PO po;

    String descriptionTask = "Develop a new feature for the application.";
    String titleTask = "Feature Development";
    Date deadlineTask = new Date();
    int rewardTask = 1000;
    int numberOfMeetingsTask = 5;
    int numberOfHuntersRequiredTask = 3;
    List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);
    TaskStatus taskStatus;

    Task task;

    @Before
    public void setUp() {
        isPoNotified = false;
        isHunterNotified = false;
        exception = null;

        tasksHunter = new ArrayList<>();
        hunter = new Hunter(
                cpfHunter,
                nameHunter,
                emailHunter,
                passwordHunter,
                linkPortfolio,
                tasksHunter,
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


    }

    //
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
                new TaskId(UUID.randomUUID())
        );

        TaskStatus status = TaskStatus.valueOf(taskStatusStr.toUpperCase());
        this.taskStatus = status;
        task.setStatus(status);
        System.out.println("Status da Task: " + task.getStatus());
        po.addTask(task);

        System.out.println("Rating do hunter: " + hunter.getRating());
        System.out.println("Rating necessário para a Task: " + task.getRatingRequired());
        System.out.println("Status da Task: " + task.getStatus());
        System.out.println("Número de hunters aplicados: " + task.getHuntersApplied().size());
    }

    @When("o hunter aplica para a Task")
    public void hunter_apply_to_task() {
        System.out.println("Task status agora: " + task.getStatus());
        try {
            System.out.println("Task status before applying: " + task.getStatus());
            TaskService.applyHunterToTask(task, hunter); // O problema pode ser aqui
            isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                    "Você aplicou nessa Task! O PO foi notificado!");
            isPoNotified = notificationService.NotifyPO(po, task.getTitle(),
                    "Você recebeu uma aplicação nova nessa Task do usuário " + hunter.getName());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            exception = e;
        }
    }

    @Then("a aplicação {string}")
    public void task_applied(String taskApplied) {
        System.out.println("Task status after applying in then : " + task.getStatus());
        if (taskApplied.equals("não é enviada")) {
            System.out.println("Valor da task" + task.getStatus());
            if (task.getStatus() == TaskStatus.DONE) {
                System.out.println("Task registrada como done" + task.getStatus());
                assertEquals("Cannot apply to task. The task is already closed.", exception.getMessage());
            } else if (task.getStatus() == TaskStatus.PENDING) {
                System.out.println("Task registrada como pending");
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
        try {
            TaskService.applyHunterToTask(task, hunter);
        } catch (Exception e) {
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
            TaskService.acceptHunter(task, hunter);
            isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                    "Você foi aceito para a Task!");
        } else if (action.equals("recusa")) {
            TaskService.declineHunter(task, hunter);
            isHunterNotified = notificationService.NotifyHunter(hunter, task.getTitle(),
                    "Você foi recusado para a Task.");
        }
    }

    @Then("o hunter é {string} da Task")
    public void verifica_status_hunter_na_task(String status) {
        if (status.equals("designado a fazer parte")) {
            assertTrue(task.getHunters().contains(hunter));
        } else if (status.equals("removido da lista de aplicados")) {
            assertFalse(task.getHuntersApplied().contains(hunter));
        }
    }

    @And("o hunter é notificado que foi {string} para fazer parte da Task")
    public void hunter_notificado_para_task(String notificacaoStatus) {
        assertTrue(isHunterNotified);
    }

}
