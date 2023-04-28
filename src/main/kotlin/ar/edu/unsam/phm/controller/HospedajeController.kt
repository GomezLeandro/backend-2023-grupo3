package ar.edu.unsam.phm.controller

import ar.edu.unsam.phm.service.HospedajeService
import ar.edu.unsam.phm.Hospedaje
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")

class HospedajeController {

    @Autowired
    lateinit var hospedajeService: HospedajeService

    @GetMapping("/hospedajes")
    fun traerTodosLosHospedajes(@RequestParam(name = "pagina") pagina: Int): MutableSet<HospedajeReservadoDTO>? =
        hospedajeService.getHospedajes(pagina)?.map { unHospedaje -> unHospedaje.mapearAHospedajeReservadoDTO() }
            ?.toMutableSet()

    @GetMapping("/hospedajes/filtrados")
    fun filtrarLosHospedajes(
        @RequestParam(name = "pagina") pagina: Int,
        @RequestParam(name = "destino") destino: String?,
        @RequestParam(name = "fechaDesde") fechaDesde: LocalDate,
        @RequestParam(name = "fechaHasta") fechaHasta: LocalDate,
        @RequestParam(name = "pasajeros") pasajeros: Int?,
        @RequestParam(name = "puntaje") puntaje: Int
    ): MutableSet<HospedajeReservadoDTO>? =
        hospedajeService.getHospedajesFiltrados(pagina, destino, fechaDesde, fechaHasta, pasajeros, puntaje)
            ?.map { unHospedaje -> unHospedaje.mapearAHospedajeReservadoDTO() }
            ?.toMutableSet()

    @DeleteMapping("/mis-comentarios")
    @Operation(summary = "elimina un comentario del usuario")
    fun deletePublicacion(@RequestParam(name = "hospedajeId")hospedajeId:Int,@RequestParam(name = "userId")userId:Int ){
        hospedajeService.deleteComentario(hospedajeId,userId)
    }
    @GetMapping("/detalle/hospedaje")
    fun traerDetalleHospedajes(@RequestParam(name = "hospedajeId") hospedajeId: Int): HospedajeDetalleDTO =
        mapToHospedajeDetalleDTO(hospedajeService.getHospedajeById(hospedajeId))

    fun mapToHospedajeDetalleDTO(hospedaje: Hospedaje): HospedajeDetalleDTO = HospedajeDetalleDTO().also {
        it.id = hospedaje.id
        it.imagen = hospedaje.imagen
        it.nombre = hospedaje.nombre
        it.detalleAlojamiento = hospedaje.detalleAlojamiento
        it.ubicacion = hospedaje.ubicacion
        it.costoPorNoche = hospedaje.precio()
        it.costoTotal = hospedaje.costoTotal
        it.destino = hospedaje.destino
        it.otrosAspectos = hospedaje.otrosAspectos
        it.cantHuespedes = hospedaje.cantHuespedes
        it.cantBaños = hospedaje.cantBaños
        it.cantHabitaciones = hospedaje.cantHabitaciones
        it.puntaje = hospedaje.puntaje()
        it.paisDeOrigen = hospedaje.paisDeOrigen
        it.tieneServicioDeLimpieza = hospedaje.tieneServicioLimpieza
    }
}

data class FiltroHospedaje(
    val destino: String?,
    val pasajeros: Int?,
    val fechaDesde: LocalDate,
    val fechaHasta: LocalDate,
    val puntaje: Int
)

class HospedajeDetalleDTO() {
    var id: Int = 0
    lateinit var imagen: String
    lateinit var nombre: String
    lateinit var detalleAlojamiento: String
    lateinit var ubicacion: String
    var costoPorNoche: Double = 0.0
    var costoTotal: Double = 0.0
    lateinit var destino: String
    lateinit var otrosAspectos: String
    var cantHuespedes: Int = 0
    var cantBaños: Int = 0
    var cantHabitaciones: Int = 0
    var puntaje: Double = 0.0
    lateinit var paisDeOrigen: String
    var tieneServicioDeLimpieza: Boolean = false
}
