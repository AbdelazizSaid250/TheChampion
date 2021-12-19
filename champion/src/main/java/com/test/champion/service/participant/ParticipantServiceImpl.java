package com.test.champion.service.participant;

import com.test.champion.dao.ParticipantRepository;
import com.test.champion.model.Participant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public void saveOrUpdate(Participant participant) {
        participantRepository.save(participant);
    }

    @Override
    public void updateByIDs(String id, String group_id) {
        Optional<Participant> result = participantRepository.findById(id);

        if (result.isPresent()) {
            Participant participant = result.get();
            participant.setGroupID(group_id);
            participantRepository.save(participant);
        } else {
            log.error("didn't find the Participant in the server.");
            throw new RuntimeException("We didn't find the Participant");
        }
    }

    @Override
    public int existsByName(String name) {
        return participantRepository.existsByName(name);
    }

    @Override
    public Participant findByID(String id) {
        Optional<Participant> result = participantRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            log.error("didn't find the Participant in the server.");
            throw new RuntimeException("We didn't find the Participant");
        }
    }

    @Override
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    @Override
    public Participant getWinner() {
        return participantRepository.getWinner();
    }
}
