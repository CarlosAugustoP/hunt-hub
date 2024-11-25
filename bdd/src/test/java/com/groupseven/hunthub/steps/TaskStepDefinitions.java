package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.PoRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import com.groupseven.hunthub.steps.builder.BasicPoBuilder;
import com.groupseven.hunthub.steps.builder.BasicTaskBuilder;
import com.groupseven.hunthub.steps.director.PODirector;
import com.groupseven.hunthub.steps.director.TaskDirector;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TaskStepDefinitions {

    private final PoRepository poRepository = new PoRepositoryImpl();
    private final POService poService = new POService(poRepository);
    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
    private final NotificationService notificationService = new NotificationService(new NotificationRepositoryImpl());
    private final TaskService taskService = new TaskService(taskRepository, poRepository, notificationService);

    private Exception excecao;
    private PO po;
    private Task task;
    private List<Task> tasks;
    private int po_initial_points;

    private final String description = "Desenvolver nova funcionalidade";
    private final String title = "Nova Funcionalidade";
    private final int numberOfMeetings = 2;
    private final int numberOfHuntersRequired = 1;
    private final int ratingRequired = 1;
    private final List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);
    private int reward;

    @Before
    public void setUp() {
        excecao = null;
        tasks = new ArrayList<>();

        BasicPoBuilder poBuilder = new BasicPoBuilder();
        PODirector poDirector = new PODirector(poBuilder);
        poDirector.constructPO();
        po = poDirector.getPO();
        po.setTasks(tasks);

        BasicTaskBuilder taskBuilder = new BasicTaskBuilder();
        TaskDirector taskDirector = new TaskDirector(taskBuilder);
        taskDirector.constructTask();
        task = taskDirector.getTask();
        task.setPO(po);
        task.setStatus(TaskStatus.PENDING);
    }

    @Given("que o PO possui a quantidade de pontos {int}")
    public void pontos_disponiveis(int pts_disponiveis) {
        po.setPoints(pts_disponiveis);
        po_initial_points = pts_disponiveis;
        poRepository.save(po);
    }

    @When("o PO cria uma nova Task de {int} pontos")
    public void o_PO_cria_uma_nova_Task_com_os_detalhes(int pts_reward) {
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse("2024-10-01");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        reward = pts_reward;

        try {
            task = taskService.createTask(po.getId().getId(), description, title, deadline, reward,
                    numberOfMeetings, numberOfHuntersRequired, ratingRequired, tags);
            tasks.add(task);
        }
        catch (Exception e) {
            this.excecao = e;
        }
    }

    @Then("a Task e criada com sucesso")
    public void task_criada() {
        Task createdTask = po.getTasks().get(0);
        assertEquals(createdTask, task);
    }

    @Then("o sistema não deixa o PO criar a Task")
    public void task_not_created() {
        if (this.excecao != null) {
            String exceptionExpected = "Not enough points";
            assertEquals(this.excecao.getMessage(), exceptionExpected);
        } else {
            System.out.println("Nenhuma exceção foi capturada.");
            fail("Nenhuma exceção foi lançada, mas era esperado que fosse.");
        }
    }

    @And("a Task aparece no sistema para os hunters")
    public void task_aparece() {
        Task lastTask = tasks.get(tasks.size() - 1);
        assertEquals(lastTask, task);
    }

    @And("o pagamento do valor da task e feito e retido no sistema ate a finalizacao da task")
    public void task_pagamento() {
        assertEquals(po_initial_points - task.getReward(), po.getPoints());
    }
}