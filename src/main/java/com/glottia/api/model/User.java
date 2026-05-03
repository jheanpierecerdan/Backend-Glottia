package com.glottia.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer idUsuario;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(unique = true, length = 150)
    private String correo;

    @Column(length = 150)
    private String contrasena;

    @Column(length = 100)
    private String ciudad;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Column(length = 50)
    private String modalidad;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "idrol")
    private Role rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UserLanguage> idiomas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UserInterest> intereses;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (estado == null) estado = true;
    }
}
