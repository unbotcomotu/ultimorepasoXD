package com.example.uwu2.Controller;

import com.example.uwu2.Entity.*;
import com.example.uwu2.Repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class KiwiRestController {

    private final EquipoRepository equipoRepository;
    private final ParticipanteRepository participanteRepository;
    private final DeporteRepository deporteRepository;
    private final PartidoRepository partidoRepository;
    private final HistorialpartidoRepository historialpartidoRepository;

    public KiwiRestController(EquipoRepository equipoRepository,
                              ParticipanteRepository participanteRepository,
                              DeporteRepository deporteRepository,
                              PartidoRepository partidoRepository,
                              HistorialpartidoRepository historialpartidoRepository) {
        this.equipoRepository = equipoRepository;
        this.participanteRepository = participanteRepository;
        this.deporteRepository = deporteRepository;
        this.partidoRepository = partidoRepository;
        this.historialpartidoRepository = historialpartidoRepository;
    }

    @PostMapping("/sdci/equipo/registro")
    public ResponseEntity<HashMap<String, Object>>registroEquipo(@RequestParam(value = "nombreEquipo",required = false)String nombreEquipo,
                                                                 @RequestParam(value = "colorEquipo",required = false)String colorEquipo,
                                                                 @RequestParam(value = "mascota",required = false)String mascota){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        if(nombreEquipo==null||nombreEquipo.isEmpty()){
            errors.put("nombreEquipo","Ingrese un nombre de equipo");
            response.put("status","error");
        }
        if(colorEquipo==null||colorEquipo.isEmpty()){
            errors.put("colorEquipo","Ingrese un color de equipo");
            response.put("status","error");
        }
        if(mascota==null||mascota.isEmpty()){
            errors.put("mascota","Ingrese un nombre de mascota");
            response.put("status","error");
        }
        if(response.get("status")==null){
            Equipo equipo=new Equipo();
            equipo.setColorEquipo(colorEquipo);
            equipo.setNombreEquipo(nombreEquipo);
            equipo.setMascota(mascota);
            equipoRepository.save(equipo);
            response.put("status","success");
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/sdci/participante/registro")
    public ResponseEntity<HashMap<String, Object>>registroParticipante(@RequestParam(value = "carrera",required = false)String carrera,
                                                                 @RequestParam(value = "codigo",required = false)String codigoStr,
                                                                 @RequestParam(value = "tipoParticipante",required = false)String tipoParticipante,
                                                                       @RequestParam(value = "idEquipo",required = false)String idEquipoStr){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        Integer idEquipo=null;
        Long codigo=null;
        if(carrera==null||carrera.isEmpty()){
            errors.put("carrera","Ingrese el nombre de la carrera");
            response.put("status","error");
        }
        if(codigoStr==null||codigoStr.isEmpty()){
            errors.put("codigo","Ingrese el código PUCP");
            response.put("status","error");
        }else {
            try{
                codigo=Long.parseLong(codigoStr);
                if(codigoStr.length()>10){
                    errors.put("codigo","El código ingresado no puede tener más de 10 dígitos");
                    response.put("status","error");
                }
            }catch (NumberFormatException ex){
                errors.put("codigo","El código ingresado no corresponde a un número válido");
                response.put("status","error");
            }
        }
        if(tipoParticipante==null||tipoParticipante.isEmpty()){
            errors.put("tipoParticipante","Ingrese el tipo de participante");
            response.put("status","error");
        }
        if(idEquipoStr==null||idEquipoStr.isEmpty()){
            errors.put("idEquipo","Ingrese el ID del equipo al que pertenece");
            response.put("status","error");
        }else {
            try{
                idEquipo=Integer.parseInt(idEquipoStr);
                if(equipoRepository.findById(idEquipo).isEmpty()){
                    errors.put("idEquipo","El ID ingresado no corresponde a ningún equipo");
                    response.put("status","error");
                };
            }catch (NumberFormatException ex){
                errors.put("idEquipo","El ID del equipo debe corresponder a un número");
                response.put("status","error");
            }
        }
        if(response.get("status")==null){
            Participante participante=new Participante();
            participante.setCarrera(carrera);
            participante.setEquipo(equipoRepository.findById(idEquipo).get());
            participante.setCodigo(codigo);
            participante.setTipoParticipante(tipoParticipante);
            participanteRepository.save(participante);
            response.put("status","success");
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/sdci/deporte/registro")
    public ResponseEntity<HashMap<String, Object>>registroDeporte(@RequestParam(value = "nombreDeporte",required = false)String nombreDeporte,
                                                                       @RequestParam(value = "pesoDeporte",required = false)String pesoDeporteStr){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        Integer pesoDeporte=null;
        if(nombreDeporte==null||nombreDeporte.isEmpty()){
            errors.put("nombreDeporte","Ingrese el nombre del deporte");
            response.put("status","error");
        }
        if(pesoDeporteStr==null||pesoDeporteStr.isEmpty()){
            errors.put("codigo","Ingrese el peso del deporte");
            response.put("status","error");
        }else {
            try{
                pesoDeporte=Integer.parseInt(pesoDeporteStr);
            }catch (NumberFormatException ex){
                errors.put("pesoDeporte","El peso ingresado no corresponde a un peso válido");
                response.put("status","error");
            }
        }
        if(response.get("status")==null){
            Deporte deporte=new Deporte();
            deporte.setPesoDeporte(pesoDeporte);
            deporte.setNombreDeporte(nombreDeporte);
            deporteRepository.save(deporte);
            response.put("status","success");
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("/sdci/partido/registro")
    public ResponseEntity<HashMap<String, Object>>registroPartido(@RequestParam(value = "idEquipoA",required = false)String idEquipoAStr,
                                                                  @RequestParam(value = "idEquipoB",required = false)String idEquipoBStr,
                                                                  @RequestParam(value = "scoreA",required = false)String scoreAStr,
                                                                  @RequestParam(value = "scoreB",required = false)String scoreBStr,
                                                                  @RequestParam(value = "idDeporte",required = false)String idDeporteStr,
                                                                  @RequestParam(value = "horaFecha",required = false)String horaFechaStr){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        Integer idEquipoA=null;
        Integer idEquipoB=null;
        Integer scoreA=null;
        Integer scoreB=null;
        Integer idDeporte=null;
        if(idEquipoAStr==null||idEquipoAStr.isEmpty()){
            errors.put("idEquipoA","Ingrese el ID del primer equipo");
            response.put("status","error");
        }else {
            try {
                idEquipoA=Integer.parseInt(idEquipoAStr);
                if(equipoRepository.findById(idEquipoA).isEmpty()){
                    errors.put("idEquipoA","El ID del equipo ingresado no se relaciona a ningún equipo");
                    response.put("status","error");
                }
            }catch (NumberFormatException ex){
                errors.put("idEquipoA","El ID ingresado no corresponde a un número");
                response.put("status","error");
            }
        }
        if(idEquipoBStr==null||idEquipoBStr.isEmpty()){
            errors.put("idEquipoB","Ingrese el ID del segundo equipo");
            response.put("status","error");
        }else {
            try {
                idEquipoB=Integer.parseInt(idEquipoBStr);
                if(equipoRepository.findById(idEquipoB).isEmpty()){
                    errors.put("idEquipoB","El ID del equipo ingresado no se relaciona a ningún equipo");
                    response.put("status","error");
                }
            }catch (NumberFormatException ex){
                errors.put("idEquipoB","El ID ingresado no corresponde a un número");
                response.put("status","error");
            }
        }
        if(scoreAStr==null||scoreAStr.isEmpty()){
            errors.put("scoreA","Ingrese el puntaje del primer equipo");
            response.put("status","error");
        }else {
            try {
                scoreA=Integer.parseInt(scoreAStr);
            }catch (NumberFormatException ex){
                errors.put("scoreA","El puntaje ingresado no corresponde a un número");
                response.put("status","error");
            }
        }
        if(scoreBStr==null||scoreBStr.isEmpty()){
            errors.put("scoreB","Ingrese el puntaje del segundo equipo");
            response.put("status","error");
        }else {
            try {
                scoreB=Integer.parseInt(scoreBStr);
            }catch (NumberFormatException ex){
                errors.put("scoreB","El puntaje ingresado no corresponde a un número");
                response.put("status","error");
            }
        }
        if(idDeporteStr==null||idDeporteStr.isEmpty()){
            errors.put("idDeporte","Ingrese el ID del deporte jugado");
            response.put("status","error");
        }else {
            try {
                idDeporte=Integer.parseInt(idDeporteStr);
                if(deporteRepository.findById(idDeporte).isEmpty()){
                    errors.put("idDeporte","El ID del deporte ingresado no se relaciona a ningún deporte");
                    response.put("status","error");
                }
            }catch (NumberFormatException ex){
                errors.put("idDeporte","El ID ingresado no corresponde a un número");
                response.put("status","error");
            }
        }
        if(horaFechaStr==null||horaFechaStr.isEmpty()){
            errors.put("horaFecha","Ingrese la fecha y hora del encuentro");
            response.put("status","error");
        }else {
            try {
                LocalDateTime.parse(horaFechaStr,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }catch (DateTimeException ex){
                errors.put("horaFecha","Ingrese un formato de fecha y hora adecuada (\"yyyy-MM-dd HH:mm:ss\")");
                response.put("status","error");
            }
        }
        if(response.get("status")==null){
            if(idEquipoA==idEquipoB){
                response.put("status","error");
                errors.put("IDs","Los ID de equipo no pueden ser iguales");
                response.put("errors",errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            Partido partido=new Partido();
            partido.setEquipoA(equipoRepository.findById(idEquipoA).get());
            partido.setEquipoB(equipoRepository.findById(idEquipoB).get());
            partido.setScoreA(scoreA);
            partido.setScoreB(scoreB);
            partidoRepository.save(partido);
            Historialpartido historialpartido=new Historialpartido();
            historialpartido.setPartido(partidoRepository.obtenerUltimoPartido());
            historialpartido.setDeporte(deporteRepository.findById(idDeporte).get());
            historialpartido.setHoraFecha(horaFechaStr);
            historialpartidoRepository.save(historialpartido);
            response.put("status","success");
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/sdci/partido/getparticipantes")
    public ResponseEntity<HashMap<String, Object>>obtenerParticipantesPartido(@RequestParam(value = "idPartido",required = false)String idPartidoStr,
                                                                  @RequestParam(value = "idEquipo",required = false)String idEquipoStr){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        Integer idPartido=null;
        Integer idEquipo=null;
        if(idPartidoStr==null||idPartidoStr.isEmpty()){
            errors.put("idPartido","Ingrese el ID del partido");
            response.put("status","error");
        }else {
            try {
                idPartido=Integer.parseInt(idPartidoStr);
                if(partidoRepository.findById(idPartido).isEmpty()){
                    errors.put("idPartido","El ID del partido ingresado no se relaciona a ningún partido");
                    response.put("status","error");
                }
            }catch (NumberFormatException ex){
                errors.put("idPartido","El ID ingresado no corresponde a un número");
                response.put("status","error");
            }
        }
        if(idEquipoStr!=null&&!idEquipoStr.isEmpty()){
            try{
                idEquipo=Integer.parseInt(idEquipoStr);
            }catch (NumberFormatException ex){
                errors.put("idEquipo","Ingrese un valor numérico");
            }
        }
        if(response.get("status")==null){
            Partido partido=partidoRepository.findById(idPartido).get();
            if(idEquipo!=null){
                if(partido.getEquipoA().getId()==idEquipo){
                    response.put("content",partido.getEquipoA());
                }else if(partido.getEquipoB().getId()==idEquipo){
                    response.put("content",partido.getEquipoB());
                }else {
                    response.put("content",partido);
                }
            }else {
                if(errors.get("idEquipo")!=null){
                    response.put("errors",errors);
                }
                response.put("content",partido);
            }
            response.put("status","success");
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/sdci/partido/gethistorialpartidos")
    public ResponseEntity<HashMap<String, Object>>obtenerHistorialPartidos(@RequestParam(value = "idEquipo",required = false)String idEquipoStr){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        Integer idEquipo=null;
        if(idEquipoStr!=null&&!idEquipoStr.isEmpty()){
            try{
                idEquipo=Integer.parseInt(idEquipoStr);
            }catch (NumberFormatException ex){
                errors.put("idEquipo","Ingrese un valor numérico");
            }
        }
        List<Historialpartido>listaHistorialPartidos=historialpartidoRepository.findAll();
        if(idEquipo==null){
            if(errors.get("idEquipo")!=null){
                response.put("errors",errors);
            }
            response.put("content",listaHistorialPartidos);
        }else {
            List<Historialpartido>nuevaLista=new ArrayList<>();
            for(Historialpartido historialpartido:listaHistorialPartidos){
                if(historialpartido.getPartido().getEquipoA().getId()==idEquipo||historialpartido.getPartido().getEquipoB().getId()==idEquipo){
                    nuevaLista.add(historialpartido);
                }
            }
            response.put("content",nuevaLista);
        }
        response.put("status","success");
        return ResponseEntity.ok(response);
    }

}
