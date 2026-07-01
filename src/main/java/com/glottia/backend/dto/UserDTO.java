package com.glottia.backend.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;
    private String ciudad;
    private String biografia;
    private String modalidad;
    private LocalDateTime fechaRegistro;
    private Boolean estado;
    private Integer idRol;
}
