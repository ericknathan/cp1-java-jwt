package com.github.ericknathan.tasks.dtos.user;

public record UserAuthDetailsDTO(
        String token
) {
    public UserAuthDetailsDTO(String token) {
        this.token = token;
    }
}
