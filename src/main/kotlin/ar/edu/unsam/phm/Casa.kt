package ar.edu.unsam.phm
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName(value = "casa")
class Casa(precioBase: Double) : Hospedaje(precioBase) {

    val VALOR_POR_HUESPED: Double = 500.00

    override fun plus(): Double {
        return cantHuespedes * VALOR_POR_HUESPED
    }
}