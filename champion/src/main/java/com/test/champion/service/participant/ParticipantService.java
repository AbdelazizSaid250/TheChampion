package com.test.champion.service.participant;

import com.test.champion.model.Participant;

import java.util.List;

public interface ParticipantService {
    void saveOrUpdate(Participant participant);

    void updateByIDs(String id, String group_id);

    int existsByName(String name);

    Participant findByID(String name);

    List<Participant> findAll();

    Participant getWinner();
}
