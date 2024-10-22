package com.groupseven.hunthub.steps;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.services.NotificationService;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.persistence.memoria.repository.HunterRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.NotificationRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import com.groupseven.hunthub.persistence.memoria.repository.PoRepositoryImpl;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.aspectj.bridge.IMessage;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//import static org.mockito.Mockito.*;
public class AvaliarStepDefinitions {

    private TaskService taskService;
    private final NotificationService notificationService = new NotificationService(new NotificationRepositoryImpl());
    private final PoRepository poRepository = new PoRepositoryImpl();
    private final HunterService hunterService = new HunterService(new HunterRepositoryImpl(), poRepository );
    private final POService poService = new POService(poRepository);
    private PO po;
    private Hunter hunter1;
    private Hunter hunter2;
    private final Task task;
    List<Tags> tags = Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST);

    List<Hunter> hunters;

    public AvaliarStepDefinitions(){
        this.hunters = new ArrayList<>();
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        this.taskService = new TaskService(taskRepository);
        Long cpfPO = 12345678900L;
        String namePO = "John Doe";
        String emailPO = "johndoe@example.com";
        String passwordPO = "password123";
        String profilePicturePO = "https://example.com/profile/johndoe.jpg";
        String bioPO = "PO experiente com foco em projetos.";
        this.po = new PO(cpfPO, namePO, emailPO, passwordPO, null, profilePicturePO, bioPO);
        task = new Task(po, "qualquer descricao", "qualquer titulo", new Date(), 3000, 10,
        10, 3.5, tags, new TaskId(UUID.randomUUID()));

        Long cpfHunter = 98765432100L;
        String nameHunter = "Jessie Hunter";
        String emailHunter = "jessiehunter@example.com";
        String passwordHunter = "passwordhunter";


        String profilePicturehunter = "https://example.com/profile/jessie.jpg";
        String bioHunter = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
        this.hunter1 = new Hunter(cpfHunter, nameHunter, emailHunter, passwordHunter, null,null,bioHunter,profilePicturehunter,null,null,null,null);
        Long cpfHunter2 = 98765432101L;
        String nameHunter2 = "Alex Hunter";
        String emailHunter2 = "alexhunter@example.com";
        String passwordHunter2 = "passwordhunter2";


        String profilePictureHunter2 = "https://example.com/profile/alex.jpg";
        String bioHunter2 = "Caçador de soluções com experiência em diversos projetos.";
        this.hunter2 = new Hunter(cpfHunter2, nameHunter2, emailHunter2, passwordHunter2, null, null, bioHunter2, profilePictureHunter2, null, null, null, null);
        this.hunters.add(hunter1);
        this.hunters.add(hunter2);
        }

        @Given("que a Task foi finalizada")
        public void task_completada_com_sucesso(){
            task.setCompleted(true);
        }
        @When("os users \\(PO e hunters) são notificados que a Task foi finalizada")
        public void notifyPOandHunters(){

            var response = notificationService.Notify(this.task.getPo(), this.task.getTitle(), "Task finalizada");
            assertTrue(response);
            for (Hunter hunter: hunters) {
                var responseHunter = notificationService.Notify(hunter, this.task.getTitle(), "Task finalizada");
                assertTrue(responseHunter);
            }
        }

        @Then("os hunters avaliam o PO e os outros hunters")
        public void hunterAvalia(){

            int ratingForHunter1 = 4;
            int ratingForHunter2 = 4;
            int ratingForPO = 5;

            hunterService.rateHunter(hunter1, hunter2, ratingForHunter2);
            hunterService.ratePO(po, ratingForPO);

            hunterService.rateHunter(hunter2, hunter1, ratingForHunter1);
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
                System.out.println("Pagando " + rewardPerHunter + " pontos ao Hunter: " + hunter.getName());
            }
            for (Hunter hunter : hunters) {
                assertEquals(hunter.getPoints(), rewardPerHunter);
            }
        }
}
