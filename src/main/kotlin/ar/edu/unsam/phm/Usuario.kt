package ar.edu.unsam.phm


import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class Usuario(
    var nombre: String,
    var apellido: String,
    var username: String,
    var contrasenia: String,
    var fechaDeNacimiento: LocalDate,
    var paisDeOrigen: String,
    var fotoPerfil: String,
    var saldo: Double,
    var amigos: MutableSet<Usuario> = mutableSetOf(),
    val reservasCompradas: MutableList<Hospedaje> = mutableListOf(),
    val publicaciones: MutableList<Hospedaje> = mutableListOf(),
    val hospedajesCalificados: MutableList<Hospedaje> = mutableListOf()
) : Datos {
    override var id: Int = 0
    override fun coincidencia(cadena: String): Boolean =
        coicidenciaParcialNombreApellido(cadena) || coincidenciaTotalUsername(cadena)

    private fun coincidenciaTotalUsername(cadena: String) = username.equals(cadena, ignoreCase = false)

    private fun coicidenciaParcialNombreApellido(cadena: String) =
        nombre.contains(cadena, ignoreCase = true) || apellido.contains(cadena, ignoreCase = true)

    override fun validacion() {
        if (!esValido()) {
            throw FaltaCargarInformacionException(
                "Hay informacion vacia, Nombre: $nombre, apellido: $apellido, username: $username, pais de residencia: $paisDeOrigen\n, fecha de nacimiento: $fechaDeNacimiento, contraseña : $contrasenia"
            )
        }
    }

    override fun esValido(): Boolean =
        this.tieneInformacionCargadaEnStrings() && tieneLaEdadAdecuada()

    private fun tieneInformacionCargadaEnStrings() =
        !(this.nombre.isEmpty() && this.apellido.isEmpty() && this.username.isEmpty() && this.contrasenia.isEmpty() && this.paisDeOrigen.isEmpty())

    private fun tieneLaEdadAdecuada() = ChronoUnit.YEARS.between(fechaDeNacimiento, LocalDate.now()) > 18

    //delegar a calificar estas validaciones
    fun calificar(hospedaje: Hospedaje, detalle: String, puntaje: Double) {
        if ((puntaje !in 1.0..5.0) || !puedoCalificar(hospedaje.id) || yaLoCalifique(hospedaje.id)){
            throw BusinessException(
                "No puede calificar un hospedaje que no se haya reservado previamente\n" +
                        "Revise que la calificación ingresada sea mayor a 0 y menor o igual que 5."
            )
        }
        val hospedajeReservado = this.reservasCompradas.filter{it.id == hospedaje.id}[0]
        hospedajesCalificados.add(hospedajeReservado)
        hospedajeReservado.añadirCalificacion(detalle, puntaje, this.id,hospedajeReservado.id)
    }
    private fun puedoCalificar(id: Int): Boolean {
        return this.reservasCompradas.any{it.id == id}
    }

    private fun yaLoCalifique(id:Int): Boolean {
        return this.hospedajesCalificados.any{it.id == id}
    }

    fun borrarAmigo(identificador: Int) {
        var amigoAEliminar = amigos.find { usuario: Usuario -> usuario.id == identificador }
        amigos.remove(amigoAEliminar)
    }

    private fun NoPuedoReservar(hospedaje: Hospedaje, desde: LocalDate, hasta: LocalDate): Boolean {
        return hospedaje.reservas.any { unaReserva -> unaReserva.estaReservado(desde, hasta) }
    }

    fun reservar(hospedaje: Hospedaje, desde: LocalDate, hasta: LocalDate, pasajeros: Int,costoTotal:Double) {
        if (NoPuedoReservar(hospedaje, desde, hasta)) {
            throw NotFoundException(
                "No puede reservar est hospedaje \n" +
                        "Alguien ya lo vio primero"
            )
        } else {
            hospedaje.costoTotal=costoTotal
            hospedaje.cantHuespedes=pasajeros
            this.reservasCompradas.add(hospedaje)
            hospedaje.añadirReserva(desde, hasta, this.id, pasajeros,costoTotal)
        }
    }

    fun agregarPublicacion(hospedaje: Hospedaje) {
        hospedaje.ownerData = OwnerData(this.id,this.fotoPerfil,this.nombre,this.apellido)
        publicaciones.add(hospedaje)
    }

    fun borrarPublicacion(hospedajeId: Int) {
        publicaciones.removeIf { it.id == hospedajeId }
    }

}
@Entity
class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var nombre = ""
}