package ar.edu.unsam.phm

 class Calificacion(
    val detalle: String,
    val puntaje: Double,
    val idUsuario: Int,
    val ownerData: OwnerData,
     val idHospedaje:Int,
){
     fun idDeOwner():Int{
         return ownerData.id
     }
 }