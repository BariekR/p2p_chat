package com.greenfox.p2pchat.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {
    @Id
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String text;
    private long timestamp;

    public Message() {
        id = new Random().nextInt(8_999_999) + 1000000;
        timestamp = System.currentTimeMillis();
    }

    public Message(User user, String text) {
        this();
        this.user = user;
        this.text = text;
    }

    public Message(Integer id, User user, String text, long timestamp) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
    }
}
