package com.glottia.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Integer idReserva;
    private LocalDateTime fechaReserva;
    private String estadoReserva;
    private Integer idUsuario;
    private Integer idEvento;
}
