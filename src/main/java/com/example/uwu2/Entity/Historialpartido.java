package com.example.uwu2.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "historialpartidos")
public class Historialpartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhistorialPartidos", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "deporte_iddeporte", nullable = false)
    private Deporte deporte;

    @ManyToOne
    @JoinColumn(name = "partido_idpartido", nullable = false)
    private Partido partido;

    @Column(name = "horaFecha", nullable = false)
    private String horaFecha;

}