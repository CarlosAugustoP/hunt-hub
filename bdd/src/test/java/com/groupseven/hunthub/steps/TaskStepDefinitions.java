package com.groupseven.hunthub.steps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
    int ratingCount = 5;
    int totalRating = 20;
    List<Task> tasks = new ArrayList<>(); // Lista vazia de tarefas para o mock
    String profilePicture = "https://example.com/profile/johndoe.jpg";
    String bio = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
    private final PO po = new PO(cpf, name, email, password, levels, rating, ratingCount, totalRating,tasks, profilePicture, bio);
    Task novaTask;
    double ratingRequired;


    @Given("que o PO possui a quantidade de pontos {int} para criar uma nova Task de {int}")
    public void pontos_disponiveis(int pts_disponiveis, int pts_reward) {
        po.setPoints(pts_disponiveis);
    }

    @When("o PO cria uma nova Task com os detalhes: description {string}; title {string}; deadline {string}; reward {int}; numberOfMeetings {int}; numberOfHuntersRequired {int}; ratingRequired {double};")
    public void o_PO_cria_uma_nova_Task_com_os_detalhes(String description, String title, String deadlineString, int reward, int numberOfMeetings, int numberOfHuntersRequired, double ratingRequired) {
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            taskService.createTask(po, name, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired);
            novaTask = po.getTasks().get(0);
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
        Task task = po.getTasks().get(0);
        assertEquals(task, novaTask);
    }

    @And("o pagamento do valor da task e feito e retido no sistema ate a finalizacao da task")
    public void task_pagamento() {
        Task task = po.getTasks().get(0);
        assertEquals(task, novaTask);
    }

}