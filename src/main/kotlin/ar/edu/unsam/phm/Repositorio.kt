package ar.edu.unsam.phm


import ar.edu.unsam.phm.controller.FiltroHospedaje
import ar.edu.unsam.phm.controller.UsuarioLogin
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.NoSuchElementException

@Component
open class Repositorio<Elemento: Datos> {


    val elementos: MutableList<Elemento> = mutableListOf()
    private var idSiguiente = 1

    fun cantElementos() = elementos.size

    fun crear(elemento: Elemento){
        elemento.validacion()
        yaEstaCreado(elemento)
        agregarAlRepo(elemento)
    }

    private fun yaEstaCreado(elemento: Elemento){
        if(estaEnRepo(elemento.id)){
            throw BusinessException("El elemento no puede ser creado porque ya existe dicho usuario")
        }
    }


    fun agregarAlRepo(elemento: Elemento){
        elementos.add(elemento)
        asignarId(elemento)
    }

    private fun asignarId(elemento: Elemento){
        elemento.id  = idSiguiente++
    }

    fun estaEnRepo(idABuscar: Int) = elementos.any{ it.id == idABuscar}

    fun borrar(elemento: Elemento) {
        excepcionPorNoExistenciaEnRepo(elemento.id)
        elementos.remove(elemento)
    }

    fun actualizar(elementoModificado: Elemento){
        elementoModificado.validacion()
        val elementoABorrar = buscarPorId(elementoModificado.id)
        borrar(elementoABorrar)
        agregaElementoModificado(elementoModificado)
    }

    private fun agregaElementoModificado(elementoModificado: Elemento) {
        elementos.add(elementoModificado)
    }

    private fun excepcionPorNoExistenciaEnRepo(idABuscar: Int){
        if(!estaEnRepo(idABuscar)){
            throw NotFoundException("No existe un Usuario con dicho ID")
        }
    }

    fun buscarPorId(idABuscar: Int):Elemento{
        excepcionPorNoExistenciaEnRepo(idABuscar)
        return elementos.first { it.id == idABuscar  }
    }

    fun buscar(cadena: String) = elementos.filter { it.coincidencia(cadena) }



}



@Component
class RepositorioDeHospedajes : Repositorio<Hospedaje>() {

    fun getHospedajesFiltrados(
        destino: String?,
        fechaDesde: LocalDate,
        fechaHasta: LocalDate,
        pasajeros: Int?,
        puntaje: Int
    ): List<Hospedaje>{

        return elementos.filter{
                    it.porDestino(destino) &&
                    it.porCantPasajeros(pasajeros) &&
                    it.disponible(fechaDesde,fechaHasta) &&
                            it.porPuntaje(puntaje)
        }
    }
}
@Component

class RepositorioDeUsuarios : Repositorio<Usuario>(){
    fun buscarIntentoLogueo(user: UsuarioLogin):Int{
        try {
            var usuarioLogeadoEncontrado = elementos.first { it.username == user.username && it.contrasenia == user.contrasena }
            return usuarioLogeadoEncontrado.id
        }catch (e:NoSuchElementException){
            throw NoAutenticadoException("Usuario y/o contraseña incorrecto. Verifique que haya ingresado bien")
        }
    }
    fun encontrarAmigos(identificador: Int):MutableSet<Usuario>{
        val usuarioAux= this.buscarPorId(identificador)
        return usuarioAux.amigos
    }

    fun encontrarReservasCompradas(id: Int): MutableList<Hospedaje>{
        val usuarioAux= this.buscarPorId(id)
        return usuarioAux.reservasCompradas
    }

}