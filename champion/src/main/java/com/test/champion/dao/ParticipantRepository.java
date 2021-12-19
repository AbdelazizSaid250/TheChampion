package com.test.champion.dao;

import com.test.champion.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "participant")
public interface ParticipantRepository extends JpaRepository<Participant, String> {
    @Query(value = "SELECT EXISTS(select id from the_champion.participant WHERE name = :name)", nativeQuery = true)
    int existsByName(@Param("name") String name);

    @Query(value = "SELECT *\n" +
            "FROM the_champion.participant\n" +
            "WHERE points = (SELECT MAX(points) from participant);", nativeQuery = true)
    Participant getWinner();


}