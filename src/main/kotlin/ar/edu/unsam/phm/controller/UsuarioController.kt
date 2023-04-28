package ar.edu.unsam.phm.controller
import ar.edu.unsam.phm.Calificacion
import ar.edu.unsam.phm.Hospedaje
import ar.edu.unsam.phm.Usuario

import ar.edu.unsam.phm.service.ServiceUsuario
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
@CrossOrigin("*")
class UsuarioController {

    @Autowired
    lateinit var usuarioService: ServiceUsuario


    @PostMapping("/usuario-logueado")
    @Operation(summary = "Trae el id del usuario")
    fun traerIdUsuarioLogin(@RequestBody user: UsuarioLogin): Int {
        return usuarioService.traerUsuario(user)
    }


    @DeleteMapping("/usuario/eliminar")
    @Operation(summary = "Permite borrar un amigo de la lista")
    fun borrarAmigo(@RequestParam(name="userId") userId: Int,@RequestParam(name= "id") id: Int) {
        usuarioService.deleteAmigo(userId, id)
    }

    //GET /usuario?userId={id} trae datos del usuario (veo donde lo pongo, no se si en perfil)
    @GetMapping("/usuario")
    @Operation(summary = "Permite traer datos del usuario")
    fun mapearDataUsuario(@RequestParam(name = "userId") userId: String) =
        usuarioService.traerDatos(userId).toUsuarioDTO()

    @PutMapping("/usuario")
    @Operation(summary = " actualiza o bien la fecha de nacimiento o el país de origen")
    fun actualizarUsuario(
        @RequestBody datos: DatosActualizablesUsuario,
        @RequestParam(name = "userId") userId: String
    ): UsuarioDTO {
        usuarioService.actualizarDatos(datos, userId)
        return usuarioService.traerDatos(userId).toUsuarioDTO()
    }

    @PutMapping("/usuario/credito")
    @Operation(summary = " actualiza o bien la fecha de nacimiento o el país de origen")
    fun sumarCredito(@RequestBody data: CreditoDTO, @RequestParam(name = "userId") userId: String): CreditoDTO {
        val nuevoSaldo = usuarioService.sumarCredito(data.credito, userId)
        return CreditoDTO(nuevoSaldo)
    }

    @GetMapping("/reservas/{id}")
    @Operation(summary = "Traigo las reservas compradas del usuario")
    fun traerReservas(@PathVariable id : Int) = usuarioService.getReservasCompradas(id)
    @PostMapping("/mis-publicaciones")
    @Operation(summary = "agrega una publicacion nueva del usuario")
    fun agregarPublicacion(@RequestBody hospedaje: Hospedaje, @RequestParam(name = "userId") userId: Int){
        usuarioService.agregarPublicacion(hospedaje,userId)
    }
    @DeleteMapping("/mis-publicaciones")
    @Operation(summary = "elimina una publicacion del usuario")
    fun deletePublicacion(@RequestParam(name = "hospedajeId")hospedajeId:Int,@RequestParam(name = "userId")userId:Int ){
        usuarioService.deletePublicacion(hospedajeId,userId)
    }
    @PostMapping("/comentarios")
    @Operation(summary = "agrega un comentario a la publicacion")
    fun crearComentario(@RequestParam(name="userId") userId: Int, @RequestBody data:DatosCrearComentario){
        usuarioService.agregarCalificacion(data.hospedaje,data.detalle,data.puntaje,userId)
    }
    @GetMapping("/comentarios")
    @Operation(summary = "obtiene comentarios del usuario")
    fun crearComentario(@RequestParam(name="userId") userId: Int): List<Calificacion> {
        return usuarioService.traerCalificaciones(userId)
    }
    @PostMapping("/reserva")
    @Operation(summary="reserva un alojamiento")
    fun crearReserva(@RequestParam(name="userId") userId: Int, @RequestBody body:ReservaDTO){
        usuarioService.crearReserva(body.fechaReservaDesde,body.fechaReservaHasta,body.pasajeros,body.costoTotal,body.alojamientoId,userId)
    }

    //@DeleteMapping("/mis-calificaciones?calificacionId={calificacionId}&userId={userId}")
    //@Operation(summary = "elimina una calificacion del usuario")
    //fun deleteCalificacion(@RequestParam(name= "calificacionId")calificacionId:Int, @RequestParam(name = "userId")userId: Int){

    //}


}


data class DatosCrearComentario(
    val hospedaje: Hospedaje,
    val detalle:String,
    val puntaje: Double
)
data class CreditoDTO(
    val credito: Double
)

data class DatosActualizablesUsuario(
    val fechaDeNacimiento: String?,
    val paisDeOrigen: String?
)

 data class ReservaDTO(
    val fechaReservaDesde:LocalDate,
    val fechaReservaHasta:LocalDate,
    val alojamientoId:Int=0,
    val pasajeros:Int=0,
    val costoTotal:Double=0.0,
 )
data class UsuarioDTO(
    val id:Int,
    val nombre: String,
    val apellido: String,
    val username: String,
    val paisDeOrigen: String,
    val fotoPerfil:String,
    val fechaDeNacimiento: LocalDate,
    val saldo:Double,
    val amigos:List<UsuarioTarjetaDTO>,
    val reservasCompradas:MutableList<Hospedaje>,
    val publicaciones:List<Hospedaje>,

)

data class HospedajeReservadoDTO(
    val id:Int,
    val imagen:String,
    val nombre:String,
    val puntaje:Double,
    val detalleAlojamiento:String,
    val ubicacion:String,
    val costoPorNoche:Double,
    val costoTotal:Double
)
fun Hospedaje.mapearAHospedajeReservadoDTO()=HospedajeReservadoDTO(
    id=id,
    imagen=imagen,
    nombre= nombre,
    puntaje= puntaje(),
    detalleAlojamiento= detalleAlojamiento,
    ubicacion= ubicacion,
    costoPorNoche= precio(),
    costoTotal=costoTotal,
)


fun mapearAUsuariosTarjetaDto(usuario: Usuario):UsuarioTarjetaDTO= UsuarioTarjetaDTO().also{
    it.nombre=usuario.nombre
    it.apellido=usuario.apellido
    it.paisDeOrigen= usuario.paisDeOrigen
    it.fotoPerfil=usuario.fotoPerfil
    it.id=usuario.id
}
fun Usuario.toUsuarioDTO() = UsuarioDTO(
    id=id,
    nombre = nombre,
    apellido = apellido,
    username = username,
    paisDeOrigen = paisDeOrigen,
    fotoPerfil=fotoPerfil,
    fechaDeNacimiento=fechaDeNacimiento,
    saldo = saldo,
    amigos=amigos.map{ usuario: Usuario ->  (mapearAUsuariosTarjetaDto(usuario))},
    reservasCompradas=reservasCompradas,
    publicaciones= publicaciones)

class UsuarioLogin( ){
    lateinit  var username : String
    lateinit var contrasena :String
    }
class UsuarioTarjetaDTO{
    lateinit var nombre: String
    lateinit var apellido: String
    lateinit var paisDeOrigen: String
    lateinit var fotoPerfil: String
    var id=0
    }