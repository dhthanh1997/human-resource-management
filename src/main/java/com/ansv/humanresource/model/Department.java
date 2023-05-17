package com.ansv.humanresource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.h2.engine.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class Department extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(1000)")
    private String name;

    @Column(name = "code", columnDefinition = "varchar(20)")
    private String code;

    @Column(name = "parent_code", columnDefinition = "varchar(20)")
    private String parentCode;

    @Column(name = "description", columnDefinition = "nvarchar(1000)")
    private String description;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();


}
