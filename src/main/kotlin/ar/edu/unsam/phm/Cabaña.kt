package ar.edu.unsam.phm

class Cabaña(precioBase: Double) : Hospedaje(precioBase) {

    override fun plus(): Double {
        var plus = if(tieneServicioLimpieza) 10000.00 else 0.00
        return plus
    }
}