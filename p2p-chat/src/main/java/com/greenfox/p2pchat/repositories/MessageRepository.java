package com.greenfox.p2pchat.repositories;

import com.greenfox.p2pchat.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
