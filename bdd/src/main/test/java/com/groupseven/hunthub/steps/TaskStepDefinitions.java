package com.groupseven.hunthub.steps;

import com.groupseven.hunthub.domain.*;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.util.Map;
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

    @Given("que o PO possui a quantidade de pontos necessária (string) para criar uma nova Task (string)")
    public void pontos_disponiveis(int pts_disponiveis, int pts_reward){
        po.setPoints(pts_disponiveis);
    }

    @When("o PO cria uma nova Task com os detalhes: description \"{string}\"; title \"{string}\"; deadline \"{string}\"; reward {int}; numberOfMeetings {int}; numberOfHuntersRequired {int}")
    public void o_PO_cria_uma_nova_Task_com_os_detalhes(String description, String title, String deadlineString, int reward, int numberOfMeetings, int numberOfHuntersRequired) {
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineString);
        } catch (ParseException e) {
            e.printStackTrace(); // Trate a exceção de acordo com a necessidade do seu projeto
        }

        novaTask = new Task(description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired);
        po.addTask(novaTask);
    }

    @Then("a Task é criada com sucesso")
    public void task_criada() {
        Task task = po.getTasks().get(0);
        assertTrue(task.equals(novaTask))
    }

}
