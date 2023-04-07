package ar.edu.unsam.phm

import ar.edu.unsam.phm.Cabaña
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Dada una cabania con servicio de limpieza")
class CabañaConServicioLimpiezaTests {

    @Test
    @DisplayName("Obtiene un plus en su precio base")
    fun cabañaConServicioLimpieza(){
        val cabañaConServicioLimpieza = Cabaña(0.00)
        cabañaConServicioLimpieza.tieneServicioLimpieza = true
        Assertions.assertEquals(10000.00, cabañaConServicioLimpieza.plus())
    }
}

@DisplayName("Dada una cabania sin servicio de limpieza")
class CabañaSinServicioLimpiezaTests {

    @Test
    @DisplayName("No obtiene ningun plus en su precio base")
    fun cabañaSinServicioLimpieza(){
        val cabañaSinServicioLimpieza = Cabaña(0.00)
        Assertions.assertEquals(0.00, cabañaSinServicioLimpieza.plus())
    }
}