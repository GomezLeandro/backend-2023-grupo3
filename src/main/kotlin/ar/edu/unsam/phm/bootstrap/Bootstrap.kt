package ar.edu.unsam.phm.bootstrap

import ar.edu.unsam.phm.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Bootstrap : InitializingBean {

    //REPOSITORIOS
    @Autowired
    lateinit var usuarioRepository: Repositorio<Usuario>
    @Autowired
    lateinit var hospedajeRepository: Repositorio<Hospedaje>

    //USUARIOS
    lateinit var leandro:Usuario
    lateinit var dario:Usuario
    lateinit var veronica:Usuario
    lateinit var gonzalo:Usuario

    //CASAS
    lateinit var casa1: Casa
    lateinit var casa2: Casa
    lateinit var casa3: Casa
    lateinit var casa4: Casa
    lateinit var casa5: Casa
    lateinit var casa6: Casa
    lateinit var casa7: Casa
    lateinit var casa8: Casa

        //CABAÑAS
        lateinit var cabaña1: Cabaña
        lateinit var cabaña2: Cabaña
        lateinit var cabaña3: Cabaña
        lateinit var cabaña4: Cabaña
        lateinit var cabaña5: Cabaña
        lateinit var cabaña6: Cabaña
        lateinit var cabaña7: Cabaña
        lateinit var cabaña8: Cabaña
    /*
            //DEPARTAMENTO
            var departamento1 = Departamento(1800.00)
            var departamento2 = Departamento(1900.00)
            var departamento3 = Departamento(2300.00)
            var departamento4 = Departamento(2400.00)
            var departamento5 = Departamento(2500.00)
            var departamento6 = Departamento(2000.00)
            var departamento7 = Departamento(2100.00)
            var departamento8 = Departamento(2500.00)
        */

    //PROCESO DE INICIO
    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running initialization")
        println("************************************************************************")
        this.crearUsuarios()
        this.crearHospedajes()
    }

    //FUNCION DONDE SE DELEGA LA CREACION Y AGREGACION DE LOS HOSPEDAJES SEGUN SU TIPO (CASA, CABAÑA O DEPARTAMENTO)
    fun crearHospedajes(){
        this.agregarCasas()
        this.agregarCabañas()
    }

    //CREANDO Y AGREGANDO A LOS USUARIOS AL REPOSITORIO
    fun crearUsuarios(){
        leandro = Usuario(
            "Leandro",
            "Gomez",
            "LeanGomez",
            "Lean123",
            LocalDate.of(2000,10,20),
            "España",
            3500.00
        )

        dario = Usuario(
            "Dario",
            "Albelo",
            "DaruAlbelo",
            "Dario123",
            LocalDate.of(2002,1,14),
            "Brasil",
            2000.00
        )

        veronica = Usuario(
            "Veronica",
            "Carbonelli",
            "VeroCarbonelli",
            "Veru123",
            LocalDate.of(1997,9,6),
            "Argentina",
            3000.00
        )

        gonzalo = Usuario(
            "Gonzalo",
            "Mendez",
            "GonzaMendez",
            "Gonza123",
            LocalDate.of(2003,7,15),
            "Argentina",
            3500.00
        )

        usuarioRepository.crear(leandro)
        usuarioRepository.crear(dario)
        usuarioRepository.crear(veronica)
        usuarioRepository.crear(gonzalo)
    }

    //CREANDO Y AGREGANDO A LAS CABAÑAS AL REPOSITORIO
    fun agregarCabañas(){
        cabaña1 = Cabaña(2100.00).apply {

        }
        cabaña2 = Cabaña(1800.00).apply {

        }
        cabaña3 = Cabaña(1900.00).apply {

        }
        cabaña4 = Cabaña(2000.00).apply {

        }
        cabaña5 = Cabaña(2100.00).apply {

        }
        cabaña6 = Cabaña(2200.00).apply {

        }
        cabaña7 = Cabaña(2400.00).apply {

        }
        cabaña8 = Cabaña(2500.00).apply {

        }
    }

    //CREANDO Y AGREGANDO A LAS CASAS AL REPOSITORIO
    fun agregarCasas(){
        casa1 = Casa(1000.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 4
            cantBaños = 2
            destino = "Brasil"
        }
        casa2 = Casa(2000.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 2
            cantBaños = 1
            destino = "Brasil"
        }
        casa3 = Casa(1500.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 3
            cantBaños = 1
            destino = "Argentina"
        }
        casa4 = Casa(1500.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 5
            cantBaños = 2
            destino = "España"
        }
        casa5 = Casa(1200.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 5
            cantBaños = 1
            destino = "España"
        }
        casa6 = Casa(1100.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 3
            cantBaños = 2
            destino = "Argentina"
        }
        casa7 = Casa(2500.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 5
            cantBaños = 3
            destino = "Mexico"
        }
        casa8 = Casa(2200.00).apply {
            nombre = "casa soleada a la orilla del mar"
            cantHuespedes = 1
            cantBaños = 1
            destino = "Mexico"
        }

        hospedajeRepository.crear(casa1)
        hospedajeRepository.crear(casa2)
        hospedajeRepository.crear(casa3)
        hospedajeRepository.crear(casa4)
        hospedajeRepository.crear(casa5)
        hospedajeRepository.crear(casa6)
        hospedajeRepository.crear(casa7)
        hospedajeRepository.crear(casa8)
    }
}