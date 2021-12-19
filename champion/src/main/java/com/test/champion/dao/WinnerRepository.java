package com.test.champion.dao;

import com.test.champion.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "winner")
public interface WinnerRepository extends JpaRepository<Winner, String> {

}