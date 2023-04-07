package ar.edu.unsam.phm

import ar.edu.unsam.phm.Departamento
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Dado un departamento con pocas habitaciones")
class tests {

    @Test
    @DisplayName("Obtiene un plus en su precio base")
    fun plusDepartamentoPocasHabitaciones() {
        val departamentoPocasHabitaciones = Departamento(0.00)
        val VALOR_PARA_POCAS_HABITACIONES:Double = 2000.00
        departamentoPocasHabitaciones.cantHabitaciones = 2;
        Assertions.assertEquals(VALOR_PARA_POCAS_HABITACIONES,departamentoPocasHabitaciones.plus())
    }

}

@DisplayName("Dado un departamento con muchas habitaciones")
class tests1 {
    @Test
    @DisplayName("Obtiene un plus multiplicado por cada habitacion que tenga")
    fun plusDepartamentoMuchasHabitaciones() {
        val departamentoMuchasHabitaciones = Departamento(0.00)
        val VALOR_PARA_MUCHAS_HABITACIONES:Double = 1000.00
        departamentoMuchasHabitaciones.cantHabitaciones = 3;
        Assertions.assertEquals(VALOR_PARA_MUCHAS_HABITACIONES*3,departamentoMuchasHabitaciones.plus())
    }
}