package ar.edu.unsam.phm

class Casa(precioBase: Double) : Hospedaje(precioBase) {

    val VALOR_POR_HUESPED: Double = 500.00

    override fun plus(): Double {
        return cantHuespedes * VALOR_POR_HUESPED
    }
}