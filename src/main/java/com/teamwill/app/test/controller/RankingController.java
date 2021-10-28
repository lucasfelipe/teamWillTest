package com.teamwill.app.test.controller;

import com.teamwill.app.test.controller.dto.RankingDTO;
import com.teamwill.app.test.controller.mapper.RankingMapper;
import com.teamwill.app.test.entity.Ranking;
import com.teamwill.app.test.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@Transactional
@RestController
@RequestMapping(API.RANKINGS)
@RequiredArgsConstructor
public class RankingController {

    private final RankingMapper mapper;
    private final RankingService service;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> createRanking(@Valid @RequestBody RankingDTO dto) {
        Ranking ranking = mapper.fromDTOtoEntity(dto);
        Long id = service.createRanking(ranking);
        return created(URI.create(API.RANKINGS + "/" + id)).build();
    }

    @GetMapping
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ResponseEntity<List<RankingDTO>> all() {
        List<RankingDTO> all = service.getAllOrdered().stream().map(mapper::fromEntityToDTO).collect(Collectors.toList());
        return ok(all);
    }

}
