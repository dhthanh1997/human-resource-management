package com.ansv.humanresource.dto.response;

import com.ansv.humanresource.model.Auditable;
import com.ansv.humanresource.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO extends BaseDTO<String> implements Serializable {
    private Long id;

    private String name;

    private String code;

    private String parentCode;

    private String description;

}
