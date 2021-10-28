package com.teamwill.app.test.service.impl;

import com.teamwill.app.test.entity.Ranking;
import com.teamwill.app.test.repository.RankingRepository;
import com.teamwill.app.test.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository repository;

    @Override
    public List<Ranking> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Ranking> getAllOrdered() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "rank"));
    }

    @Override
    public List<Ranking> getTop5() {
        return repository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "rank"))).getContent();
    }

    @Secured("ROLE_ADMIN")
    @Override
    public Long createRanking(Ranking ranking) {
        Ranking saved = repository.save(ranking);
        return saved.getId();
    }
}
