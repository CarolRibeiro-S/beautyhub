package com.beautyhub.dto;

public class LoginResponse {
    private String token;
    private String fullName;

    public LoginResponse(String token, String fullName) {
        this.token = token;
        this.fullName = fullName;
    }

    public String getToken() { return token; }
    public String getFullName() { return fullName; }
}