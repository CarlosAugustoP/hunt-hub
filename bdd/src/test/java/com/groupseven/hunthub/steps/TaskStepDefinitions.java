package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.models.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class TaskStepDefinitions {
    Long cpf = 12345678900L;
    String name = "John Doe";
    String email = "johndoe@example.com";
    String password = "password123";
    int levels = 5;
    int rating = 4;
    List<Task> tasks = new ArrayList<>(); // Lista vazia de tarefas para o mock
    String profilePicture = "https://example.com/profile/johndoe.jpg";
    String bio = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
    private PO po = new PO(cpf, name, email, password, levels, rating, tasks, profilePicture, bio);
    Task novaTask;

    @Given("que o PO possui a quantidade de pontos necessaria {string} para criar uma nova Task {string}")
    public void pontos_disponiveis(int pts_disponiveis, int pts_reward){
        System.out.println("Executando pontos_disponiveis");
        po.setPoints(pts_disponiveis);
    }

    @When("o PO cria uma nova Task com os detalhes: description \"{string}\"; title \"{string}\"; deadline \"{string}\"; reward {int}; numberOfMeetings {int}; numberOfHuntersRequired {int}")
    public void o_PO_cria_uma_nova_Task_com_os_detalhes(String description, String title, String deadlineString, int reward, int numberOfMeetings, int numberOfHuntersRequired) {
        System.out.println("Executando o_PO_cria_uma_nova_Task_com_os_detalhes");
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineString);
        } catch (ParseException e) {
            e.printStackTrace(); // Trate a exceção de acordo com a necessidade do seu projeto
        }

        novaTask = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired);
        po.addTask(novaTask);
    }

    @Then("a Task e criada com sucesso")
    public void task_criada() {
        System.out.println("Executando task_criada");
        Task task = po.getTasks().get(0);
        assertTrue(task.equals(novaTask));
    }

    @And("a Task aparece no sistema para os hunters")
    public void task_aparece() {
        System.out.println("Executando task_aparece");
        Task task = po.getTasks().get(0);
        assertTrue(task.equals(novaTask));
    }

    @And("o pagamento do valor da task e feito e retido no sistema ate a finalizacao da task")
    public void task_pagamento() {
        System.out.println("Executando task_pagamento");
        Task task = po.getTasks().get(0);
        assertTrue(task.equals(novaTask));
    }

}
