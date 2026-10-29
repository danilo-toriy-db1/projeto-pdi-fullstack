package com.project.my_landing_page.http.response;

import com.project.my_landing_page.models.enums.UsersRoles;

import java.time.LocalDateTime;

public record UsuarioResponse(Long id,
                              String usuario,
                              LocalDateTime dataCadastro,
                              UsersRoles role
) {}
