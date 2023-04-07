package ar.edu.unsam.phm.bootstrap

import ar.edu.unsam.phm.ProyectoAirphm
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(ProyectoAirphm::class.java)
    }
}