package com.cqrs.account.command.api.controllers;

import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.cqrs.account.command.api.commands.OpenAccountCommand;
import com.cqrs.account.command.api.dto.OpenAccountRequest;
import com.cqrs.account.command.api.dto.OpenAccountResponse;
import com.cqrs.account.common.dto.BaseResponse;

import com.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping("/api/v1/openFundingAccount")
public class OpenAccountController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@Valid @RequestBody OpenAccountRequest request) {
        var id = UUID.randomUUID().toString();
        OpenAccountCommand command = request.toCommand(id);

        logger.info("Received request to open account: " + request);

        try {
            commandDispatcher.send(command);

            return new ResponseEntity<>(new OpenAccountResponse(id, "Account opened successfully"), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.warning("An error occurred while processing request: " + e.getMessage());

            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var message = MessageFormat
                    .format("An error occurred while processing request to open funding account: {0}", e.getMessage());
            logger.severe("An error occurred while processing request: " + e.getMessage());

            return new ResponseEntity<>(new BaseResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
