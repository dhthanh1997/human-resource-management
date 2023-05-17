package com.ansv.humanresource.controller;


import com.ansv.humanresource.dto.response.ResponseDataObject;
import com.ansv.humanresource.dto.response.DepartmentDTO;
import com.ansv.humanresource.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<DepartmentDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<DepartmentDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<DepartmentDTO> listDTO = departmentService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<DepartmentDTO>> create(@RequestBody @Valid DepartmentDTO item) {
        ResponseDataObject<DepartmentDTO> response = new ResponseDataObject<>();
        DepartmentDTO dto = departmentService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<DepartmentDTO>> update(@PathVariable(value = "id") Long id, @RequestBody @Valid DepartmentDTO item) {
        ResponseDataObject<DepartmentDTO> response = new ResponseDataObject<>();
        DepartmentDTO dto = departmentService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<DepartmentDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<DepartmentDTO> response = new ResponseDataObject<>();
        DepartmentDTO dto = departmentService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        departmentService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = departmentService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
