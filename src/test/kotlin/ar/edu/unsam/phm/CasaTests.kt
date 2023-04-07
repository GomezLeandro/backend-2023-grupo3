package ar.edu.unsam.phm

import ar.edu.unsam.phm.Casa
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Dada una casa")
class CasaTests {

    @Test
    @DisplayName("Su precio base obtiene un plus que depende del numero de huespedes")
    fun plusCasa(){
        val casa = Casa(0.00)
        val VALOR:Double = 500.00
        casa.cantHuespedes = 2
        Assertions.assertEquals(VALOR * 2, casa.plus())
    }
}