package com.cqrs.account.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {
    private String message;
}
