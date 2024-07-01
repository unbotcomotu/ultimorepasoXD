package com.example.uwu2.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "participantespartido")
public class Participantespartido {
    @Id
    @Column(name = "idparticipantesPartido", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participante_idparticipante", nullable = false)
    private Participante participanteIdparticipante;

    @Column(name = "horaFecha", nullable = false)
    private Instant horaFecha;

}