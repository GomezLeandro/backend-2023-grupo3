package ar.edu.unsam.phm
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName(value = "cabaña")
class Cabaña(precioBase: Double) : Hospedaje(precioBase) {

    override fun plus(): Double {
        var plus = if(tieneServicioLimpieza) 10000.00 else 0.00
        return plus
    }
}