package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
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

    private final HunterRepository hunterRepository;
    private final PoRepository poRepository;
    private final PasswordEncoder passwordEncoder;

    public HunterService(HunterRepository hunterRepository, PoRepository poRepository) {
        this.hunterRepository = hunterRepository;
        this.poRepository = poRepository;
        this.passwordEncoder = null;
    }

    @Autowired
    public HunterService(HunterRepository hunterRepository, PoRepository poRepository, PasswordEncoder passwordEncoder) {
        this.hunterRepository = hunterRepository;
        this.poRepository = poRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void createHunter(Hunter hunter) {
        hunter.setPassword(passwordEncoder.encode(hunter.getPassword()));
        System.out.println("Hunter: " + hunter);
        System.out.println("Senha: " + hunter.getPassword());
        hunterRepository.save(hunter);
    }

    @Transactional
    public Hunter updateHunter(UUID id, Hunter updatedHunterData) {
        Hunter existingHunter = hunterRepository.findById(id);
        if (existingHunter == null) {
            throw new EntityNotFoundException("Hunter com ID " + id + " não encontrado.");
        }

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

        System.out.println("Updated hunter: " + existingHunter);
        System.out.println("Updated hunter: " + existingHunter.getId().getId());
        System.out.println("Updated hunter: " + existingHunter.getBio());
        System.out.println("Updated hunter: " + existingHunter.getProfilePicture());
        System.out.println("Updated hunter: " + existingHunter.getLinkPortfolio());
        System.out.println("Updated hunter: " + existingHunter.getCertifications());
        System.out.println("Updated hunter: " + existingHunter.getLinks());
        System.out.println("Updated hunter: " + existingHunter.getRating());
        System.out.println("Updated hunter: " + existingHunter.getRatingCount());
        System.out.println("Updated hunter: " + existingHunter.getTotalRating());
        System.out.println("Updated hunter: " + existingHunter.getLevel());
        System.out.println("Nome: " + existingHunter.getName());
        System.out.println("Email: " + existingHunter.getEmail());
        System.out.println("CPF: " + existingHunter.getCpf());
        System.out.println("Pontos: " + existingHunter.getPoints());
        System.out.println("Tasks: " + existingHunter.getTasks());

        hunterRepository.save(existingHunter);

        return existingHunter;
    }

    public void deleteHunter(Hunter hunter) {
        hunterRepository.delete(hunter);
    }
}
