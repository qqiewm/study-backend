package com.wangmang.study.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="role")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;


    public Role(Roles name) {
        this.name = name;
    }
}
