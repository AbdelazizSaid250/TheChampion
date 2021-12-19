package com.test.champion.service.winner;

import com.test.champion.model.Winner;

import java.util.List;

public interface WinnerService {
    void saveOrUpdate(Winner winner);

    List<Winner> findAll();
}
