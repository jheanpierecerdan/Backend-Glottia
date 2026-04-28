package com.glottia.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "idiomas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ididioma")
    private Integer idIdioma;

    @Column(length = 100)
    private String nombre;

    @Column(name = "codigoiso", length = 10)
    private String codigoIso;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
