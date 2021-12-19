package com.test.champion.controller;

import com.test.champion.model.Group;
import com.test.champion.model.Participant;
import com.test.champion.model.Report;
import com.test.champion.model.Winner;
import com.test.champion.service.group.GroupService;
import com.test.champion.service.participant.ParticipantService;
import com.test.champion.service.report.ReportService;
import com.test.champion.service.winner.WinnerService;
import com.test.champion.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    private final GroupService groupService;
    private final ParticipantService participantService;
    private final ReportService reportService;
    private final WinnerService winnerService;

    @Autowired
    public CompetitionController(
            GroupService groupService,
            ParticipantService participantService,
            ReportService reportService,
            WinnerService winnerService) {
        this.groupService = groupService;
        this.participantService = participantService;
        this.reportService = reportService;
        this.winnerService = winnerService;
    }

    @PutMapping("firstRound")
    public void startFirstRound() {
        // Step 1: Get the list of participants related to each group
        List<Group.GroupParticipants> groupsParticipants = groupService.getGroupsParticipants();
        // Step 2: Iterate over the groups to make participates play against each other.
        groupsParticipants.forEach((groupParticipants -> {
            List<String> participantIDs = groupParticipants.getParticipantIDs();
            for (int i = 0; i < participantIDs.size(); i++) {
                for (int j = i + 1; j < participantIDs.size(); j++) {
                    // get the two participants from the db by id.
                    String firstParticipantID = participantIDs.get(i);
                    String secondParticipantID = participantIDs.get(j);

                    // choose the winner from the loser and save them into the db.
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        Participant winner = participantService.findByID(firstParticipantID);
                        winner.setPlayedMatches(winner.getPlayedMatches() + Utils.POINT);
                        winner.setWinMatches(winner.getWinMatches() + Utils.POINT);
                        winner.setPoints(winner.getPoints() + Utils.THREE_POINT);

                        participantService.saveOrUpdate(winner);
                    } else {
                        Participant loser = participantService.findByID(secondParticipantID);
                        loser.setLoseMatches(loser.getLoseMatches() + Utils.POINT);

                        participantService.saveOrUpdate(loser);
                    }

                    // Update the report into the db
                    Report report = new Report(UUID.randomUUID().toString());
                    report.setPlayedMatches(Utils.PLAYED_MATCHES++);

                    reportService.saveOrUpdate(report);
                }
            }
        }));
    }

    @PutMapping("calculateFirstRound")
    public void chooseFirstRoundWinners() {
        // List all participants from the database
        List<Participant> participants = participantService.findAll();

        // Group the participants by the group ids.
        Map<String, List<Participant>> participantsByGroupID =
                participants.stream().collect(Collectors.groupingBy(Participant::getGroupID));

        // Iterate of the participants of each group
        for (List<Participant> groupParticipants : participantsByGroupID.values()) {
            // Extract the participant who has the highest points.
            Participant winnerParticipant = groupParticipants.stream().max(Comparator.comparing(Participant::getPoints)).get();

            // Init the winner object and save it into the db.
            Winner winner = new Winner(UUID.randomUUID().toString());
            winner.setParticipantName(winnerParticipant.getName());
            winner.setPoints(winnerParticipant.getPoints());
            winner.setParticipants(Collections.singletonList(winnerParticipant));
            winnerService.saveOrUpdate(winner);
        }
    }

    @PutMapping("secondRound")
    public void startSecondRound() {
        List<Winner> winners = winnerService.findAll();
        for (int i = 0; i < winners.size(); i++) {
            for (int j = i + 1; j < winners.size(); j++) {
                // get the two participants from the db by id.
                Participant firstParticipant = winners.get(i).getParticipants().get(0);
                Participant secondParticipant = winners.get(j).getParticipants().get(0);

                // choose the winner from the loser.
                if (new Random().nextBoolean()) {
                    firstParticipant.setPlayedMatches(firstParticipant.getPlayedMatches() + Utils.POINT);
                    firstParticipant.setWinMatches(firstParticipant.getWinMatches() + Utils.POINT);
                    firstParticipant.setPoints(firstParticipant.getPoints() + Utils.THREE_POINT);

                    participantService.saveOrUpdate(firstParticipant);
                } else {
                    secondParticipant.setLoseMatches(secondParticipant.getLoseMatches() + Utils.POINT);
                    participantService.saveOrUpdate(secondParticipant);
                }

                // Update the report into the db
                Report report = new Report(UUID.randomUUID().toString());
                report.setPlayedMatches(Utils.PLAYED_MATCHES++);

                reportService.saveOrUpdate(report);
            }
        }
    }
}

