package ar.edu.unsam.phm

class BusinessException(message: String) : RuntimeException(message)

class FaltaCargarInformacionException(message: String) : RuntimeException(message)