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
import com.groupseven.hunthub.steps.builder.BasicHunterBuilder;
import com.groupseven.hunthub.steps.builder.BasicPoBuilder;
import com.groupseven.hunthub.steps.builder.BasicTaskBuilder;
import com.groupseven.hunthub.steps.director.HunterDirector;
import com.groupseven.hunthub.steps.director.PODirector;

import com.groupseven.hunthub.steps.director.TaskDirector;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

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




    @Before
    public void setUp() {

        isPoNotified = false;
        isHunterNotified = false;
        exception = null;


        BasicHunterBuilder hunterBuilder = new BasicHunterBuilder();
        HunterDirector hunterDirector = new HunterDirector(hunterBuilder);
        this.hunter = hunterDirector.getSpecificHunter("John");


        BasicPoBuilder poBuilder = new BasicPoBuilder();
        PODirector poDirector = new PODirector(poBuilder);
        poDirector.constructPO();
        this.po = poDirector.getPO();

        BasicTaskBuilder taskBuilder = new BasicTaskBuilder();
        TaskDirector taskDirector = new TaskDirector(taskBuilder);
        taskDirector.constructTask();
        task = taskDirector.getTask();
        task.setPO(po);
        task.setStatus(TaskStatus.PENDING);


        notificationService = new NotificationService(new NotificationRepositoryImpl());
        taskService = new TaskService(new TaskRepositoryImpl(), new PoRepositoryImpl(), notificationService);
    }

    @Given("que o hunter tem a avaliação {double} e a Task tem a avaliação necessária {double} e o status da vaga é {string}")
    public void task_qualification(double hunterRating, double taskRating, String taskStatusStr) {
        hunter.setRating(hunterRating);

        BasicTaskBuilder taskBuilder = new BasicTaskBuilder();
        TaskDirector taskDirector = new TaskDirector(taskBuilder);
        taskDirector.constructTask();
        task = taskDirector.getTask();
        task.setRatingRequired(taskRating);
        task.setStatus(TaskStatus.valueOf(taskStatusStr.toUpperCase()));
        task.setPO(po);
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

        BasicTaskBuilder taskBuilder = new BasicTaskBuilder();
        TaskDirector taskDirector = new TaskDirector(taskBuilder);
        taskDirector.constructTask();
        task = taskDirector.getTask();
        task.setPO(po);
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