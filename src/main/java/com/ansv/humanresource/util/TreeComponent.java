package com.ansv.humanresource.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeComponent {
    private Long id;

    private String name;

    private String code;

    private String parentCode;

    private String description;

    private Integer type;

    private Integer depth;

    private List<TreeComponent> children;
}
