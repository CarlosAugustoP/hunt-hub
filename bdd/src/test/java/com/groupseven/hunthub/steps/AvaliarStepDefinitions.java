package com.groupseven.hunthub.steps;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.TaskService;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.persistence.memoria.repository.TaskRepositoryImpl;
import com.groupseven.hunthub.domain.services.TaskService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.*;
import static org.mockito.Mockito.*;
public class AvaliarStepDefinitions {
    private TaskService taskService;
    private PO po;
    private Hunter hunter1;
    private Hunter hunter2;
    private Task task;
    private List<Hunter> hunters;

    public AvaliarStepDefinitions(){
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        this.taskService = new TaskService(taskRepository);
        Long cpfPO = 12345678900L;
        String namePO = "John Doe";
        String emailPO = "johndoe@example.com";
        String passwordPO = "password123";
        String profilePicturePO = "https://example.com/profile/johndoe.jpg";
        String bioPO = "PO experiente com foco em projetos.";
        this.po = new PO(cpfPO, namePO, emailPO, passwordPO, null, profilePicturePO, bioPO);
        Long cpfHunter = 98765432100L;
        String nameHunter = "Jessie Hunter";
        String emailHunter = "jessiehunter@example.com";
        String passwordHunter = "passwordhunter";
        int levelsHunter= 13;
        int ratingHunter = 4;
        int ratingCountHunter = 3;
        int totalRatingHunter = 12;
        String profilePicturehunter = "https://example.com/profile/jessie.jpg";
        String bioHunter = "Desenvolvedor experiente com paixão por criar soluções inovadoras.";
        this.hunter1 = new Hunter(cpfHunter, nameHunter, emailHunter, passwordHunter, null,null,bioHunter,profilePicturehunter,null,null,null,null);
        Long cpfHunter2 = 98765432101L; // Outro hunter para avaliação
        String nameHunter2 = "Alex Hunter";
        String emailHunter2 = "alexhunter@example.com";
        String passwordHunter2 = "passwordhunter2";
        int levelsHunter2 = 10;
        int ratingHunter2 = 3;
        int ratingCountHunter2 = 4;
        int totalRatingHunter2 = 10;
        String profilePictureHunter2 = "https://example.com/profile/alex.jpg";
        String bioHunter2 = "Caçador de soluções com experiência em diversos projetos.";
        this.hunter2 = new Hunter(cpfHunter2, nameHunter2, emailHunter2, passwordHunter2, null, null, bioHunter2, profilePictureHunter2, null, null, null, null);
        }//EXISTE FUNÇÃO DEFINIDA DE COMPLETAR A TASK QUE JÁ RESOLVERIA ISSO
        @Given("que a Task foi finalizada")
        public void task_completada_com_sucesso() throws ParseException{
            String title = "Complete Task";
            String description = "Task successfully completed by Hunter";
            Date deadline = new SimpleDateFormat("yyyy-MM-dd").parse("2024-10-31");
            int reward = 100;
            int numberOfMeetings = 5;
            int numberOfHuntersRequired = 2;
            double ratingRequired = 4.0;

            po.setPoints(600);
            task.setCompleted(true);
        }//APARENTEMENTE JÁ TEM UMA CLASSE ESPECIALIZADA DE NOTIFICAÇÃO
        @When("os users \\(PO e hunters) são notificados que a Task foi finalizada")
        public void notifyPOandHunters(){

        //SIMULANDO NOTIFICAÇÃO, ISSO DEVE SER ALTERADO FUTURAMENTE QUANDO FOR CRIADO A NOTIFICAÇÃO DE VERDADE
            System.out.println("Notificando o PO: " + po.getName() + " que a Task " + task.getTitle() + " foi finalizada.");
            for (Hunter hunter : hunters) {
                System.out.println("Notificando o Hunter: " + hunter.getName() + " que a Task " + task.getTitle() + " foi finalizada.");
            }
        }
        // CHECAR O JPA
        @Then("os hunters avaliam o PO e os outros hunters")
        public void hunterAvalia(){
            // Simular o serviço
            HunterService hunterService = mock(HunterService.class);

            int ratingForHunter2 = 4; // Avaliação do hunter1 para hunter2
            hunterService.rateHunter(hunter1, hunter2, ratingForHunter2);

            int ratingForPO = 5; // Avaliação do hunter1 para o PO
            hunterService.ratePO(po, ratingForPO);

            verify(hunterService).rateHunter(hunter1, hunter2, ratingForHunter2);
            verify(hunterService).ratePO(po, ratingForPO);

            assertEquals(ratingForHunter2, hunter2.getRating());
            assertEquals(ratingForPO, po.getRating());
        }
    @When("o PO avalia os hunters")
    public void poAvaliaHunters() {
        int ratingForHunter1 = 5;
        po.rateHunter(hunter1, ratingForHunter1);
    }
    @When("os hunters sobem sua avaliação com base no que foi avaliado no sistema")
    public void subirAvaliacaoHunter(){
        int ratingFromPO = 4;
        int ratingFromOtherHunter = 5;

        hunter1.addRating(ratingFromPO);
        hunter1.addRating(ratingFromOtherHunter);

        assertEquals(17, hunter1.getTotalRating()); // 12 + 4 + 5
        assertEquals(5, hunter1.getRatingCount());  // 3 + 2 (duas novas avaliações)

        assertEquals(3, hunter1.getRating());  // Nova média de avaliações (17/5 arredondado)
    }
    @When("o PO sobe sua avaliação com base no que foi avaliado no sistema")
    public void subirAvaliacaoPo(){
        int ratingFromHunter1 = 4;
        int ratingFromHunter2 = 5;
        po.addRating(ratingFromHunter1);
        po.addRating(ratingFromHunter2);
        assertEquals(29, po.getTotalRating());
        assertEquals(7, po.getRatingCount());
        assertEquals(4, po.getRating());
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
            hunter.setPoints(hunter.getPoints() + rewardPerHunter);  // Adiciona os pontos ao hunter
            System.out.println("Pagando " + rewardPerHunter + " pontos ao Hunter: " + hunter.getName());
        }

        // Valida se o pagamento foi liberado corretamente
        for (Hunter hunter : hunters) {
            assertEquals(hunter.getPoints(), rewardPerHunter);  // Verifica se os pontos foram adicionados corretamente
        }
    }
}
