package ar.edu.unsam.phm

import java.time.LocalDate
import java.time.temporal.ChronoUnit

abstract class Hospedaje(var precioBase: Double) : Datos {


    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var destino: String
    var cantHuespedes: Int = 0
    var cantBaños: Int = 0
    var cantHabitaciones: Int = 0
    lateinit var detalleAlojamiento: String
    var tieneServicioLimpieza:Boolean = false
    val PORC_COMISION = 0.05
    val calificaciones:MutableList<Calificacion> = mutableListOf()
    val reservas:MutableSet<Reserva> = mutableSetOf()

    fun precio() = precionConPlus() + comision()

    fun precionConPlus(): Double{
        return precioBase + plus()
    }

    abstract fun plus(): Double

    fun comision(): Double{
        return precionConPlus() * PORC_COMISION
    }

    fun añadirCalificacion(calificacionNueva: Calificacion){
        calificaciones.add(calificacionNueva)
    }

    fun añadirReserva(reservaNueva: Reserva){
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

    fun cantidadDeHuespedesCorrecta()= cantHuespedes > 0
}