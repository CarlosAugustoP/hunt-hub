package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.groupseven.hunthub.domain.services.NotificationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplyToTaskStepDefinitions {
    Exception exception;
    boolean isPoNotified = false;
    boolean isHunterNotified = false;

    private final NotificationService notificationService = new NotificationService(new NotificationRepositoryImpl());
    private final TaskService taskService = new TaskService(new TaskRepositoryImpl());

    Long cpfHunter = 12345678901L;
    String nameHunter = "John Doe";
    String emailHunter = "john.doe@example.com";
    String passwordHunter = "hunter123";
    String linkPortfolio = "https://portfolio.example.com/johndoe";
    List<Task> tasksHunter = new ArrayList<>(); // Lista de tarefas vazia
    String bioHunter = "Experienced developer and hunter.";
    String profilePictureHunter = "https://example.com/johndoe.jpg";
    int levelHunter = 5;
    List<String> certificationsHunter = List.of("Java Certification", "AWS Certified");
    List<String> linksHunter = List.of("https://github.com/johndoe", "https://linkedin.com/in/johndoe");
    List<Achievement> achievementsHunter = new ArrayList<>();
    List<Project> projectsHunter = new ArrayList<>();
    int ratingCountHunter = 10;
    int totalRatingHunter = 45;

    Hunter hunter = new Hunter(
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
            projectsHunter
    );

    Long cpfPO = 98765432101L;
    String namePO = "Jane Smith";
    String emailPO = "jane.smith@example.com";
    String passwordPO = "po123";
    List<Task> tasksPO = new ArrayList<>();
    String profilePicturePO = "https://example.com/janesmith.jpg";
    String bioPO = "Product Owner with 10 years of experience.";

    PO po = new PO(
            cpfPO,
            namePO,
            emailPO,
            passwordPO,
            tasksPO,
            profilePicturePO,
            bioPO
    );

    String descriptionTask = "Develop a new feature for the application.";
    String titleTask = "Feature Development";
    Date deadlineTask = new Date();
    int rewardTask = 1000;
    int numberOfMeetingsTask = 5;
    int numberOfHuntersRequiredTask = 3;
    double ratingRequiredTask = 4.5;
    List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);
    String taskStatus;

    Task task = new Task(
            po,
            descriptionTask,
            titleTask,
            deadlineTask,
            rewardTask,
            numberOfMeetingsTask,
            numberOfHuntersRequiredTask,
            ratingRequiredTask,
            tags
    );

    @Given("que o hunter tem a avaliação {double} e a Task tem a avaliação necessária {double} e o status da vaga é {string}")
    public void task_qualification(double hunterRating, double taskRating, String taskStatus) {
        hunter.setRating(hunterRating);
        task.setRatingRequired(taskRating);
        this.taskStatus = taskStatus;
        task.setStatus(taskStatus);
        po.addTask(task);
        tasksHunter.add(task);
    }

    @When("o hunter aplica para a Task")
    public void hunter_apply_to_task() {
            try {
                TaskService.applyHunterToTask(task, hunter);
                isHunterNotified = notificationService.Notify(hunter, task.getTitle(), "Você aplicou nessa Task! O PO foi notificado!");
                isPoNotified = notificationService.Notify(po, task.getTitle(), "Você recebeu uma aplicação nova nessa Task do usuário" + hunter.getName());
                System.out.println(isHunterNotified);
            } catch (Exception e) {
                exception = e;
            }
    }

    @Then("a aplicação {string}")
    public void task_applied(String taskApplied) {
        if (taskApplied.equals("não é enviada") && taskStatus.equals("closed")) {
            assertEquals(exception.getMessage(),"Cannot apply to task. The task is already closed.");
        } else if (taskApplied.equals("não é enviada") && taskStatus.equals("open")) {
            assertEquals(exception.getMessage(),"Cannot apply to task. Rating required: " + task.getRatingRequired() + ". Your rating " + hunter.getRating());
        } else {
            assertEquals(hunter, task.getHuntersApplied().get(task.getHuntersApplied().size()-1));
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

}
