package com.test.champion.controller;

import com.test.champion.model.Group;
import com.test.champion.service.group.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("save")
    public Group save(@RequestBody Group.Request request) {
        String id = UUID.randomUUID().toString();
        Group group = new Group(id, request.getName());

        groupService.saveOrUpdate(group);
        log.info("Saved Group is: {}", group);

        return group;
    }
}