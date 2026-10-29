package com.project.my_landing_page.http.request;

import com.project.my_landing_page.models.enums.UsersRoles;

public record UsuarioRequest(String usuario,
                             String senha,
                             UsersRoles role
) {}
