package com.ansv.humanresource.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO<String> {
    private Long id;

    private String username;

    private String code;

    private String fullName;

    private String email;

    private String phone_number;

    private String position;

    private String note;

    private Long roleId;

    private Long departmentId;

    private String password;

    private String typeRequest;

}
