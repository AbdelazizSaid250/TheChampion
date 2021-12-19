package com.test.champion.service.winner;

import com.test.champion.dao.WinnerRepository;
import com.test.champion.model.Winner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WinnerServiceImpl implements WinnerService {
    private final WinnerRepository winnerRepository;

    @Autowired
    public WinnerServiceImpl(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    @Override
    public void saveOrUpdate(Winner winner) {
        winnerRepository.save(winner);
    }

    @Override
    public List<Winner> findAll() {
        return winnerRepository.findAll();
    }
}
