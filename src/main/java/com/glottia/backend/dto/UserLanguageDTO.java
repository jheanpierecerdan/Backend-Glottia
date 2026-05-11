package com.glottia.backend.dto;

import lombok.Data;

@Data
public class UserLanguageDTO {
    private Integer idUsuarioIdioma;
    private String nivel;
    private String tipo;
    private Integer idUsuario;
    private Integer idIdioma;
}
