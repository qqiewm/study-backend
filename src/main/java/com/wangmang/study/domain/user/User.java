package com.wangmang.study.domain.user;

import com.wangmang.study.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_idx"),
            inverseJoinColumns = @JoinColumn(name = "role_idx"))
    private Set<Role> roles = new HashSet<>();


    @Builder
    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;

    }





}
