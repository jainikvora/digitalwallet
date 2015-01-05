package exception

class BadRequestException(message: String = null, cause: Throwable = null) extends Exception(message, cause) {

}