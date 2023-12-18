package com.greenfox.p2pchat.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String ipAddress;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Message> messages;

    public User(String userName) {
        this.userName = userName;
    }
}
