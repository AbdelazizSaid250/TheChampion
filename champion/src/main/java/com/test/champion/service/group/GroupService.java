package com.test.champion.service.group;

import com.test.champion.model.Group;

import java.util.List;

public interface GroupService {
    void saveOrUpdate(Group group);

    List<Group> findAll();

    Group findByID(String id);

    List<Group.GroupParticipants> getGroupsParticipants();
}
