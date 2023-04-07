package ar.edu.unsam.phm
import java.time.LocalDate


class Reserva {
    lateinit var fechaReservaDesde:LocalDate
    lateinit var fechaReservaHasta:LocalDate
    var pasajeros:Int = 0

    fun estaReservado(fechaInicial:LocalDate,fechaFinal: LocalDate): Boolean {
        return (fechaReservaDesde < fechaInicial && fechaReservaHasta < fechaInicial) ||
                (fechaReservaHasta > fechaFinal && fechaReservaHasta > fechaFinal)
    }
}