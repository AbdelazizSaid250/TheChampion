package com.test.champion.service.report;

import com.test.champion.model.Report;

import java.util.List;

public interface ReportService {
    void saveOrUpdate(Report report);

    List<Report> findAll();
}
