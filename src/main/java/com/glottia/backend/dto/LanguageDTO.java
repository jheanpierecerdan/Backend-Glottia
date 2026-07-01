package com.glottia.backend.dto;

import lombok.Data;

@Data
public class LanguageDTO {
    private Integer idIdioma;
    private String nombre;
    private String codigoIso;
    private String descripcion;
}
