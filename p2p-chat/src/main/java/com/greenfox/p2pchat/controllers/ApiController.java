package com.greenfox.p2pchat.controllers;

import com.greenfox.p2pchat.dtos.ErrorMsgDto;
import com.greenfox.p2pchat.dtos.ReceiveDto;
import com.greenfox.p2pchat.services.ApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ApiController {
    private ApiService apiService;

    @PostMapping("/api/message/receive")
    public ResponseEntity<?> receiveMessage(@RequestBody ReceiveDto receiveDto, HttpServletRequest request) {
        String errorMessage = apiService.validateReceivedMsg(receiveDto);
        if (errorMessage != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMsgDto("error", errorMessage));
        }
        apiService.receiveMessage(receiveDto, request.getRemoteAddr());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
