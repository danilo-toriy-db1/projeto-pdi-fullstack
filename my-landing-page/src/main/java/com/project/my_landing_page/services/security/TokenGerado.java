package com.project.my_landing_page.services.security;

import java.time.Instant;

public record TokenGerado(String token, Instant expiration) {
}
