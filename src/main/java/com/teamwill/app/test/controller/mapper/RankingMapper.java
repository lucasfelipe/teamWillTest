package com.teamwill.app.test.controller.mapper;

import com.teamwill.app.test.controller.dto.RankingDTO;
import com.teamwill.app.test.entity.Ranking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RankingMapper {
    @Mapping(target = "id", ignore = true)
    Ranking fromDTOtoEntity(RankingDTO dto);

    RankingDTO fromEntityToDTO(Ranking entity);
}
