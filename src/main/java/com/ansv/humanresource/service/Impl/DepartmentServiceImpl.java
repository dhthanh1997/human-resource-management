package com.ansv.humanresource.service.Impl;

import com.ansv.humanresource.dto.criteria.SearchCriteria;
import com.ansv.humanresource.dto.mapper.BaseMapper;
import com.ansv.humanresource.dto.response.DepartmentDTO;
import com.ansv.humanresource.dto.specification.GenericSpecificationBuilder;
import com.ansv.humanresource.model.Department;
import com.ansv.humanresource.repository.DepartmentRepository;
import com.ansv.humanresource.service.DepartmentService;
import com.ansv.humanresource.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private static final BaseMapper<Department, DepartmentDTO> mapper = new BaseMapper<>(Department.class, DepartmentDTO.class);


    @Autowired
    private DepartmentRepository repository;

    @Override
    public DepartmentDTO findById(Long id) {
        if (DataUtils.notNull(id)) {
            Optional<Department> entity = repository.findById(id);
            if (entity.isPresent()) {
                return mapper.toDtoBean(entity.get());
            }
        }
        return null;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO item) {
        Department entity = null;
        DepartmentDTO dto = findById(item.getId());
        if (DataUtils.notNull(dto)) {
            item.setLastModifiedDate(LocalDateTime.now());
        }
        entity = mapper.toPersistenceBean(item);
        return mapper.toDtoBean(repository.save(entity));
    }

    @Override
    public List<DepartmentDTO> findAll() {
        List<Department> entities = repository.findAll();
        return mapper.toDtoBean(entities);
    }

    @Override
    public List<DepartmentDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public Page<DepartmentDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
        GenericSpecificationBuilder<Department> builder = new GenericSpecificationBuilder<>();

        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        builder.setClazz(Department.class);
        Specification<Department> spec = builder.build();
        Page<DepartmentDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            DepartmentDTO dto = mapper.toDtoBean(entity);
            return dto;
        });
        return listDTO;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Integer deleteByListId(List<Long> listId) {
        return repository.deleteByListId(listId);
    }
}
