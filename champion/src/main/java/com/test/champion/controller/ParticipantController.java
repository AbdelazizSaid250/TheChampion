package com.test.champion.controller;

import com.test.champion.model.Group;
import com.test.champion.model.Participant;
import com.test.champion.service.group.GroupService;
import com.test.champion.service.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/participant")
public class ParticipantController {
    private final ParticipantService participantService;
    private final GroupService groupService;

    @Autowired
    public ParticipantController(ParticipantService participantService, GroupService groupService) {
        this.participantService = participantService;
        this.groupService = groupService;
    }

    @PostMapping("save")
    public Participant save(@RequestBody Participant.Request request) {
        String id = UUID.randomUUID().toString();
        Participant participant = new Participant(id);
        participant.setName(request.getName());
        participant.setGender(request.getGender());
        participant.setPlayedMatches(0);
        participant.setWinMatches(0);
        participant.setLoseMatches(0);
        participant.setPoints(0);

        participantService.saveOrUpdate(participant);
        log.info("Saved Participant is: {}", participant);

        return participant;
    }

    @GetMapping("list")
    public List<Participant> list() {
        List<Participant> participants = participantService.findAll();
        log.info("All Participants are: {}", participants);

        return participants;
    }

    @GetMapping("distributeParticipants")
    public List<List<String>> distributeGroups() {
        int groupNum = 3;  // I can send this in the request but for now I will put it with a static data.
        List<String> participantIDs = new ArrayList<>();

        // Step 1: List all participant from the database and init the list with participant ids.
        participantService.findAll().forEach((participant -> participantIDs.add(participant.getName())));
        log.info("Participant IDs are: {}", participantIDs);

        // Step 2: Create the random groups from the retrieved list.
        Collections.shuffle(participantIDs);
        List<List<String>> groups = IntStream.range(0, participantIDs.size())
                .boxed()
                .collect(Collectors.groupingBy(i -> i % groupNum))
                .values()
                .stream()
                .map(il -> il.stream().map(participantIDs::get).collect(Collectors.toList()))
                .collect(Collectors.toList());

        // Step 3: Iterate over the groups and init the group table with the groups
        for (int i = 0; i < groups.size(); i++) {
            String groupID = UUID.randomUUID().toString();
            groupService.saveOrUpdate(new Group(groupID, "group" + (i + 1)));
            Group savedGroup = groupService.findByID(groupID);

            for (int j = 0; j < groups.get(i).size(); j++) {
                log.info("group id is: {}", groups.get(i).get(j));
                participantService.updateByIDs(groups.get(i).get(j), savedGroup.getId());
            }
        }
        return groups;
    }

    @GetMapping("winner")
    public Participant getWinner() {
        Participant winner = participantService.getWinner();
        log.info("Congratulation, the Winner is {}", winner);

        return winner;
    }

}