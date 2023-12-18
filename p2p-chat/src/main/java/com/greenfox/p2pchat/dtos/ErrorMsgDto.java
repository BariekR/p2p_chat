package com.greenfox.p2pchat.dtos;

import lombok.Getter;
import lombok.Setter;

public record ErrorMsgDto(String status, String message) {
}
