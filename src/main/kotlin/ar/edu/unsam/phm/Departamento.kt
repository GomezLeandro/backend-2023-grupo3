package ar.edu.unsam.phm

class Departamento(precioBase: Double) : Hospedaje(precioBase) {

    override fun plus(): Double{
        var plus = if(cantHabitaciones<3) 2000.00 else 1000.00 * cantHabitaciones
        return plus
    }
}