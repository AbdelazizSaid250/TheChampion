package com.test.champion.dao;

import com.test.champion.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "report")
public interface ReportRepository extends JpaRepository<Report, String> {

}