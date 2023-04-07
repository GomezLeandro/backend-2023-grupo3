package ar.edu.unsam.phm.controller

import ar.edu.unsam.phm.service.HospedajeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")

class HospedajeController {

     @Autowired
     lateinit var hospedajeService: HospedajeService

     @GetMapping("/hospedajes")
     fun traerTodosLosHospedajes()=hospedajeService.getHospedajes()

}