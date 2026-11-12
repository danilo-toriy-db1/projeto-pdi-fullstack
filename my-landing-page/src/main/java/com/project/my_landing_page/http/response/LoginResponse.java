package com.project.my_landing_page.http.response;

import com.project.my_landing_page.models.enums.UsersRoles;

import java.time.Instant;

public record LoginResponse(String token,
                            String tipo,
                            Instant expiracao,
                            Long usuarioId,
                            String usuario,
                            UsersRoles role) {
}
