package ar.edu.unsam.phm
import java.time.LocalDate
//Que la reserva fije el precio cuando se realiza la propia reserva

class Reserva ( var fechaReservaDesde:LocalDate,
                var fechaReservaHasta:LocalDate,
                var pasajeros:Int = 0,
                var idUsuario:Int = 0,
                var costoTotal:Double)
                {



    fun estaReservado(fechaInicialDeBusqueda:LocalDate,fechaFinalDeBusqueda: LocalDate): Boolean {
        //TODO: Usar localDay o usar el "between" de localDate
        return (fechaInicialDeBusqueda.isAfter(fechaReservaDesde) && fechaInicialDeBusqueda.isBefore(fechaReservaHasta))
                || fechaInicialDeBusqueda.isEqual(fechaReservaDesde)
                || fechaInicialDeBusqueda.isEqual(fechaReservaHasta)
                || (fechaFinalDeBusqueda.isAfter((fechaReservaDesde)) && fechaFinalDeBusqueda.isBefore(fechaReservaHasta))
                || fechaFinalDeBusqueda.isEqual(fechaReservaDesde)
                || fechaFinalDeBusqueda.isEqual(fechaReservaHasta)
    }
}