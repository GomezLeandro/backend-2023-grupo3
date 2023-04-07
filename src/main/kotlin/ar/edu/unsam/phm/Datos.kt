package ar.edu.unsam.phm

interface Datos {
    var id : Int

    fun coincidencia(cadena: String) : Boolean

    fun esValido() : Boolean

    fun validacion()

}