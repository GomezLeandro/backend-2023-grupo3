package ar.edu.unsam.phm



import org.springframework.stereotype.Component
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
            throw BusinessException("No existe un Usuario con dicho ID")
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
    fun mostrarTodosLosHospedajes (): List<Hospedaje> = elementos


}

class
RepositorioDeUsuarios : Repositorio<Usuario>(){


}