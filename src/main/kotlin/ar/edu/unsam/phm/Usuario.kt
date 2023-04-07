package ar.edu.unsam.phm


import java.time.LocalDate
import java.time.temporal.ChronoUnit


class Usuario(
        var nombre: String,
        var apellido: String,
        var username:String,
        var contrasenia:String,
        var fechaDeNacimiento: LocalDate,
        var paisDeOrigen: String,
        var saldo: Double,
        var amigos: MutableSet<Usuario> = mutableSetOf(),
        val reservasCompradas: MutableList<Hospedaje> = mutableListOf(),

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

    fun tieneInformacionCargadaEnStrings() =
            !(this.nombre.isEmpty() && this.apellido.isEmpty() && this.username.isEmpty() && this.contrasenia.isEmpty() && this.paisDeOrigen.isEmpty())

    fun tieneLaEdadAdecuada()= ChronoUnit.YEARS.between(fechaDeNacimiento, LocalDate.now()) > 18

}