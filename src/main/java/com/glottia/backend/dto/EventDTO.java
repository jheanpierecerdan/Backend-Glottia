package com.glottia.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Integer idEvento;
    private String titulo;
    private String descripcion;
    private String modalidad;
    private LocalDateTime fechaHora;
    private Integer cupoMaximo;
    private String ubicacion;
    private String enlaceVirtual;
    private String estado;
    private Integer idIdioma;
    private Integer idOrganizador;
}
