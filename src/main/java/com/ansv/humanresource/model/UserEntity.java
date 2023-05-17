package com.ansv.humanresource.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", columnDefinition = "varchar(500)")
    private String username;

    @Column(name = "email", columnDefinition = "varchar(500)")
    private String email;

    @Column(name = "phone", columnDefinition = "varchar(20)")
    private String phone;

    @Column(name = "fullname", columnDefinition = "nvarchar(500)")
    private String fullname;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_department", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "department_id", referencedColumnName = "id")
            }
    )
    private Set<Department> departments = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_position", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "position_id", referencedColumnName = "id")
            }
    )
    private Set<Position> positions = new HashSet<>();


}
