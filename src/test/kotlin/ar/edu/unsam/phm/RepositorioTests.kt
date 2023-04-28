package ar.edu.unsam.phm


import org.junit.jupiter.api.*
import java.time.LocalDate



@DisplayName("Dado un repositorio")
    class RepositorioTests {
        lateinit var usuario1 : Usuario
        lateinit var usuario2 : Usuario
        lateinit var usuario3 : Usuario
        lateinit var usuarioModificado : Usuario
        lateinit var repoDeUsuario: Repositorio<Usuario>


        @BeforeEach
        fun init(){
            usuario1 = Usuario(nombre="Leandro",apellido="Gomez",username="Lgomez",contrasenia="1234",fechaDeNacimiento=LocalDate.of(1985, 9, 30),paisDeOrigen="Argentina","",saldo=3000.00)
            usuario2 = Usuario(nombre="Rodrigo",apellido="Nieto",username="Rnieto",contrasenia="1234",fechaDeNacimiento=LocalDate.of(1990, 1, 15),paisDeOrigen="Argentina","",saldo=5000.00)
            usuario3 = Usuario(nombre="Dario",apellido="Albelo",username="Lobito",contrasenia="1234",fechaDeNacimiento=LocalDate.of(1990, 3, 25),paisDeOrigen="Sudafrica","",saldo=9500.00)
            repoDeUsuario = Repositorio<Usuario>()
            repoDeUsuario.crear(usuario1)
            repoDeUsuario.crear(usuario2)
            usuarioModificado = usuario2
            usuarioModificado.nombre= "Mateo"
        }

        @Test
        @DisplayName("trae un usuario por id")
        fun traigoUnUsuarioPorId() {
            Assertions.assertEquals(usuario2,repoDeUsuario.buscarPorId(2))
        }
        @Test
        @DisplayName("se crea un usuario")
        fun crearUnUsuario(){
            repoDeUsuario.crear(usuario3)
            Assertions.assertTrue(repoDeUsuario.estaEnRepo(usuario3.id))
        }

        @Test
        @DisplayName("se modifica un usuario")
        fun modificarUnUsuario(){
            repoDeUsuario.actualizar(usuarioModificado)
            Assertions.assertEquals("Mateo",usuario2.nombre)
        }

        @Test
        @DisplayName("se borra un usuario")
        fun borrarUnUsuario(){
            repoDeUsuario.borrar(usuario2)
            Assertions.assertFalse(repoDeUsuario.estaEnRepo(2))
        }

        @Test
        @DisplayName("se intenta crear un usuario que ya existe => lanza la excepción")
        fun errorPorExistenciaDeUsuario(){
            assertThrows<BusinessException> {
                repoDeUsuario.crear(usuario2)
            }
        }

    @Test
    @DisplayName("se busca un usuario por su username")
    fun buscarPorString(){
        var listaEsperada=mutableListOf(usuario2)

        Assertions.assertEquals(listaEsperada,repoDeUsuario.buscar("Rnieto"))
        }

    @Test
    @DisplayName("se busca un usuario por su nombre o apellido")
    fun buscarPorStringParcial(){
        var listaEsperada=mutableListOf(usuario1)

        Assertions.assertEquals(listaEsperada,repoDeUsuario.buscar("Leand"))
    }
    }







