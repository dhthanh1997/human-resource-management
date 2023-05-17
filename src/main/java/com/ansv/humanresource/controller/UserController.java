package com.ansv.humanresource.controller;


import com.ansv.humanresource.service.UserService;
import com.ansv.humanresource.dto.response.ResponseDataObject;
import com.ansv.humanresource.dto.response.UserDTO;
import com.ansv.humanresource.service.UserService;
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
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<UserDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<UserDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<UserDTO> listDTO = userService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<UserDTO>> create(@RequestBody @Valid UserDTO item) {
        ResponseDataObject<UserDTO> response = new ResponseDataObject<>();
        UserDTO dto = userService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<UserDTO>> update(@PathVariable(value = "id") Long id, @RequestBody @Valid UserDTO item) {
        ResponseDataObject<UserDTO> response = new ResponseDataObject<>();
        UserDTO dto = userService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<UserDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<UserDTO> response = new ResponseDataObject<>();
        UserDTO dto = userService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        userService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = userService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
