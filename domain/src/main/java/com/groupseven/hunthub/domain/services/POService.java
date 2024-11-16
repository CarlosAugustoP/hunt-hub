package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.PoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class POService {
    private final PoRepository poRepository;
    private final PasswordEncoder passwordEncoder;

    public POService(PoRepository poRepository) {
        this.poRepository = poRepository;
        this.passwordEncoder = null;
    }

    @Autowired
    public POService(PoRepository poRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.poRepository = poRepository;
    }

    public void ratePO(PO po, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("The rating must be between 1 and 5.");
        }

        po.addRating(rating);

        poRepository.save(po);
    }

    public void rateHunter(Hunter hunter, int rating) {
        if (hunter == null) {
            throw new IllegalArgumentException("Hunter não pode ser nulo.");
        }
        if (rating < 1 || rating > 5) { // Exemplo de validação do rating
            throw new IllegalArgumentException("A classificação deve estar entre 1 e 5.");
        }

        hunter.rate(rating);
    }

    public List<PO> getAllPOs() {
        return poRepository.findAll();
    }

    public PO findPOById(UUID id) {
        return poRepository.findById(id);
    }

    public PO createPO(PO po) {
        assert passwordEncoder != null;
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        poRepository.save(po);
        return po;
    }

    @Transactional
    public PO updatePO(UUID id, PO updatedPoData) {
        PO existingPo = poRepository.findById(id);

        if (existingPo == null) {
            throw new IllegalArgumentException("PO com o ID " + id + " não encontrado.");
        }

        if (updatedPoData.getBio() != null) {
            existingPo.setBio(updatedPoData.getBio());
        }

        if (updatedPoData.getProfilePicture() != null) {
            existingPo.setProfilePicture(updatedPoData.getProfilePicture());
        }

        if (updatedPoData.getPoints() != 0) {existingPo.setPoints(updatedPoData.getPoints());}
        if (updatedPoData.getRating() != 0) {existingPo.setRating(updatedPoData.getRating());}

        poRepository.save(existingPo);

        return existingPo;
    }

    public void deletePO(UUID id) {
        poRepository.delete(id);
    }
}
