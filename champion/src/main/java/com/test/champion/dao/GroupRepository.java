package com.test.champion.dao;

import com.test.champion.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "group")
public interface GroupRepository extends JpaRepository<Group, String> {
    @Query(value = "SELECT * FROM the_champion.participant WHERE id = :id", nativeQuery = true)
    Group findByID(@Param("id") String name);

    @Query(value = "SELECT group_id, GROUP_CONCAT(id) as participant_ids\n" +
            "FROM the_champion.participant\n" +
            "GROUP BY group_id;", nativeQuery = true)
    List<Group.GroupParticipants> getGroupsParticipants();


}