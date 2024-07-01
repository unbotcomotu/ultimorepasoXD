package com.example.uwu2.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idequipo", nullable = false)
    private Integer id;

    @Column(name = "nombreEquipo", nullable = false, length = 45)
    private String nombreEquipo;

    @Column(name = "colorEquipo", nullable = false, length = 45)
    private String colorEquipo;

    @Column(name = "mascota", nullable = false, length = 45)
    private String mascota;

}