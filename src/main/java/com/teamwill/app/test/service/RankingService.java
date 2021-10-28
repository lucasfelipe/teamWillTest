package com.teamwill.app.test.service;

import com.teamwill.app.test.entity.Ranking;

import java.util.List;

public interface RankingService {

    List<Ranking> getAll();

    List<Ranking> getAllOrdered();

    List<Ranking> getTop5();

    Long createRanking(Ranking ranking);
}
