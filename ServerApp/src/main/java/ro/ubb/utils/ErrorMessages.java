package ro.ubb.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessages {
    UNSUPPORTED_COMMAND("Error: unsupported command."),
    UNSUPPORTED_ARGS("Error: unsupported arguments.");

    private final String message;

    public String message() {
        return message;
    }
}