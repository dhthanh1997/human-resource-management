package com.ansv.humanresource.service;

import com.ansv.humanresource.dto.response.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO findById(Long id);

    DepartmentDTO save(DepartmentDTO item);

    List<DepartmentDTO> findAll();

    List<DepartmentDTO> search(Map<String, Object> mapParam);

    Page<DepartmentDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);
}
