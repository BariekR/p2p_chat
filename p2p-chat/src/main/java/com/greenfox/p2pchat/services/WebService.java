package com.greenfox.p2pchat.services;

import com.greenfox.p2pchat.models.Message;
import com.greenfox.p2pchat.models.User;
import com.greenfox.p2pchat.repositories.MessageRepository;
import com.greenfox.p2pchat.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WebService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public User saveUser(String userName) throws UnknownHostException {
        User user = new User(userName);
        user.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        return userRepository.save(user);
    }

    public void updateUserName(String userName, User user) {
        user.setUserName(userName);
        userRepository.save(user);
    }

    public void saveMessage(String messageText, User user) {
        messageRepository.save(new Message(user, messageText));
    }

    public List<Message> findMessages() {
        return messageRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
