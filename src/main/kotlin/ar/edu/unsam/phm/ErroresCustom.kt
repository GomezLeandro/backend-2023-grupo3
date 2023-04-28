package ar.edu.unsam.phm
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.NoSuchElementException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NotFoundException(message: String): NoSuchElementException(message)
class FaltaCargarInformacionException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class NoAutenticadoException(message: String): RuntimeException(message)