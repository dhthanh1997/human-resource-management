package com.ansv.humanresource.service;

import com.ansv.humanresource.dto.response.PositionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PositionService {
    PositionDTO findById(Long id);

    PositionDTO save(PositionDTO item);

    List<PositionDTO> findAll();

    List<PositionDTO> search(Map<String, Object> mapParam);

    Page<PositionDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);
}
