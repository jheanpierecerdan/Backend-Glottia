package com.glottia.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinteres")
    private Integer idInteres;

    @Column(length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
