package com.glottia.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50)
    private String modalidad;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "cupo_maximo")
    private Integer cupoMaximo;

    @Column(length = 255)
    private String ubicacion;

    @Column(name = "enlace_virtual", length = 255)
    private String enlaceVirtual;

    @Column(name = "imagen_referencial", length = 255)
    private String imagenReferencial;

    @Column(name = "nivel_sugerido", length = 50)
    private String nivelSugerido;

    @Column(length = 50)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_idioma")
    private Language idioma;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User organizador;
}
