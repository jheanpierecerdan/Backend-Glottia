package com.glottia.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuariointereses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuariointeres")
    private Integer idUsuarioInteres;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "idinteres")
    private Interest interes;
}
