package com.gameisland.exceptions;

public class UserBalanceNotEnoughEception extends RuntimeException {
    public UserBalanceNotEnoughEception(String message) {
        super(message);
    }
}
