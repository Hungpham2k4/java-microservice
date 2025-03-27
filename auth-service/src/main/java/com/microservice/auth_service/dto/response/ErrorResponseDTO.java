package com.microservice.auth_service.dto.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Getter

@RequiredArgsConstructor
public class ErrorResponseDTO implements Serializable {

    @NonNull
    private int status;

    @NonNull
    private String message;

}