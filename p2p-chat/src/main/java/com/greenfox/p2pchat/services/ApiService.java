package com.greenfox.p2pchat.services;

import com.greenfox.p2pchat.dtos.ReceiveDto;
import com.greenfox.p2pchat.models.Message;
import com.greenfox.p2pchat.models.User;
import com.greenfox.p2pchat.repositories.MessageRepository;
import com.greenfox.p2pchat.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public void receiveMessage(ReceiveDto receiveDto, String ipAddress) {
        User user = userRepository.save(new User(receiveDto.getClient().getId()));
        user.setIpAddress(ipAddress);
        Message message = messageRepository.save(new Message(
                receiveDto.getMessage().getId(),
                user,
                receiveDto.getMessage().getText(),
                receiveDto.getMessage().getTimestamp()));
    }

    public String validateReceivedMsg(ReceiveDto receiveDto) {
        if (isReceivedMsgValid(receiveDto)) {
            return null;
        }

        List<String> missingFields = new ArrayList<>();
        String missingFieldsIni = "Missing field(s): ";

        if (receiveDto.getMessage() == null && receiveDto.getClient() == null) {
            missingFields.add("message");
            missingFields.add("client");
            return missingFieldsIni + String.join(", ", missingFields);
        }

        if (receiveDto.getMessage() == null) {
            missingFields.add("message");
            return missingFieldsIni + String.join(", ", missingFields);
        }

        if (receiveDto.getClient() == null) {
            missingFields.add("client");
            return missingFieldsIni + String.join(", ", missingFields);
        }

        if (receiveDto.getMessage().getId() == null) {
            missingFields.add("message.id");
        }
        if (receiveDto.getMessage().getUsername() == null) {
            missingFields.add("message.username");
        }
        if (receiveDto.getMessage().getText() == null) {
            missingFields.add("message.text");
        }
        if (receiveDto.getMessage().getTimestamp() == null) {
            missingFields.add("message.timestamp");
        }
        if (receiveDto.getClient().getId() == null) {
            missingFields.add("client.id");
        }
        return missingFieldsIni + String.join(", ", missingFields);
    }

    private boolean isReceivedMsgValid(ReceiveDto receiveDto) {
        if (receiveDto.getMessage() == null || receiveDto.getClient() == null) {
            return false;
        }

        return !(receiveDto.getMessage().getId() == null ||
                receiveDto.getMessage().getUsername() == null ||
                receiveDto.getMessage().getText() == null ||
                receiveDto.getMessage().getTimestamp() == null ||
                receiveDto.getClient().getId() == null);
    }
}
