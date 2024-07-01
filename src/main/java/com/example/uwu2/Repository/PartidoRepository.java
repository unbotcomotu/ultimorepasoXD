package com.example.uwu2.Repository;

import com.example.uwu2.Entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartidoRepository extends JpaRepository<Partido, Integer> {
    @Query(nativeQuery = true,value = "select * from partido order by idpartido desc limit 1")
    Partido obtenerUltimoPartido();
}