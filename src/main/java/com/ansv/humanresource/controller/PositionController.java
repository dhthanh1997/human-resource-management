package com.ansv.humanresource.controller;


import com.ansv.humanresource.dto.response.PositionDTO;
import com.ansv.humanresource.dto.response.ResponseDataObject;
import com.ansv.humanresource.service.PositionService;
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
@RequestMapping("/v1/api/position")
public class PositionController extends BaseController {

    @Autowired
    private PositionService positionService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<PositionDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<PositionDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<PositionDTO> listDTO = positionService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<PositionDTO>> create(@RequestBody @Valid PositionDTO item) {
        ResponseDataObject<PositionDTO> response = new ResponseDataObject<>();
        PositionDTO dto = positionService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<PositionDTO>> update(@PathVariable(value = "id") Long id, @RequestBody @Valid PositionDTO item) {
        ResponseDataObject<PositionDTO> response = new ResponseDataObject<>();
        PositionDTO dto = positionService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<PositionDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<PositionDTO> response = new ResponseDataObject<>();
        PositionDTO dto = positionService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        positionService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = positionService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
