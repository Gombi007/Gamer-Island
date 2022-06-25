package com.gameisland.exceptions;

public class SteamApiNotRespondingException extends RuntimeException {
    public SteamApiNotRespondingException(String message) {
        super(message);
    }
}
