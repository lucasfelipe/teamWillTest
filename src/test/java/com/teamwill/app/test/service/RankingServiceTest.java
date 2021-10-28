package com.teamwill.app.test.service;

import com.teamwill.app.test.entity.Ranking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RankingServiceTest {

    @Autowired
    private RankingService service;

    @Test
    public void getAllRankings() {
        List<Ranking> all = service.getAll();
        assertThat(all).isNotNull();
        assertThat(all).allMatch(r -> r.getRank() > 40)
                       .isNotEmpty();
    }

    @Test
    public void getTop5() {
        List<Ranking> all = service.getTop5();
        assertThat(all).isNotNull();
        assertThat(all).element(0).extracting("name").isEqualTo("Janaina");
        assertThat(all).element(4).extracting("name").isEqualTo("Monica");
    }

}
