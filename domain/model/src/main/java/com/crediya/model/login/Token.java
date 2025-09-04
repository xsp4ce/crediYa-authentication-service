package com.crediya.model.login;

import java.time.Instant;

public record Token(String value, Instant expiresAt) {
}
