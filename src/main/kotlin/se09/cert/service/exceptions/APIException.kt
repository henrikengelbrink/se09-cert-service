package se09.cert.service.exceptions

class APIException(val code: APIExceptionCode) : RuntimeException()