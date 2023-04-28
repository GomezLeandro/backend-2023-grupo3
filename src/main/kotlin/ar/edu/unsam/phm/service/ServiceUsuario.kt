package ar.edu.unsam.phm.service

import ar.edu.unsam.phm.Calificacion
import ar.edu.unsam.phm.Hospedaje
import ar.edu.unsam.phm.RepositorioDeUsuarios
import ar.edu.unsam.phm.RepositorioDeHospedajes
import ar.edu.unsam.phm.Usuario
import ar.edu.unsam.phm.controller.DatosActualizablesUsuario
import ar.edu.unsam.phm.controller.UsuarioLogin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Struct
import java.time.LocalDate
import java.util.Date


@Service
class ServiceUsuario {
    @Autowired
    lateinit var repositorio: RepositorioDeUsuarios
    @Autowired
    lateinit var repositorioAlojamiento: RepositorioDeHospedajes
    fun traerUsuario(user: UsuarioLogin): Int {
        return repositorio.buscarIntentoLogueo(user)
    }
    fun crearReserva(fechaReservaDesde:LocalDate,fechaReservaHasta:LocalDate,pasajeros:Int,costoTotal:Double,alojamientoId:Int,userId:Int){
        val usuarioReserva=repositorio.buscarPorId(userId)
        val hospedajeAux=repositorioAlojamiento.buscarPorId(alojamientoId)
        usuarioReserva.reservar(hospedajeAux,fechaReservaDesde,fechaReservaHasta,pasajeros,costoTotal)
    }
    fun traerDatos(id: String): Usuario {
        return repositorio.buscarPorId(id.toInt())
    }

    fun actualizarDatos(datos: DatosActualizablesUsuario, id: String) {
        var usuario = this.traerDatos(id)
        if (datos.fechaDeNacimiento !== null) {
            usuario.fechaDeNacimiento = LocalDate.parse(datos.fechaDeNacimiento)
        }
        if (datos.paisDeOrigen !== null) {
            usuario.paisDeOrigen = datos.paisDeOrigen
        }
        repositorio.actualizar(usuario)
    }

    fun sumarCredito(cantidad: Double, id: String): Double {
        val usuario = this.traerDatos(id)
        usuario.saldo += (cantidad + 0.0)
        repositorio.actualizar(usuario)
        return usuario.saldo
    }




    fun getReservasCompradas(id: Int) = repositorio.encontrarReservasCompradas(id)
    fun deleteAmigo(idUsuario: Int, idAmigo: Int) {
        val usuarioAux = repositorio.buscarPorId(idUsuario)
        usuarioAux.borrarAmigo(idAmigo)
    }
    fun agregarPublicacion(nuevoHospedaje: Hospedaje,id:Int){
        val usuarioAux = repositorio.buscarPorId(id)
        usuarioAux.agregarPublicacion(nuevoHospedaje)
    }
    fun deletePublicacion(hospedajeId:Int,id:Int){
        val usuarioAux = repositorio.buscarPorId(id)
        usuarioAux.borrarPublicacion(hospedajeId)
    }

    fun agregarCalificacion(hospedaje:Hospedaje,detalle:String, puntaje:Double, id:Int){
        val usuarioAux = repositorio.buscarPorId(id)
        usuarioAux.calificar(hospedaje,detalle,puntaje)
    }
    fun traerCalificaciones(id:Int): List<Calificacion> {
        val usuarioAux = repositorio.buscarPorId(id)
        return calificacionesDelUsuario(usuarioAux.hospedajesCalificados,id)
    }
    fun calificacionesDelUsuario(hospedajesCalificados:List<Hospedaje>,id:Int): List<Calificacion> {
        val calificaciones = hospedajesCalificados.flatMap { it.calificaciones }
        return calificaciones.filter{it.idUsuario == id}
    }
    //fun deleteCalificacion(calificacionId:Int, id:Int){
    //    val usuarioAux = repositorio.buscarPorId(id)
    //    usuarioAux.borrarCalificacion(calificacionId)
    //}
}




