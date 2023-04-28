package ar.edu.unsam.phm

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDate
import ar.edu.unsam.phm.controller.FiltroHospedaje

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Cabaña::class, name = "cabaña"),
    JsonSubTypes.Type(value = Casa::class, name = "casa"),
    JsonSubTypes.Type(value = Departamento::class, name = "departamento")
)
abstract class Hospedaje(var precioBase: Double) : Datos {

    lateinit var nombre: String
    var descripcion: String = ""
    lateinit var destino: String
    var cantHuespedes: Int = 0
    var cantBaños: Int = 0
    var cantHabitaciones: Int = 0
    var detalleAlojamiento: String = ""
    var tieneServicioLimpieza: Boolean = false
    val PORC_COMISION = 0.05
    var calificaciones: MutableList<Calificacion> = mutableListOf()
    val reservas: MutableSet<Reserva> = mutableSetOf()
    var otrosAspectos: String = ""
    var imagen: String = ""
    val paisDeOrigen: String = ""
    var ubicacion: String = ""
    lateinit var ownerData: OwnerData
    var costoTotal: Double = 0.0

    fun precio() = precionConPlus() + comision()

    //fun costoTotal()=5.35
    fun puntaje() = 4.0

    fun precionConPlus(): Double {
        return precioBase + plus()
    }

    abstract fun plus(): Double

    fun comision(): Double {
        return precionConPlus() * PORC_COMISION
    }

    fun añadirCalificacion(detalle: String, puntaje: Double, id: Int,idHospedaje:Int) {
        val calificacionNueva = Calificacion(detalle, puntaje, id,this.ownerData, idHospedaje)
        calificaciones.add(calificacionNueva)
    }

    fun borrarCalificacion(identificador: Int) {
        var calificacionAEliminar = calificaciones.find { calificacion:Calificacion -> calificacion.idDeOwner() == identificador }
            calificaciones.remove(calificacionAEliminar)
    }
    fun añadirReserva(desde: LocalDate, hasta: LocalDate, idUsuario: Int, puntaje: Int,costoTotal:Double) {
        val reservaNueva = Reserva(desde, hasta, puntaje, idUsuario, costoTotal)
        reservas.add(reservaNueva)
    }

    override var id: Int = 0
    override fun coincidencia(cadena: String): Boolean =
        coincidenciaDescripcion(cadena) || coincidenciaNombreHospedaje(cadena)

    private fun coincidenciaNombreHospedaje(cadena: String) = nombre.equals(cadena, ignoreCase = false)

    private fun coincidenciaDescripcion(cadena: String) =
        descripcion.contains(cadena, ignoreCase = true)

    override fun validacion() {
        if (!esValido()) {
            throw FaltaCargarInformacionException(
                "Hay informacion vacia, Nombre: $nombre, Descripción: $descripcion"
            )
        }
    }

    override fun esValido(): Boolean =
        this.tieneInformacionCargadaEnStrings() && this.cantidadDeHuespedesCorrecta()

    fun tieneInformacionCargadaEnStrings() =
        !(this.nombre.isEmpty() && this.descripcion.isEmpty())

    fun cantidadDeHuespedesCorrecta() = cantHuespedes > 0

    fun porDestino(unDestino: String?): Boolean {
        if(unDestino.isNullOrEmpty()){
            return true
        }
        return this.destino == unDestino
    }

    fun porCantPasajeros(cantPasajeros: Int?): Boolean {
        if(cantPasajeros == null){
            return true
        }
        return cantHuespedes >= cantPasajeros
    }

    fun disponible(fechaInicialBusqueda: LocalDate, fechaFinalBusqueda: LocalDate) =
        cantDeReservas(fechaInicialBusqueda, fechaFinalBusqueda) == 0
    fun porPuntaje(puntaje: Int) = this.puntaje() >= puntaje
    fun cantDeReservas(fechaInicialBusqueda: LocalDate, fechaFinalBusqueda: LocalDate): Int =
        reservas.filter { it.estaReservado(fechaInicialBusqueda, fechaFinalBusqueda) }.size

}
