package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
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

    private final TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
    private final TaskService taskService = new TaskService(taskRepository);
    private Exception excecao;

    Long cpf = 12345678900L;
    String name = "John Doe";
    String email = "johndoe@example.com";
    String password = "password123";
    int levels = 5;
    int rating = 4;
    List<Task> tasks = new ArrayList<>(); // Lista vazia de tarefas para o mock
    String profilePicture = "https://example.com/profile/johndoe.jpg";
    String bio = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
    private final PO po = new PO(cpf, name, email, password, tasks, profilePicture, bio);
    Task novaTask;
    String description = "Desenvolver nova funcionalidade";
    String title = "Nova Funcionalidade";
    int po_initial_points;
    int reward;
    int numberOfMeetings = 2;
    int numberOfHuntersRequired = 1;
    int ratingRequired = 1;
    List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);


    @Given("que o PO possui a quantidade de pontos {int}")
    public void pontos_disponiveis(int pts_disponiveis) {
        po.setPoints(pts_disponiveis);
        po_initial_points = pts_disponiveis;
    }

    @When("o PO cria uma nova Task de {int} pontos")
    public void o_PO_cria_uma_nova_Task_com_os_detalhes(int pts_reward) {
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse("2024-10-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        reward = pts_reward;

        try {
            taskService.createTask(po, name, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired, tags);
            novaTask = po.getTasks().get(0);
            tasks.add(novaTask);
        } catch (Exception e) {
            this.excecao = e;
        }
    }

    @Then("a Task e criada com sucesso")
    public void task_criada() {
        Task task = po.getTasks().get(0);
        assertEquals(task, novaTask);
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
        assertEquals(lastTask, novaTask);
    }

    @And("o pagamento do valor da task e feito e retido no sistema ate a finalizacao da task")
    public void task_pagamento() {
        assertEquals(po_initial_points-novaTask.getReward(), po.getPoints());
    }
}
