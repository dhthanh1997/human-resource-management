package com.ansv.humanresource.repository;

import com.ansv.humanresource.model.Department;
import com.ansv.humanresource.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department>, DepartmentRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM department WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

    Optional<Department> findByCode(String code);

}
