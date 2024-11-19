package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskStatus;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HunterService {

    @Autowired
    private HunterRepository hunterRepository;

    @Autowired
    private PoRepository poRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HunterService(HunterRepository hunterRepository, PoRepository poRepository) {
        this.hunterRepository = hunterRepository;
        this.poRepository = poRepository;
        this.passwordEncoder = null;
    }

    public List<Hunter> getAllHunters() {
        return hunterRepository.findAll();
    }

    public Hunter findHunterById(UUID id) {
        return hunterRepository.findById(id);
    }

    public void rateHunter(Hunter evaluator, Hunter target, int rating) {
        if (evaluator.equals(target)) {
            throw new IllegalArgumentException("A avaliação não pode ser feita a si mesmo.");
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("A avaliação deve estar entre 1 e 5.");
        }
        target.addRating(rating);
        hunterRepository.save(target);
    }

    public void ratePO(PO po, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("The rating must be between 1 and 5");
        }
        po.addRating(rating);
        poRepository.save(po);

    }

    public Hunter createHunter(Hunter hunter) {
        assert passwordEncoder != null;
        hunter.setPassword(passwordEncoder.encode(hunter.getPassword()));
        hunterRepository.save(hunter);
        return hunter;
    }

    @Transactional
    public Hunter updateHunter(UUID id, Hunter updatedHunterData) {
        Hunter existingHunter = hunterRepository.findById(id);
        if (existingHunter == null) {
            throw new EntityNotFoundException("Hunter com ID " + id + " não encontrado.");
        }

        updatedHunterData.setId(existingHunter.getId().getId());
        updatedHunterData.setEmail(existingHunter.getEmail());
        updatedHunterData.setCpf(existingHunter.getCpf());
        updatedHunterData.setPassword(existingHunter.getPassword());
        updatedHunterData.setId(id);
        existingHunter.setPoints(updatedHunterData.getPoints());


        System.out.println("OLA CHEGUEI AQUI" + updatedHunterData.getPoints());



        if (updatedHunterData.getBio() != null) {
            existingHunter.setBio(updatedHunterData.getBio());
        }
        if (updatedHunterData.getProfilePicture() != null) {
            existingHunter.setProfilePicture(updatedHunterData.getProfilePicture());
        }

        if (updatedHunterData.getLinkPortfolio() != null) {
            existingHunter.setLinkPortfolio(updatedHunterData.getLinkPortfolio());
        }

        if (updatedHunterData.getCertifications() != null) {
            existingHunter.setCertifications(new ArrayList<>(updatedHunterData.getCertifications()));
        }

        if (updatedHunterData.getLinks() != null) {
            existingHunter.setLinks(new ArrayList<>(updatedHunterData.getLinks()));
        }

        hunterRepository.save(existingHunter);

        return existingHunter;
    }

    public void deleteHunter(Hunter hunter) {
        hunterRepository.delete(hunter);
    }
    public void save (Hunter hunter){
        hunterRepository.save(hunter);
    }

    @Transactional
    public Hunter payTheHunter (UUID hunterID, UUID taskID) {
        Hunter foundHunter = hunterRepository.findById(hunterID);
        Task foundTask = taskRepository.findById(taskID);
        if (foundHunter == null) {
            System.out.println("The hunter with ID " + hunterID + " doesn't exist.");
            return null;
        }

        if (foundTask == null) {
            System.out.println("The task with ID " + taskID + " doesn't exist.");
        }

        if (!(foundTask.getStatus() == TaskStatus.DONE)){
            System.out.println("The task with ID " + taskID + " has status " + foundTask.getStatus());
        }

        int payment = foundTask.getReward()/foundTask.getHunters().size();
        int totalPayload = foundHunter.getPoints() + payment;
        foundHunter.setPoints(totalPayload);
        hunterRepository.save(foundHunter);
        return foundHunter;
    }


}
