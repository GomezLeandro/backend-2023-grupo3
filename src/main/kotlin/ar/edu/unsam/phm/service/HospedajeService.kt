package ar.edu.unsam.phm.service

import ar.edu.unsam.phm.RepositorioDeHospedajes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HospedajeService {
    @Autowired
    lateinit var repositorio: RepositorioDeHospedajes

    fun getHospedajes()=repositorio.elementos

    //Implementar la busqueda
    //Consultar eliminar, editar, etc


}