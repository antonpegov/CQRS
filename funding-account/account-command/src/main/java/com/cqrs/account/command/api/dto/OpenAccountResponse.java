package com.cqrs.account.command.api.dto;

import com.cqrs.account.common.dto.BaseResponse;

import lombok.Data;

@Data
public class OpenAccountResponse extends BaseResponse {
    private String accountId;

    public OpenAccountResponse(String accountId, String message) {
        super(message);
        this.accountId = accountId;
    }

}
