package ar.edu.unsam.phm.service

import ar.edu.unsam.phm.RepositorioDeHospedajes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ar.edu.unsam.phm.Hospedaje
import java.time.LocalDate

@Service
class HospedajeService {
    @Autowired
    lateinit var repositorio: RepositorioDeHospedajes

    fun elementosPaginados(hospedajes:List<Hospedaje>,pagina:Int): List<Hospedaje>? {
        val totalHospedajes = totalElementos(hospedajes)
        val itemsPorPagina = 12
        val primerIndice = pagina * itemsPorPagina
        if (primerIndice >= totalHospedajes) {
            return null
        }
        if(totalHospedajes < itemsPorPagina){
            return hospedajes.slice(primerIndice until totalHospedajes)
        }
        if(primerIndice == 0){
            return hospedajes.slice(primerIndice until itemsPorPagina)
        }
        return hospedajes.slice(primerIndice..itemsPorPagina)
    }
    fun deleteComentario(hospedajeId:Int,id:Int){
        val hospedajeAux = repositorio.buscarPorId(hospedajeId)
        hospedajeAux.borrarCalificacion(id)
    }
    private fun totalElementos(hospedajes: List<Hospedaje>): Int {
        return hospedajes.size
    }
    fun getHospedajes(pagina: Int): List<Hospedaje>? {
        return elementosPaginados(repositorio.elementos,pagina)

    }

    fun getHospedajesFiltrados(
        pagina: Int,
        destino: String?,
        fechaDesde: LocalDate,
        fechaHasta: LocalDate,
        pasajeros: Int?,
        puntaje: Int
    ): List<Hospedaje>? {
        return elementosPaginados(repositorio.getHospedajesFiltrados(destino, fechaDesde, fechaHasta, pasajeros, puntaje), pagina)
    }

    fun getHospedajeById(id:Int):Hospedaje{
        return repositorio.buscarPorId(id)
    }

}