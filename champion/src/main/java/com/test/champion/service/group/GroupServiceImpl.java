package com.test.champion.service.group;

import com.test.champion.dao.GroupRepository;
import com.test.champion.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void saveOrUpdate(Group group) {
        groupRepository.save(group);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findByID(String id) {
        Optional<Group> result = groupRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            log.error("didn't find the Group in the server.");
            throw new RuntimeException("We didn't find the Group");
        }
    }

    @Override
    public List<Group.GroupParticipants> getGroupsParticipants() {
        return groupRepository.getGroupsParticipants();
    }
}
