package com.glottia.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_idiomas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarioidioma")
    private Integer idUsuarioIdioma;

    @Column(length = 50)
    private String nivel;

    @Column(length = 50)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "ididioma")
    private Language idioma;
}
